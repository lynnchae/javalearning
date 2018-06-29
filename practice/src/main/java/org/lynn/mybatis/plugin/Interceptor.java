package org.lynn.mybatis.plugin;

public interface Interceptor {

    public Object intercept(Invocation invocation) throws Throwable;

    public Object plugin(Object target);

}
