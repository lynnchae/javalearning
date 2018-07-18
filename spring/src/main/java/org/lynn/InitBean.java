package org.lynn;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        InitBean initBean = (InitBean) context.getBean("initBean");
    }
}
