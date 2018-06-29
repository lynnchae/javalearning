package org.lynn.mybatis.plugin;

public class User implements IUser{

    public String sayHello(){
        System.out.println("-==========>");
        return "hello world!";
    }

}
