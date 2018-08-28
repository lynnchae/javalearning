package org.lynn.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * Class Name : org.lynn.dubbo
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/28 17:33
 */
public class DemoConsumer {

    private DubboDemoService dubboDemoService;

    public void setDubboDemoService(DubboDemoService dubboDemoService) {
        this.dubboDemoService = dubboDemoService;
    }

    public String testString(){
        return dubboDemoService.test("testString");
    }

    public String testInteger(){
        return dubboDemoService.test(123);
    }

    public static void main(String[] args){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-dubbo-consumer.xml");
        app.start();
        DubboDemoService dds = ((DubboDemoService)app.getBean("dubboDemoService"));
        DemoConsumer d = (DemoConsumer)app.getBean("demoConsumer");
        d.testString();
    }

}
