package org.lynn.duplicate.service;

/**
 * Class Name : org.lynn.service
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 11:25
 */
public interface TestService {


    String payTest(String userId,String orderNid);

    String outerMethod();

    String innerMethod();

}
