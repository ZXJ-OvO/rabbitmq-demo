package com.zxj.ovo.advance.death;


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
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DeathMsgPublisher {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {

        // 测试这里的代码需要注释掉启动类的Jackson2JsonMessageConverter
        Message message = MessageBuilder
                .withBody("这是用于测试死信机制的消息".getBytes(StandardCharsets.UTF_8))
                .setExpiration("5000")
                .build();

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        correlationData.getFuture().addCallback(
                confirm -> {
                    if (Objects.requireNonNull(confirm).isAck()) {
                        log.info("消息发送成功, ID:{}", correlationData.getId());
                    } else {
                        log.error("消息发送失败, ID:{}, 原因{}", correlationData.getId(), confirm.getReason());
                    }
                },
                ex -> log.error("消息发送异常, ID:{}, 原因{}", correlationData.getId(), ex.getMessage())
        );

        rabbitTemplate.convertAndSend("src.exchange", "src", message, correlationData);

        Thread.sleep(2000);
    }
}
