package org.lynn.designPattern.singleton;

import org.junit.jupiter.api.Test;

/**
 * 为了是资源共享，只需要赋值或者初始化一次，大家都可以重复利用
 * 例如  listener，calender ,IOC容器，配置信息config
 *
 * 技术方案：保证整个运行过程中只有一份
 *
 *  1、饿汉式
 *  2、懒汉式
 *  3、注册登记式（枚举式）
 *  4、反序列话如何保证单例
 *
 *  解决问题：恶劣环境（程序的健全性）
 */
public class SingletonMain {

    @Test
    public void test(){

    }

}
