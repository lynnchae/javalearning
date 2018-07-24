package org.lynn.designPattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Class Name : org.lynn.designPattern.proxy
 * Description : 代理类工厂
 * @author : cailinfeng
 * Date : 2018/6/8 14:32
 */
public class DaoProxyFactory {

    /**MethodProxy
     * 被代理类
     */
    private Object target;

    public DaoProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("transaction manager start >......");
                method.invoke(target, args);
                System.out.println("transaction manager commit");
                return null;
            }
        });
    }

}
