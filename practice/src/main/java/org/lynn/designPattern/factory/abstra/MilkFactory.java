package org.lynn.designPattern.factory.abstra;

import org.lynn.designPattern.factory.entity.Milk;
import org.lynn.designPattern.factory.func.MengniuFactory;
import org.lynn.designPattern.factory.func.TelunsuFactory;

public class MilkFactory extends AbstractFactory {

    public Milk produceMengniuMilk() {
        return new MengniuFactory().produceMilk();
    }

    public Milk produceTelunsuMilk() {
        return new TelunsuFactory().produceMilk();
    }

    public Milk produceSanluMilk() {
        return null;
    }
}
