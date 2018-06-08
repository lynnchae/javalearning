package org.lynn.designPattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Class Name : org.lynn.designPattern.proxy.cglib
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/8 14:56
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public CglibProxy(Object target){
        this.target = target;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib proxy transaction manager start >.......");
        method.invoke(target,args);
        System.out.println("cglib proxy transaction manager commit <.......");
        return null;
    }
}
