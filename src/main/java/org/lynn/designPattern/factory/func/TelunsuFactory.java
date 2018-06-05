package org.lynn.designPattern.factory.func;

import org.lynn.designPattern.factory.entity.Milk;
import org.lynn.designPattern.factory.entity.TelunsuMilk;

/**
 * 专业化工厂
 */
public class TelunsuFactory implements Factory {

    public Milk produceMilk() {
        return new TelunsuMilk();
    }

}
