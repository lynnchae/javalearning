package org.lynn.filters;

/**

 * Class Name : org.lynn.filters
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/27 14:35
 */
public class SecurityFilter implements Filter{

    public void filter(Invoker invoker) {
        System.out.println("security filter begin...>");
        invoker.invoke();
        System.out.println("security filter end<...");
    }
}
