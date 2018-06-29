package org.spring;

import org.annotation.AutoWired;
import org.annotation.Controller;
import org.annotation.Service;
import org.lynn.annotation.AutoWired;
import org.lynn.annotation.Controller;
import org.lynn.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**

 * Class Name : org.lynn.spring
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/26 15:47
 */
public class LynnDispatchServlet extends HttpServlet {

    Properties contextConfig = new Properties();

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    private List<String> classNames = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>>> doPost");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //定位
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //加载
        doLoad(contextConfig.getProperty("scanPackage"));

        //注册
        doRegistry();
        //依赖注入
        doPopulate();

        //
        initHandlerMapping();


    }

    private void initHandlerMapping() {
    }

    private void doRegistry() {
        if (classNames.isEmpty()){return;}
        try{
            for(String className : classNames){
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)){
                    String beanName = clazz.getAnnotation(Controller.class).value();
                    if (beanName.isEmpty()){
                        beanName = lowerFirstCase(className);
                    }
                    beanMap.put(beanName,clazz.newInstance());
                }else if (clazz.isAnnotationPresent(Service.class)){
                    String beanName = clazz.getAnnotation(Service.class).value();
                    if (beanName.isEmpty()){
                        beanName = lowerFirstCase(className);
                    }
                    Object instance = clazz.newInstance();
                    beanMap.put(beanName,instance);
                    Class<?> [] interfaces = clazz.getInterfaces();
                    for (Class claz : interfaces){
                        beanMap.put(claz.getName(),instance);
                    }

                }else{
                    continue;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void doPopulate() {
        if (beanMap.isEmpty()){
            return;
        }

        for(Map.Entry entry : beanMap.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for(Field f : fields){
                if (!f.isAnnotationPresent(AutoWired.class)){continue;}
                AutoWired autoWired = f.getAnnotation(AutoWired.class);
                String beanName = autoWired.value();
                if (beanName.trim().isEmpty()) {
                    beanName = f.getType().getName();
                }
                f.setAccessible(true);
                try {
                    f.set(entry.getValue(),beanMap.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void doLoad(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()){
            if(file.isDirectory()){
                String packageName = scanPackage+"."+file.getName();
                doLoad(packageName);
            }else{
                classNames.add(scanPackage+"."+file.getName().replace(".class",""));
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation.replace("classpath:", ""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String lowerFirstCase(String str){
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }
}
