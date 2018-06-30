package org.lynn.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class AopProxyUtils {

    public static boolean isAopProxy(Object object){
        return Proxy.isProxyClass(object.getClass());
    }

    public static Object getTargetObject(Object proxy) throws Exception {
        //判断传进来的这个对象是否为代理过的对象
        //如果不是一个代理对象，直接返回
        if (!isAopProxy(proxy)) {
            return proxy;
        }
        return getProxyTargetObject(proxy);
    }
    
    private static Object getProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field target = aopProxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return target.get(aopProxy);
    }

}
