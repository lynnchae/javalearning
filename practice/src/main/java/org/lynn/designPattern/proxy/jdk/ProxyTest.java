package org.lynn.designPattern.proxy.jdk;

import org.testng.annotations.Test;

/**
 * Class Name : org.lynn.designPattern.proxy
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/8 14:40
 */
public class ProxyTest {

    @Test
    public void test() {
        UserDao userDaoTarget = new UserDaoImpl();
        System.out.println("target class is >... " + userDaoTarget.getClass());

        UserDao daoProxy =(UserDao) new DaoProxyFactory(userDaoTarget).getProxyInstance();
        System.out.println("proxy class is >... " + daoProxy.getClass());

        daoProxy.save();
    }

}
