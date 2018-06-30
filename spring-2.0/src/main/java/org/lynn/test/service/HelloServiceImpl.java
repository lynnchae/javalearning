package org.lynn.test.service;

import org.lynn.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService{

    @Override
    public void sayHello(String name) {
        System.out.println("hello, "+name);
    }
}
