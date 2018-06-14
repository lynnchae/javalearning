package org.lynn.mybatis.plugin;

public class LogInterceptor implements Interceptor {



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("this is log");
        try{
            return invocation.proceed();
        }catch (Throwable e){
            throw  e;
        }finally {
            System.out.println("out log");
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }
}
