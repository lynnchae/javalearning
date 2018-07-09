package org.lynn.lock.unreentrant;

/**
 * Class Name : org.lynn.lock.reentrant
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/9 14:26
 */
public class UnReentractLock {

    private boolean locked = false;

    public synchronized void lock() throws InterruptedException {
        //如果是锁住，则等待
        while (locked){
            System.out.println("waiting release lock!");
            wait();
        }
        //拿到锁后
        locked = true;
    }

    public synchronized void unlock(){
        locked=false;
        notify();
    }

}
