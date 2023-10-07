package com.zxj.ovo.standard.simple;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSimpleQueue {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "test simple queue";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
