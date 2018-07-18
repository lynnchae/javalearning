package org.lynn.duplicate.service;

import org.lynn.duplicate.annotation.Shield;
import org.lynn.duplicate.annotation.ShieldDuplicateRequest;
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
    public String payTest(String userId, @ShieldDuplicateRequest String orderNid) {
        System.out.println("-----------------------------------------------orderNid is " + orderNid);
        return null;
    }
}
