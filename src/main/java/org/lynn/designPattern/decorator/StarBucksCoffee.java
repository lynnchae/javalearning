package org.lynn.designPattern.decorator;

public class StarBucksCoffee implements Coffee {

    @Override
    public void makeCoffee() {
        System.out.println("coffee produced success!");
    }
}
