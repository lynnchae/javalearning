package org.lynn.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class AuthSucessEvent extends ApplicationEvent {


    private String orderNid;

    private String userId;

    private String phone;

    public AuthSucessEvent(Object source, String orderNid, String userId, String phone) {
        super(source);
        this.orderNid = orderNid;
        this.userId = userId;
        this.phone = phone;
    }

}
