package org.lynn.context.support;

import org.lynn.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 对配置文件进行查找，读取，解析
 */
public class BeanDefitionReader {

    private Properties config = new Properties();

    private List<String> registryBeanClass = new ArrayList<>();

    private final String SCAN_PACKAGE = "scanPackage";

    public List<String> loadBeanDefinitions(){ return this.registryBeanClass;}

    public BeanDefitionReader(String... configLocations) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(configLocations[0].replace("classpath:", ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScan(config.getProperty(SCAN_PACKAGE));
    }

    //每注册一个className，包装为beanDefinition
    public BeanDefinition registerBean(String className) {
        if (registryBeanClass.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactroyBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
            return beanDefinition;
        }
        return null;
    }

    private void doScan(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScan(packageName + "." + file.getName());
            } else {
                registryBeanClass.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

    public Properties getConfig() {
        return this.config;
    }

    private String lowerFirstCase(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }
}
