package org.lynn.designPattern.mybatic.plugin;

import java.util.ArrayList;
import java.util.List;

public class InterceptorChain {


    private static List<Interceptor> interceptors;

    static {
        interceptors = new ArrayList<>();
        interceptors.add(new HelloInterceptor());
        interceptors.add(new LogInterceptor());
    }

    public Object pluginAll(Object target){
        for(Interceptor interceptor : interceptors){
            target = interceptor.plugin(target);
        }
        return target;
    }

    public static void main(String[] args){
        InterceptorChain chain = new InterceptorChain();
        IUser user = new User();
        user = (IUser) chain.pluginAll(user);
        user.sayHello();
    }

}
