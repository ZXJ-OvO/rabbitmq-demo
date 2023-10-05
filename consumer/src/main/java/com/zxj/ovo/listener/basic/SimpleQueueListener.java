package com.zxj.ovo.listener.basic;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueueListener {

    @RabbitListener(queues = "simple.queue")
    public void receive(String msg) {
        System.out.println("SimpleQueueListener收到消息：" + msg);
    }
}
