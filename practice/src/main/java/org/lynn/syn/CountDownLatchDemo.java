package org.lynn.syn;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName() + "----> done");
        }, "countdown-latch-thread");
        Thread tA = new Thread(() -> {
            try {
                latch.await();
                System.out.println(Thread.currentThread().getName() + "-----> execute }" + System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "thread-TA");
        Thread tB = new Thread(() -> {
            try {
                latch.await();
                System.out.println(Thread.currentThread().getName() + "-----> execute }" + System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "thread-TB");
        tA.start();
        tB.start();
        t1.start();
        Scanner sc = new Scanner(System.in);
        while (true) {
            if ("exit".equals(sc.nextLine())) {
                break;
            }
            System.out.println(sc.nextLine());
        }
        System.out.println("end");
    }

}
