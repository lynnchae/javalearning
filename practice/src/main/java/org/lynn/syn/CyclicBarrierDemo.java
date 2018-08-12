package org.lynn.syn;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    public static void main(String[] args) throws IOException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName()+"执行结束，到达await");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName()+"--> after await");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        },"cyclicBarrier-t1-thread");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+"执行结束，到达await");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName()+"--> after await");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        },"cyclicBarrier-t2-thread");
        t1.start();
        t2.start();
        System.in.read();
    }

}
