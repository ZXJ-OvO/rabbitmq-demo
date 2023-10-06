package com.zxj.ovo.listener.lazy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LazyQueueListener {
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(
                            name = "lazy.exchange",
                            type = "direct",
                            durable = "true"
                    ),
                    value = @Queue(
                            name = "lazy.queue",
                            durable = "true",
                            arguments = @Argument(
                                    name = "x-queue-mode", value = "lazy"
                            )
                    ),
                    key = "lazy.routingKey"
            )
    )
    public void receive(String message) {
        log.info("收到惰性队列的消息：{}", message);
    }
}
