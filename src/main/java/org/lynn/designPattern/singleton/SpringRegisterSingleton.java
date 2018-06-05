package org.lynn.designPattern.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册时单例模式，Spring
 */
public class SpringRegisterSingleton {

    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    public static Object getInstance(String className) {
        Object object = ioc.get(className);
        if (object != null) {
            return ioc.get(className);
        } else {
            synchronized (ioc) {
                try {
                    Class clazz = Class.forName(className);
                    object = clazz.newInstance();
                    ioc.put(className, object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return object;

            }
        }
    }

}
