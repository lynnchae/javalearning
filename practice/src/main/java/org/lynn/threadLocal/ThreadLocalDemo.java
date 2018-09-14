package org.lynn.threadLocal;

/**
 * Class Name : org.lynn.threadLocal
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/9/14 14:31
 */
public class ThreadLocalDemo {

    public static void main(String[] args){
        Thread t1 = Thread.currentThread();
        ThreadLocal<String> tl1 = new ThreadLocal<>();
        ThreadLocal<String> tl2 = new ThreadLocal<>();
        tl1.set("test");
        tl2.set("test");
        System.out.println(t1.getName());
    }

}
