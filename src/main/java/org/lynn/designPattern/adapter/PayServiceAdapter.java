package org.lynn.designPattern.adapter;

import java.util.Random;

public class PayServiceAdapter implements PayService {

    private NewPayService newPayService;

    public PayServiceAdapter(NewPayService newPayService) {
        this.newPayService = newPayService;
    }

    @Override
    public void pay() {
        Integer uid = new Random().nextInt(20000);
        newPayService.newPay(uid.toString());
    }
}
