package org.lynn.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Class Name : org.lynn.dubbo
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/28 17:02
 */
public class DubboDemoServiceImpl implements DubboDemoService{

    @Override
    public String test(String a) {
        System.out.println(">>>");
        return a;
    }

    @Override
    public String test(Integer a) {
        System.out.println("...");
        return a + " > reload method";
    }

    @Override
    public String go(String go) {
        return go;
    }

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-provider.xml");
        app.start();
        ((DubboDemoService)app.getBean("dubboDemoService")).test("atest");
        System.in.read();
    }


}
