package org.lynn.designPattern.strategy.payment;

import org.lynn.designPattern.strategy.dto.PayResult;

public class AliPay implements PayService {

    @Override
    public PayResult pay(String userId, String amount) {
        System.out.println("begin alipay paying >...");
        PayResult wechatPay = new PayResult();
        wechatPay.setStatus("1");
        wechatPay.setMsg("alipay pay success! amount is {" + amount + "}");
        return wechatPay;
    }

}
