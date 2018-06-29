package org.lynn.designPattern.strategy;

import org.junit.jupiter.api.Test;
import org.lynn.designPattern.strategy.payment.PayEnum;

public class PayTest {

    @Test
    void payTest(){
        Order order = new Order("2018061001","15202105898","19.2");
        System.out.println(order.pay(PayEnum.ALI_PAY));
        order = new Order("2018061002","15202105898","520");
        System.out.println(order.pay(PayEnum.WECHAT_PAY));
    }

}
