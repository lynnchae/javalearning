package org.lynn.designPattern.singleton;

/**
 * 懒汉式单例
 * 外部需要使用时才实例化
 */
public class LazyTwo {

    private LazyTwo() {
    }

    //静态块，公共内存区域
    private static LazyTwo lazy = null;

    //性能问题
    public static synchronized LazyTwo getInstance() {
        if (lazy == null) {
            lazy = new LazyTwo();
        }
        //如果已经初始化，直接返回
        return lazy;
    }

}
