package org.lynn.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Class Name : org.lynn.rabbitmq
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/30 18:11
 */
@Component
public class RabbitmqProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducer.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        logger.info("sendMessage .......");
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                if (ack) {
//                    logger.info(">>>>>> message sent success!");
//                } else {
//                    logger.info(">>>>>> message sent failed!");
//                }
//            }
//        });
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("message route from exchange {} with routing key {}, replyCode {}, replyText {}",
                        exchange, routingKey, replyCode, replyText);
            }
        });
        rabbitTemplate.convertAndSend("{\"key\":\"rabbitmq.key\",\"number\":\"1\"}");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
        RabbitmqProducer rabbitmqProducer = context.getBean("rabbitmqProducer", RabbitmqProducer.class);
        rabbitmqProducer.sendMessage();

    }


}
