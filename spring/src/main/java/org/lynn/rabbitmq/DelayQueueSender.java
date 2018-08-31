package org.lynn.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Class Name : org.lynn.rabbitmq
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/31 14:45
 */
@Component
public class DelayQueueSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static String EXCHANGE = "org.lynn.exchange";

    private static String ROUTE_KEY = "org.lynn.routeKey";

    public void sendDelay() {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY, "test_message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(5000));
                System.out.println("------>" + System.currentTimeMillis());
                return message;
            }
        });
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
        DelayQueueSender delayQueueSender = context.getBean("delayQueueSender", DelayQueueSender.class);
        delayQueueSender.sendDelay();
    }

}
