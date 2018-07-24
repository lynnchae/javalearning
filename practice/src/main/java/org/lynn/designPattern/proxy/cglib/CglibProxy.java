package org.lynn.designPattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Class Name : org.lynn.designPattern.proxy.cglib
 * Description :
 * @author : cailinfeng
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

    /**
     * @param o 代理对象本身
     * @param method 被拦截的方法对象
     * @param args 方法调用入参
     * @param methodProxy 用于调用被拦截方法的方法代理对象
     * @return
     * @throws Throwable
     */
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib proxy transaction manager start >.......");
        methodProxy.invokeSuper(o,args);
        System.out.println("cglib proxy transaction manager commit <.......");
        return null;
    }
}
