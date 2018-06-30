package org.lynn.context;

import org.lynn.annotation.AutoWired;
import org.lynn.annotation.Controller;
import org.lynn.annotation.Service;
import org.lynn.aspect.AopConfig;
import org.lynn.beans.BeanDefinition;
import org.lynn.beans.BeanPostProcessor;
import org.lynn.beans.BeanWrapper;
import org.lynn.context.support.BeanDefitionReader;
import org.lynn.core.BeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private String[] configLocations;

    private BeanDefitionReader reader;

    //用来保证注册式单例容器
    private Map<String, Object> beanCacheMap = new HashMap<>();

    private Map<String, BeanWrapper> beanWrapperMap = new HashMap<>();

    public ApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    private void refresh() {
        this.reader = new BeanDefitionReader(configLocations);

        //加载
        List<String> beanDefinitons = reader.loadBeanDefinitions();
        //注册
        doRegistry(beanDefinitons);

        //
        populateBean();

    }

    private void populateBean() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();

            if (!beanDefinitionEntry.getValue().isLazy_init()) {
                Object object = getBean(beanName);
            }
        }

        for (Map.Entry<String, BeanWrapper> beanWrapperEntry : this.beanWrapperMap.entrySet()) {
            doPopulate(beanWrapperEntry.getKey(), beanWrapperEntry.getValue().getOriginInstance());
        }

    }

    private void doPopulate(String beanName, Object originInstance) {

        Class clazz = originInstance.getClass();

        if (! (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (!f.isAnnotationPresent(AutoWired.class)) {
                continue;
            }
            AutoWired autoWired = f.getAnnotation(AutoWired.class);
            String autowiredBeanName = autoWired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = f.getType().getName();
            }
            f.setAccessible(true);
            try {
                f.set(originInstance, beanWrapperMap.get(autowiredBeanName).getWrapperInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void doRegistry(List<String> beanDefinitons) {

        for (String className : beanDefinitons) {
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isInterface()) {
                    continue;
                }
                BeanDefinition beanDefinition = reader.registerBean(className);
                if (beanDefinition != null) {
                    this.beanDefinitionMap.put(beanDefinition.getFactroyBeanName(), beanDefinition);
                }

                Class[] interfaces = clazz.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    //为什么？因为Spring没那么智能，就是这么傻
                    //这个时候，可以自定义名字
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 依赖注入，从这里开始，读取beanDefinition中的信息
     * 然后通过反射机制创建实例并返回
     * spring的做法是，不会吧最原始的对象放出去，会用一个beanWrapper来进行一次包装
     * <p>
     * 装饰器模式：
     * 保留原来的oop关系
     * 对其进行扩展，增强（例如aop）
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            return null;
        }
        String className = beanDefinition.getBeanClassName();

        BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

        Object instance = instantionBean(beanDefinition);
        if (instance == null) {
            return null;
        }

        beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        try {
            beanWrapper.setAopConfig(instantionAopConfig(beanDefinition));
            beanWrapper.setPostProcessor(beanPostProcessor);
            this.beanWrapperMap.put(beanName, beanWrapper);

            beanPostProcessor.postProcessAfterInitialization(instance, beanName);

            return this.beanWrapperMap.get(beanName).getWrapperInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object instantionBean(BeanDefinition beanDefinition) {
        Object instance = null;
        String className = beanDefinition.getBeanClassName();

        if (this.beanCacheMap.containsKey(className)) {
            instance = beanCacheMap.get(className);
        } else {
            try {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                beanCacheMap.put(className, instance);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private AopConfig instantionAopConfig(BeanDefinition beanDefinition) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        AopConfig aopConfig = new AopConfig();
        String expression = reader.getConfig().getProperty("pointCut");
        String[] before = reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after = reader.getConfig().getProperty("aspectAfter").split("\\s");
        String className = beanDefinition.getBeanClassName();
        Class<?> clazz = Class.forName(className);
        Pattern pattern = Pattern.compile(expression);
        Class aspectClass = Class.forName(before[0]);
        for (Method m : clazz.getMethods()) {
            //public .* org\.lynn\.service\..*Service\..*\(.*\)
            //public java.lang.String org.lynn.service.DemoServiceImpl.sayHello(java.lang.String,java.lang.String)
            Matcher matcher = pattern.matcher(m.toString());
            if (matcher.matches()) {
                //能满足切面规则的类，添加的AOP配置中
                aopConfig.put(m, aspectClass.newInstance(), new Method[]{aspectClass.getMethod(before[1]), aspectClass.getMethod(after[1])});
            }
        }
        return aopConfig;

    }
}
