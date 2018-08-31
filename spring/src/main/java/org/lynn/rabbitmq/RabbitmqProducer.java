package org.lynn.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.alibaba.fastjson.JSON.toJSONString;

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

    private static String EXCHANGE = "org.lynn.exchange";

    private static String ROUTE_KEY = "org.lynn.routeKey";

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessageWithConfirmCallback() {
        logger.info("sendMessage .......");
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("cause {}", cause);
                logger.info("correlationData {}", correlationData);
                if (ack) {
                    //remove local cache for send success message
                    logger.info(">>>>>> message sent success!");
                } else {
                    logger.info(">>>>>> message sent failed! id {}", correlationData.getId());
                    //get from local cache and retry
                }
            }
        });

        //exchange 错误，route_key 正确，ack = false
//        rabbitTemplate.convertAndSend(EXCHANGE + "NO", ROUTE_KEY, "test_message");
        //exchange 正确，route_key 正确，ack = true
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY, "test_message", new CorrelationData("message_id_20180831"));
        //exchange 正确，route_key 错误，ack = true
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY + "NO", "test_message");
    }


    public void sendMessageWithReturnCallback() {
        logger.info("sendMessage .......");
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("message route from exchange {{}} with routing key {{}}, replyCode {{}}, replyText {{}}",
                        exchange, routingKey, replyCode, replyText);
                String messageStr = new String(message.getBody());
                logger.info("messageProperties {}", toJSONString(message.getMessageProperties()));
                logger.info("returned message is {{}}", messageStr);
            }
        });
        //exchange 错误，route_key 正确，发送报错，无法正常发送到交换机
//        rabbitTemplate.convertAndSend(EXCHANGE + "NO", ROUTE_KEY, "test_message");
        //exchange 正确，route_key 正确，发送正常
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY, "test_message");
        //exchange 正确，route_key 正确，returnCallback被回调
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY + "NO", "test_message");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
        RabbitmqProducer rabbitmqProducer = context.getBean("rabbitmqProducer", RabbitmqProducer.class);
//        rabbitmqProducer.sendMessageWithReturnCallback();
        rabbitmqProducer.sendMessageWithConfirmCallback();
    }


}
