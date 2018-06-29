package org.lynn.designPattern.proxy.jdk;

/**
 * Class Name : org.lynn.designPattern.proxy
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/8 14:19
 */
public class UserDaoImpl implements UserDao{

    public void save() {
        System.out.println("saving user to db.......");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("save success!");
    }
}
