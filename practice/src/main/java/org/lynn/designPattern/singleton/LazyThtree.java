package org.lynn.designPattern.singleton;

/**
 * 懒汉式单例
 * 内部类只有在外部类被调用的时候才会被加载，一定是方法调用前初始化，巧妙避开了线程安全问题
 * 兼顾了饿汉式的内存浪费，
 * 也兼顾了synchronized性能问题
 * 完美屏蔽这两个的缺点
 */
public class LazyThtree {

    private static boolean initialized = false;

    //静态块，公共内存区域
    private static LazyThtree lazy = null;

    private LazyThtree(){
        //防止反射入侵
        synchronized (LazyThtree.class){
            if(initialized == false){
                initialized = !initialized;
            }else{
                throw new RuntimeException("不可再次初始化");
            }
        }
    }

    //static保存内存空间的共享
    //final保证了这个方法不会被重写
    public static final LazyThtree getInstance(){
        //返回结果前，一定先加载
        return LazyHolder.lazy;
    }

    //默认不加载
    private static class LazyHolder{
        private static final LazyThtree lazy = new LazyThtree();
    }

}
