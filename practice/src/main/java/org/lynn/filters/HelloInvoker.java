package org.lynn.filters;

/**
 * Class Name : org.lynn.filters
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/27 15:19
 */
public class HelloInvoker implements Invoker{

    @Override
    public void invoke() {
        System.out.println("hello world!");
    }
}
