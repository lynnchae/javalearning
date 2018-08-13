package org.lynn.lockSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Class Name : org.lynn.lockSupport
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/13 12:39
 */
public class LockSupportDemo {

    //1、如果一个线程 park 了，那么调用 unpark(thread) 这个线程会被唤醒；
    //2、如果一个线程先被调用了 unpark，那么下一个 park(thread) 操作不会挂起线程。
    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        final Object obj = new Object();
//        Thread A = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sum = 0;
//                for(int i=0;i<10;i++){
//                    sum+=i;
//                }
//                try {
//                    synchronized (obj){
//                        obj.wait();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                System.out.println(sum);
//            }
//        });
//        A.start();
//        synchronized (obj){
//            obj.notify();
//        }
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });
        A.start();
        LockSupport.unpark(A);
    }

}
