package org.lynn.designPattern.singleton;

/**
 * 懒汉式单例
 * 外部需要使用时才实例化
 */
public class Lazy {

    private Lazy() {
    }

    //静态块，公共内存区域
    private static Lazy lazy = null;

    //有线程安全问题
    public static Lazy getInstance() {
        if (lazy == null) {
            //并发时两个线程都会进入
            lazy = new Lazy();
        }
        //如果已经初始化，直接返回
        return lazy;
    }

}
