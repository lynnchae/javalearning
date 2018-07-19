package org.lynn.duplicate.support;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * Class Name : org.lynn.duplicate.support
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/19 14:36
 */
public class BeanUtil {

    public static Object getTarget(Object beanInstance) {
        if (!AopUtils.isAopProxy(beanInstance)) {
            return beanInstance;
        } else if (AopUtils.isCglibProxy(beanInstance)) {
            try {
                return getCglibProxyTargetObject(beanInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                return getJdkDynamicProxyTargetObject(beanInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }


}
