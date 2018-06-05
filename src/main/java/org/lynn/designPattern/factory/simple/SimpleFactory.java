package org.lynn.designPattern.factory.simple;

import org.lynn.designPattern.factory.entity.MengniuMilk;
import org.lynn.designPattern.factory.entity.Milk;
import org.lynn.designPattern.factory.entity.TelunsuMilk;

public class SimpleFactory {

    public Milk produce(String name){
        if("telunsu".equals(name)){
            return new TelunsuMilk();
        }else{
            return new MengniuMilk();
        }
    }

}
