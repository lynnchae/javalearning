package org.lynn.designPattern.strategy.payment;

public enum PayEnum {

    ALI_PAY(new AliPay()),WECHAT_PAY(new WechatPay());

    private PayService payService;

    PayEnum(PayService payService) {
        this.payService = payService;
    }

    public PayService get(){
        return payService;
    }
}
