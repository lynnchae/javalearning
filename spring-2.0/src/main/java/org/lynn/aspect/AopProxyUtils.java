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
        //代理对象有个成员对象h，通过此对象获取原始的被代理对象
        //---> http://www.codeweblog.com/spring-aop%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%9B%9B-spring-aop%E7%9A%84jdk%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86/
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field target = aopProxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return target.get(aopProxy);
    }

}
