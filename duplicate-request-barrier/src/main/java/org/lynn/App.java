package org.lynn;


import org.junit.jupiter.api.Test;
import org.lynn.duplicate.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class Name : org.lynn
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 13:36
 */
public class App {


    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
        TestService testService = (TestService) context.getBean("testService");
        CountDownLatch countDownLatch = new CountDownLatch(50);
        final ExecutorService TASK_POOL = Executors.newFixedThreadPool(50);
        Runnable task = () -> {
            try {
                countDownLatch.await();//这里等待其他线程就绪后开始放行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testService.payTest("1", "20180718172601");
        };
        for (int i = 0; i < 50; i++) {
            TASK_POOL.execute(task);
            System.out.println("第" + i + "线程已经准备好");
            countDownLatch.countDown();//每个任务提交完毕后执行
        }
        TASK_POOL.shutdown();
        TASK_POOL.awaitTermination(1, TimeUnit.SECONDS);
    }


}
