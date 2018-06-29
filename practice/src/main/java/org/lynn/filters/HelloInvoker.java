package org.lynn.filters;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : org.lynn.filters
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/27 15:19
 */
public class HelloInvoker implements Invoker{

    @Override
    public void invoke() {
        System.out.println("hello world!");
    }
}
