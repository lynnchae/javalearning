package org.lynn.beans;

import org.lynn.duplicate.aspect.AopConfig;
import org.lynn.duplicate.aspect.AopProxy;

public class BeanWrapper{

    private AopProxy aopProxy = new AopProxy();

    private BeanPostProcessor postProcessor;

    private Object originInstance;

    private Object wrapperInstance;

    public BeanWrapper(Object originInstance) {
        this.originInstance = originInstance;
        this.wrapperInstance = aopProxy.getProxy(originInstance);
    }

    public void setPostProcessor(BeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    public BeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public Object getOriginInstance() {
        return originInstance;
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public void setAopConfig(AopConfig aopConfig){
        this.aopProxy.setAopConfig(aopConfig);
    }
}
