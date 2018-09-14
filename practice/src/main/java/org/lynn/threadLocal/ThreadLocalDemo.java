package org.lynn.threadLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Class Name : org.lynn.threadLocal
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/9/14 14:31
 */
@Slf4j
public class ThreadLocalDemo {

    public static void main(String[] args) {
        Thread t1 = Thread.currentThread();
        ThreadLocal<String> tl1 = new ThreadLocal<>();
        ThreadLocal<Flag> tl2 = new ThreadLocal<>();
        tl1.set("test");
        Flag f = new Flag();
        f.setSignal("signaled");
        tl2.set(f);
        log.info("tl2 before update is {} ", f.getSignal());
        f.setSignal("signaled2");
        log.info("tl2 after update is {} ", f.getSignal());
        System.out.println(t1.getName());
    }

}
