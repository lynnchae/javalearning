package org.lynn.filters;

/**

 * Class Name : org.lynn.filters
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/27 14:34
 */
public class LogFilter implements Filter {

    public void filter(Invoker invoker) {
        System.out.println("log filter begin execute...>");
        invoker.invoke();
        System.out.println("log filter end execute<...");
    }
}
