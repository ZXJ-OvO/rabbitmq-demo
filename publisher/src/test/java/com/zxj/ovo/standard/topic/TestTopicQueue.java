package com.zxj.ovo.standard.topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTopicQueue {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "topic.exchange";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "news", message);
    }
}
