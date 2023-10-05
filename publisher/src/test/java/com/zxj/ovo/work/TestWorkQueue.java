package com.zxj.ovo.work;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWorkQueue {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    @SneakyThrows
    public void testSend() {
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend("work.queue", "work模型" + i);
            Thread.sleep(20);
        }
    }
}
