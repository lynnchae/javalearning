package org.lynn.designPattern.singleton;

/**
 * 饿汉式，类加载时立即初始化，并且创建单例对象
 *
 *  优点：没有任何的锁，执行效率比较高
 *        用户体验上来说更好
 *  缺点：类加载时就初始化，不管用还是不用都占用空间，一定程度上浪费了内存
 *
 *  线程安全，在线程访问前就已经初始化
 */
public class Hungry {

    private Hungry(){

    }
    //先静态后动态
    //先属性后方法
    //先上后下

}
