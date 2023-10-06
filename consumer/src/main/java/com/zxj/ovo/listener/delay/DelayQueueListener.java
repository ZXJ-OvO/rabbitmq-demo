package com.zxj.ovo.listener.delay;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelayQueueListener {
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "delay.queue",
                            durable = "true"
                    ),
                    exchange = @Exchange(
                            name = "delay.exchange",
                            delayed = "true"
                    ),
                    key = "delay.routingKey"
            )
    )
    public void receive(Message message) {
        log.info("收到延迟队列的消息：{}", new String(message.getBody()));
    }
}
