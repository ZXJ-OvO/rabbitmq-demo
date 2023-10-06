package com.zxj.ovo.callback;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;


/**
 * 丢失情况：publisher---->exchange   比如 exchange 不存在
 * 发布者确认机制 publisher-confirm
 * 成功：返回ack
 * 失败：返回nack
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestConfirmCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    // ConfirmCallback可以在发送消息时指定，因为每个业务处理confirm成功或失败的逻辑不一定相同
    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        // 1.消息体
        String message = "来自ConfirmCallback的消息😎😎😎😎😎";

        // 2.全局唯一的消息ID，需要封装到CorrelationData中
        // 确认机制发送消息时，需要给每个消息指定一个全局唯一的ID，以区分不同消息，避免ACK回执冲突
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 3.添加callback
        correlationData.getFuture().addCallback(
                confirm -> {
                    if (Objects.requireNonNull(confirm).isAck()) {
                        // 3.1.ack，消息成功
                        log.info("消息发送成功, ID:{}", correlationData.getId());
                    } else {
                        // 3.2.nack，消息失败
                        log.error("消息发送失败, ID:{}, 原因{}", correlationData.getId(), confirm.getReason());
                    }
                },
                ex -> log.error("消息发送异常, ID:{}, 原因{}", correlationData.getId(), ex.getMessage())
        );

        // 4.发送消息
        rabbitTemplate.convertAndSend("confirm.callback.exchange", "confirm.callback", message, correlationData);

        // 休眠一会儿，等待ack回执
        Thread.sleep(2000);
    }
}
