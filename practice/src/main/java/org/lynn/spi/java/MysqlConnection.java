package org.lynn.spi.java;

/**
 * Class Name : org.lynn.spi
 * Description :
 * @@author : cailinfeng
 * Date : 2018/6/29 14:09
 */
public class MysqlConnection implements Connection{

    @Override
    public void getConnection() {
        System.out.println("mysql connected");
    }
}
