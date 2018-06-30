package org.lynn.beans;

public class BeanDefinition {

    private String beanClassName;

    private boolean lazy_init = false;

    private String factroyBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazy_init() {
        return lazy_init;
    }

    public void setLazy_init(boolean lazy_init) {
        this.lazy_init = lazy_init;
    }

    public String getFactroyBeanName() {
        return factroyBeanName;
    }

    public void setFactroyBeanName(String factroyBeanName) {
        this.factroyBeanName = factroyBeanName;
    }
}
