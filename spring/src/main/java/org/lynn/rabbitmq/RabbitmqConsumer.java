package org.lynn.rabbitmq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * Class Name : org.lynn.rabbitmq
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/31 10:24
 */
@Component
public class RabbitmqConsumer implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConsumer.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("------>"+System.currentTimeMillis());
        String messageStr = new String(message.getBody());
        MessageProperties mp = message.getMessageProperties();
        logger.info("message properties {}", toJSONString(mp));
        logger.info("message from queue {{}} is {{}}", mp.getConsumerQueue(), messageStr);
        if ("test_message".equals(messageStr)) {
            logger.info("ack message>>>");
            channel.basicAck(mp.getDeliveryTag(), false);
        } else {
            logger.info("nack message, message requeue");
            channel.basicNack(mp.getDeliveryTag(), false, true);
        }
    }
}
