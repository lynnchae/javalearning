package org.lynn.designPattern.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.jupiter.api.Test;

/**
 * Class Name : org.lynn.designPattern.proxy.cglib
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/8 15:04
 */
public class CglibProxyTest {

    @Test
    public void test(){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\class");
        UserLog userLog = new UserLog();
        UserLog proxy = (UserLog) new CglibProxy(userLog).getProxyInstance();
        proxy.doLog();
    }

}
