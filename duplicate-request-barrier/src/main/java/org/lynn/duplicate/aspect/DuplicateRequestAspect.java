package org.lynn.duplicate.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lynn.duplicate.annotation.Shield;
import org.lynn.duplicate.annotation.ShieldDuplicateParam;
import org.lynn.duplicate.redis.RedisClientTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Class Name : org.lynn.aspect
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 10:59
 */

@Aspect
@Component
public class DuplicateRequestAspect {

    @Resource
    private RedisClientTemplate redisClientTemplate;

    @Pointcut("@annotation(org.lynn.duplicate.annotation.Shield)")
    public void requestAspect() {
    }

    @Around("requestAspect()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Method realMethod = null;
        try {
            realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (realMethod.isAnnotationPresent(Shield.class)) {
            Shield shield = realMethod.getAnnotation(Shield.class);
            String module = shield.module();
            String operation = shield.operation();
            Object[] args = joinPoint.getArgs();
            Annotation[][] annotations = realMethod.getParameterAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                for (Annotation annotation : annotations[i]) {
                    if (annotation instanceof ShieldDuplicateParam) {
                        Object value = args[i];
                        ShieldDuplicateParam shieldDuplicateParam = (ShieldDuplicateParam) annotation;
                        StringBuffer cacheKey = new StringBuffer(module).append(":").append(operation).append(":").append(value.toString());
                        Long modCount = redisClientTemplate.incr(cacheKey.toString());
                        System.out.println(Thread.currentThread().getName() + " : modCount : " + modCount + " : time :" + System.nanoTime());

                        if (modCount > 1) {
                            System.err.println(Thread.currentThread().getName() + " : duplicate request shielded!");
                            redisClientTemplate.del(cacheKey.toString());
                            System.out.println(Thread.currentThread().getName() + " >: del key");
//                            throw new RuntimeException("duplicate request shielded!");
                        } else if (modCount == 1) {
                            System.out.println(Thread.currentThread().getName() + " : execute>>>>" + System.nanoTime());
                            try {
                                result = joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throw new RuntimeException(throwable);
                            } finally {
                                System.out.println(Thread.currentThread().getName() + " : del key");
                                redisClientTemplate.del(cacheKey.toString());
                            }
                        }

                    }
                }
            }
        } else {
            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
        return result;
    }

}