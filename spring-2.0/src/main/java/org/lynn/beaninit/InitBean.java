package org.lynn.beaninit;

import org.springframework.beans.factory.InitializingBean;

public class InitBean implements InitializingBean {

    public InitBean(){
        System.out.println("InitBean constructor execute...");
    }

    public void initMethod(){
        System.out.println("init method execute...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitBean afterPropertiesSet execute...");
    }
}
