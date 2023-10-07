package com.zxj.ovo.listener.standard.work;


import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueListener {

    @SneakyThrows
    @RabbitListener(queues = "work.queue")
    public void receive1(String msg) {
        System.out.println("work.queue模型消费者1收到消息：" + msg);
        Thread.sleep(20);
    }

    @SneakyThrows
    @RabbitListener(queues = "work.queue")
    public void receive2(String msg) {
        System.out.println("work.queue模型消费者2收到消息：" + msg);
        Thread.sleep(200);
    }
}
