package org.lynn.deadLock;

/**
 * Class Name : org.lynn.deadLock
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/7 14:57
 */
public class DeadLock {

    public static void main(String[] args) {
        final Object o1 = new Object();
        final Object o2 = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "--> o1 get lock");
                        Thread.sleep(Long.parseLong("2000"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + "-->>> o2 get lock");
                    }
                }
            }
        },"deadlock-t1-thread");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "--> o2 get lock");
                        Thread.sleep(Long.parseLong("2000"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + "-->>> o1 get lock");
                    }
                }
            }
        },"deadlock-t2-thread");
        t1.start();
        t2.start();
    }


}
