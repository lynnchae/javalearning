package org.lynn.designPattern.adapter;

public class PayServiceImpl implements PayService {

    @Override
    public void pay() {
        System.out.println ("user using old versions paying >>>>");
    }
}
