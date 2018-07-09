package org.lynn.lock;

import org.junit.jupiter.api.Test;
import org.lynn.lock.reentrant.ReentractLock;
import org.lynn.lock.unreentrant.UnReentractLock;

/**
 * Class Name : org.lynn.lock.reentrant
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/9 14:30
 */
public class LockTest {

    UnReentractLock unReentractLock = new UnReentractLock();

    ReentractLock reentractLock = new ReentractLock();

    @Test
    public void testUnreentrant() throws InterruptedException {
        //sayHello先获得了lock，sayNihao就无法再获得锁
        sayHello();
    }

    @Test
    public void testReentrant() throws InterruptedException {
        read();
    }

    private void read() throws InterruptedException {
        reentractLock.lock();
        System.out.println("reading message");
        Thread.sleep(1000);
        update();
        reentractLock.unlock();
    }

    private void update() throws InterruptedException {
        reentractLock.lock();
        System.out.println("update user read message");
        reentractLock.unlock();
    }


    private void sayHello() throws InterruptedException {
        unReentractLock.lock();
        System.out.println("hello moto");
        sayNihao();
        unReentractLock.unlock();
    }

    private void sayNihao() throws InterruptedException {
        unReentractLock.lock();
        System.out.println("你好，摩托！");
        unReentractLock.unlock();
    }


}
