package org.lynn.designPattern.factory;

import org.junit.jupiter.api.Test;
import org.lynn.designPattern.factory.abstra.MilkFactory;
import org.lynn.designPattern.factory.func.Factory;
import org.lynn.designPattern.factory.func.MengniuFactory;
import org.lynn.designPattern.factory.simple.SimpleFactory;

/**
 * 先有业务场景，才有设计模式，设计模式始终为业务模型服务。不可生搬硬套。
 * 提升代码可读性、可扩展性、维护成本、复杂的业务问题。
 *
 * 大规模，标准化，批量生产
 */
public class FactoryMain {

    @Test
    public void test(){
        SimpleFactory simpleFactory = new SimpleFactory();
        //将需求告诉工厂，用户不在关心过程，只关心这个结果
        System.out.println(simpleFactory.produce("telunsu").getName());
    }

    @Test
    public void testFunc(){
        Factory mengniuFactory = new MengniuFactory();
        System.out.println(mengniuFactory.produceMilk().getName());
    }

    @Test
    public void testAbstract(){
        //以上三种方法可能会配置错误
        //抽象工厂方法，对于用户而言更加简单了，用户只有选择的权利，保证了程序的健壮性
        MilkFactory milkFactory = new MilkFactory();
        milkFactory.produceMengniuMilk();
        milkFactory.produceTelunsuMilk();
        //如果新增了三鹿牛奶，修改抽象工厂即可
        milkFactory.produceSanluMilk();
    }

}
