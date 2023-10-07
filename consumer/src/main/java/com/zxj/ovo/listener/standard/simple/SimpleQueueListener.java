package com.zxj.ovo.listener.standard.simple;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueueListener {

    @RabbitListener(queues = "simple.queue")
    public void receive(String msg) {
        System.out.println("消费者接收到simple.queue的消息：" + msg);
    }
}
