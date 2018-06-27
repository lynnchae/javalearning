package org.lynn.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : org.lynn.filters
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/27 14:37
 */
public class DemoFilterChain {

    public static void main(String[] args){
        Invoker originInvoker = new HelloInvoker();
        Invoker wrappedInvoke = buildInvokerChain(originInvoker);
        System.out.println(wrappedInvoke);
        wrappedInvoke.invoke();

    }

    public static Invoker buildInvokerChain(Invoker invoker){
        List<Filter> filters = new ArrayList<Filter>();
        Filter logFilter = new LogFilter();
        Filter securityFilter = new SecurityFilter();
        filters.add(logFilter);
        filters.add(securityFilter);
        Invoker last = invoker;
        for(int i = 0;i<filters.size();i++){
            final Filter filter = filters.get(i);
            final Invoker next = last;
            last = new Invoker() {
                public void invoke() {
                    filter.filter(next);
                }
            };
        }
        return last;
    }

}
