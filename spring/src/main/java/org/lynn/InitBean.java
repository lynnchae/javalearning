package org.lynn;

import org.springframework.beans.factory.InitializingBean;

public class InitBean implements InitializingBean {

    public InitBean() {
        System.out.println("InitBean constructor execute...");
    }

    public void initMethod() {
        System.out.println("init method execute...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitBean afterPropertiesSet execute...");
    }

    public static void main(String[] args){
        Thread t1 = new Thread(() -> {
            System.out.println("First task started");
            System.out.println("Sleeping for 2 seconds");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First task completed");
        });
        Thread t2 = new Thread(() -> System.out.println("Second task completed"));
        t1.start();
//        t1.join(); //线程实例的方法join()方法可以使得一个线程在另一个线程结束后再执行，即t2等到t1执行结束后再执行
        t2.start();
    }
}
