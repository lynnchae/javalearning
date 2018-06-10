package org.lynn.designPattern.strategy.payment;

import org.lynn.designPattern.strategy.dto.PayResult;

public class WechatPay implements PayService {

    public PayResult pay(String userId, String amount) {
        System.out.println("begin wechat paying >...");
        PayResult wechatPay = new PayResult();
        wechatPay.setStatus("1");
        wechatPay.setMsg("wechat pay success! amount is {" + amount + "}");
        return wechatPay;
    }

}
