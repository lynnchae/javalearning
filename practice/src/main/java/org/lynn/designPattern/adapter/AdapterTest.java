package org.lynn.designPattern.adapter;

import org.junit.jupiter.api.Test;

public class AdapterTest {

    @Test
    public void test(){
        //定义一个新的接口，新接口需要传递uid参数
        NewPayService newPayService = new NewPayServiceImpl();
        newPayService.newPay("23423");
        System.out.println(">>>>>>> \r\n");

        //定义一个适配器，将老接口适配到新接口上
        //此时，老接口不需要传递uid，给老接口一个适配器，可以调用到新接口
        PayServiceAdapter payServiceAdapter = new PayServiceAdapter(newPayService);
        payServiceAdapter.pay();

    }

}
