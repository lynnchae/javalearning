package org.lynn.designPattern.factory.abstra;

import org.lynn.designPattern.factory.entity.Milk;

/**
 * 抽象工厂模式，spring中运用最广泛
 * 易于扩展
 * 假设如果此时新加了一个三鹿牛奶，简单工厂和方法工厂模式修改很麻烦
 * 如果是抽象模式，只需要在抽象工厂中添加一个生产三鹿牛奶的方法即可
 */
public abstract class AbstractFactory {

    //公共的逻辑
    //方便于统一管理

    /**
     * 生产蒙牛牛奶
     * @return
     */
    public abstract Milk produceMengniuMilk();

    /**
     * 生产特仑苏牛奶
     * @return
     */
    public abstract Milk produceTelunsuMilk();

    /**
     * 生产三鹿牛奶
     * @return
     */
    public abstract Milk produceSanluMilk();

}
