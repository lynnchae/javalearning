package org.lynn.designPattern.mybatic.plugin;

public class HelloInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("this is hello");
        try{
            return invocation.proceed();
        }catch (Throwable e){
            throw  e;
        }finally {
            System.out.println("out hello");
        }
    }

    @Override
    public Object plugin(Object target) {
        Object o = Plugin.wrap(target,this);
        return o;
    }
}
