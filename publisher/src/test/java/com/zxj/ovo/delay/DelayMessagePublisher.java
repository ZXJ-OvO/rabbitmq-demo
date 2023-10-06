package com.zxj.ovo.delay;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DelayMessagePublisher {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() {
        Message message = MessageBuilder
                .withBody("这是用于测试延迟队列的消息".getBytes())
                .setHeader("x-delay", 5000)
                .build();

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("delay.exchange", "delay.routingKey", message, correlationData);
        log.info("延时消息发送成功");
    }
}
