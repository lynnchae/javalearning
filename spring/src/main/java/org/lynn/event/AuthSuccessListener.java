package org.lynn.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof AuthSucessEvent) {
            System.out.println("send authsuccess msg to user {" + ((AuthSucessEvent) applicationEvent).getUserId() + "}");
        }
    }
}
