package org.lynn.designPattern.strategy.payment;

import org.lynn.designPattern.strategy.dto.PayResult;

public interface PayService {

    PayResult pay(String userId, String amount);

}
