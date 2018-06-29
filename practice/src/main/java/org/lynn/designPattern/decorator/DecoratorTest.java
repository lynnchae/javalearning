package org.lynn.designPattern.decorator;

import org.junit.jupiter.api.Test;

public class DecoratorTest {

    /**
     * Decorator装饰器，就是动态地给一个对象添加一些额外的职责，动态扩展，和下面继承（静态扩展）的比较。因此，装饰器模式具有如下的特征：
     *
     * 它必须持有一个被装饰的对象（作为成员变量）。
     * 它必须拥有与被装饰对象相同的接口（多态调用、扩展需要）。
     * 它可以给被装饰对象添加额外的功能。
     * 总结：保持接口，动态增强性能。
     *
     * 装饰器通过包装一个装饰对象来扩展其功能，而又不改变其接口，这实际上是基于对象的适配器模式的一种变种。与对象的适配器模式异同：
     *
     * 相同点：都拥有一个目标对象。
     * 不同点：适配器模式需要实现旧接口，而装饰器模式必须实现相同接口。
     * 适配器模式是在适配器中，重写旧接口的方法来调用新接口方法，来实现旧接口不改变，同时使用新接口的目的。新接口适配旧接口。
     *
     * 而装饰模式，是装饰器和旧接口实现相同的接口，在调用新接口的方法中，会调用旧接口的方法，并对其进行扩展。
     */

    @Test
    public void makeCoffee(){
        Coffee pureStarBuckCoffee = new StarBucksCoffee();
        pureStarBuckCoffee.makeCoffee();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Coffee sugarCoffee = new SugarDecorator(pureStarBuckCoffee);
        sugarCoffee.makeCoffee();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Coffee milkSugarCoffee = new MilkDecorator(sugarCoffee);
        milkSugarCoffee.makeCoffee();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
