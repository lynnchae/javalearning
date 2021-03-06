package org.lynn.duplicate.service;

import org.lynn.duplicate.annotation.Shield;
import org.lynn.duplicate.annotation.ShieldDuplicateParam;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * Class Name : org.lynn.service
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 11:26
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    @Override
    @Shield(module = "order", operation = "pay")

    public String payTest(String userId, @ShieldDuplicateParam String orderNid) {
        System.out.println("----------开始执行支付:>>>" + orderNid);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------支付成功:>>>" + orderNid);
        return null;
    }


    @Override
    @CachePut(value = "common", key = "#uid")
    public String outerMethod(String uid) {
        System.out.println("outer method execute...... {" + uid + "}");
        this.innerMethod(uid + "01");
        return "outer";
    }

    @Override
    @CachePut(value = "common", key = "#uid")
    public String innerMethod(String uid) {
        System.out.println("inner method execute...... {" + uid + "}");
        return "inner";
    }
}
