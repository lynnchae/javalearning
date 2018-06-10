package org.lynn.designPattern.strategy;

import org.lynn.designPattern.strategy.dto.PayResult;
import org.lynn.designPattern.strategy.payment.PayEnum;

public class Order {

    private String orderNid;
    private String userId;
    private String amount;

    public Order(String orderNid, String userId, String amount) {
        this.orderNid = orderNid;
        this.userId = userId;
        this.amount = amount;
    }

    public PayResult pay(PayEnum payEnum){
        return payEnum.get().pay(this.userId,this.amount);
    }
}
