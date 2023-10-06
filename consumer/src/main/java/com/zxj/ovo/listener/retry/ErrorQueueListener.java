package com.zxj.ovo.listener.retry;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class ErrorQueueListener {

    @RabbitListener(queues = "error.queue")
    public void receive(String msg) {
        log.info("ErrorQueueListener收到消息：" + msg);
    }
}
