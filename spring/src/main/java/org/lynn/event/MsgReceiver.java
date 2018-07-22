package org.lynn.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiver implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void receive(String msg) {
        System.out.println("receive msg from mq, ms -> {" + msg + "}");
        AuthSucessEvent authSucessEvent = new AuthSucessEvent(msg, msg, msg, msg);
        context.publishEvent(authSucessEvent);
    }
}
