package org.lynn.designPattern.mybatic.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Plugin implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target,Interceptor interceptor){
        return Proxy.newProxyInstance(Plugin.class.getClassLoader(),target.getClass().getInterfaces(),new Plugin(target,interceptor));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return interceptor.intercept(new Invocation(target,method,args));
    }
}
