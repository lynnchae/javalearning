package org.lynn.designPattern.factory.func;

import org.lynn.designPattern.factory.entity.MengniuMilk;
import org.lynn.designPattern.factory.entity.Milk;

/**
 * 专业化工厂
 */
public class MengniuFactory implements Factory {

    public Milk produceMilk() {
        return new MengniuMilk();
    }

}
