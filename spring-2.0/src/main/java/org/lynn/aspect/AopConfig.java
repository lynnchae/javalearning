package org.lynn.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 对application中expression的封装
 * method target需要增强，
 * 用自己实现的业务逻辑去增强
 * 配置文件的目的：告诉spring，哪些类的哪些方法，增强的内容是什么
 * 对配置文件的内容进行封装
 */
public class AopConfig {

    //以目标对象需要增强的方法method作为key，需要增强的代码内容作为value
    private Map<Method,Aspect> points = new HashMap<>();

    public void put(Method target,Object aspect,Method[] points){
        this.points.put(target,new Aspect(aspect,points));
    }

    public Aspect get(Method method){
        return this.points.get(method);
    }

    public boolean contains(Method method){
        return this.points.containsKey(method);
    }



    //对增强的代码封装
    public class Aspect{

        private Object aspect;//切面，即logAspect sessionAspect

        private Method[] points;//切点

        public Aspect(Object aspect, Method[] points) {
            this.aspect = aspect;
            this.points = points;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getPoints() {
            return points;
        }
    }

}
