package org.lynn.designPattern.adapter;

public class NewPayServiceImpl implements NewPayService {

    @Override
    public void newPay(String uid) {
        System.out.println( uid + ": new paying......");
    }
}
