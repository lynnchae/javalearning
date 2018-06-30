package org.lynn.context;

import org.lynn.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractApplicationContext {

    protected Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    protected void refreshBeanFactory() {

    }
}
