package org.lynn.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopProxy implements InvocationHandler {

    private AopConfig aopConfig;

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());

        //在原始方法调用之前，执行增强的代码
        //需要通过原生方法去找，通过代理方法去map中是找不到的
        if(aopConfig.contains(m)){
            AopConfig.Aspect aspect = aopConfig.get(m);
            aspect.getPoints()[0].invoke(aspect.getAspect());
        }
        Object obj = method.invoke(this.target,args);

        //在原始方法调用钱，执行需要增强的代码
        if(aopConfig.contains(m)){
            AopConfig.Aspect aspect = aopConfig.get(m);
            aspect.getPoints()[1].invoke(aspect.getAspect());
        }
        return obj;
    }

    public Object getProxy(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public void setAopConfig(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }
}
