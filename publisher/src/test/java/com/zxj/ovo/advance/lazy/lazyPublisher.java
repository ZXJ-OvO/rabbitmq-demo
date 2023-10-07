package com.zxj.ovo.advance.lazy;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class lazyPublisher {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() {
        String message = "测试惰性机制的消息";

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("lazy.exchange", "lazy.routingKey", message, correlationData);
        log.info("惰性消息发送成功");
    }
}
