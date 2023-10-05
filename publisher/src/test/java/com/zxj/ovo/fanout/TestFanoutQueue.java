package com.zxj.ovo.fanout;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFanoutQueue {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "fanout.exchange";
        // 消息
        String message = "test fanout exchange";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

}
