package org.service;

import org.annotation.Service;
import org.lynn.annotation.Service;

/**
 * Class Name : org.lynn.service
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/26 15:43
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public void sayHello(String name) {
        System.out.println("hello , " + name);
    }
}
