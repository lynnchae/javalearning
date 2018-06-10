package org.lynn.designPattern.decorator;

public class MilkDecorator implements Coffee {

    private Coffee coffee;

    public MilkDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public void makeCoffee() {
        System.out.println("add milk");
        coffee.makeCoffee();
    }
}
