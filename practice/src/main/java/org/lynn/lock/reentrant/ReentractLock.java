package org.lynn.lock.reentrant;

/**
 * Class Name : org.lynn.lock.reentrant
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/9 14:40
 */
public class ReentractLock {

    boolean isLocked = false;

    Thread lockedByThread = null;

    int lockCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockedByThread != currentThread) {
            wait();
        }
        lockedByThread = currentThread;
        lockCount++;
        isLocked = true;
        System.out.println("current lock is {" + lockedByThread.getName() + "}, lockCount is {" + lockCount + "}");
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedByThread) {
            lockCount--;
//            System.out.println("unlock , lockCount is " + lockCount);
            if (lockCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }


}
