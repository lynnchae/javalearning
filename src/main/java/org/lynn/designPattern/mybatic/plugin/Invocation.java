package org.lynn.designPattern.mybatic.plugin;

import java.lang.reflect.Method;

public class Invocation {

    private Object target;
    private Method method;
    private Object[] args;


    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws Throwable{
        return method.invoke(target,args);
    }
}
