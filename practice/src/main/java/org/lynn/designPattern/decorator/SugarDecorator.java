package org.lynn.designPattern.decorator;

public class SugarDecorator implements Coffee {

    private Coffee coffee;

    public SugarDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public void makeCoffee() {
        System.out.println("add sugar");
        coffee.makeCoffee();
    }
}
