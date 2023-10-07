package com.zxj.ovo.listener.standard.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectQueueListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "direct.queue1"),
                    exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
                    key = {"a", "b"}
            )
    )
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "direct.queue2"),
                    exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
                    key = {"b", "c"}
            )
    )
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
    }
}
