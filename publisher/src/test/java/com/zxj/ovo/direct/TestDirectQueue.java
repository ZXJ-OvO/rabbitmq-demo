package com.zxj.ovo.direct;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDirectQueue {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "direct.exchange";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "c", message);
    }
}
