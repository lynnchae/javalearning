package org.lynn;

import org.junit.jupiter.api.Test;
import org.lynn.event.MsgReceiver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MsgReceiver msgReceiver = context.getBean("msgReceiver",MsgReceiver.class);
        msgReceiver.receive("20180722");
    }
}
