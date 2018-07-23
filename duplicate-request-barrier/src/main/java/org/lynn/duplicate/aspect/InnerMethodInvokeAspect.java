package org.lynn.duplicate.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Class Name : org.lynn.duplicate.aspect
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/23 17:11
 */
@Component
@Aspect
public class InnerMethodInvokeAspect {

    @Pointcut("execution(* org.lynn.duplicate.service..*(..))")
    private void doLogger() {
    }

    @Before("doLogger()")
    public void beforeAdvice(JoinPoint joinPoint) {
        Object targetObject = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        String signatureName = signature.getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("调用类:{"+targetObject+"},执行方法:{"+signatureName+"},请求参数:{"+args+"}");
    }

}
