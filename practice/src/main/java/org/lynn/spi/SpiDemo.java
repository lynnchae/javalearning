package org.lynn.spi;

import org.lynn.spi.java.Connection;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Class Name : org.lynn.spi
 * Description :
 * Date : 2018/6/29 14:09
 * @@author cailinfeng
 */
public class SpiDemo {

    /**
     * Service Provider Interface
     * java spi机制，面向接口编程，模块装配的时候实现了可插拔，不会在代码里出现硬编码，spi提供了一种服务发现机制
     * 各厂商只需要实现接口，并在META-INF/services文件下建立一个文件，名称为需要实现的接口全限定名，内容为厂商自己的实现类全限定名
     *
     *
     *  dubbo SPI
     *  对java spi进行了扩展增强
     *  接口上加了{@link com.alibaba.dubbo.common.extension.SPI}注解，说明该接口为dubbo spi扩展点
     *
     *  {@link com.alibaba.dubbo.common.extension.Adaptive}
     *  -- METHOD
     *      注解如果在方法上，则调ExtensionLoader.getAdaptiveExtension()获取自适应扩展点，首先会动态生成字节码，然后通过URL动态获取
     *      在通过createAdaptiveExtensionClassCode()生成一个自适应扩展类，类名例如：{@link org.lynn.spi.dubbo.Protocol$Adaptive}
     *      然后编译器编译成class加载。具体的编译器Compiler的实现策略也是通过ExtensionLoader.getAdaptiveExtension()
     *
     *  -- TYPE
     *      注解如果实在类上，它获取的自适应适配类不再通过生成自适应适配类java源代码的方式来获取，
     *      而是在读取扩展文件的时候，遇到实现类打了注解@Adaptive就把这个类作为自适应适配类缓存在{@link com.alibaba.dubbo.common.extension.ExtensionLoader }中，直接返回
     *
     *  dubbo spi 的 DI
     *      private T injectExtension(T instance) {
     *         try {
     *             if (objectFactory != null) {
     *                  //循环遍历原始对象的方法
     *                 for (Method method : instance.getClass().getMethods()) {
     *                      //如果碰到方法以set开头，参数列表只有一个，且为public
     *                     if (method.getName().startsWith("set")
     *                             && method.getParameterTypes().length == 1
     *                             && Modifier.isPublic(method.getModifiers())) {
     *                         Class<?> pt = method.getParameterTypes()[0];
     *                         try {
     *                             String property = method.getName().length() > 3 ? method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4) : "";
     *                             //通过AdaptiveExtensionFactory获取实例，调用set方法进行注入
     *                             Object object = objectFactory.getExtension(pt, property);
     *                             if (object != null) {
     *                                 method.invoke(instance, object);
     *                             }
     *                         } catch (Exception e) {
     *                             logger.error("fail to inject via method " + method.getName()
     *                                     + " of interface " + type.getName() + ": " + e.getMessage(), e);
     *                         }
     *                     }
     *                 }
     *             }
     *         } catch (Exception e) {
     *             logger.error(e.getMessage(), e);
     *         }
     *         return instance;
     *     }
     *
     *
     */
    public static void main(String[] args){
        ServiceLoader serviceLoader = ServiceLoader.load(Connection.class);
        Iterator<Connection> i = serviceLoader.iterator();
        while (i.hasNext()){
            Connection connection = i.next();
            connection.getConnection();
        }
    }

}
