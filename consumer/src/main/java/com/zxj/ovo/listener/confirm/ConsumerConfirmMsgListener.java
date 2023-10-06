package com.zxj.ovo.listener.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerConfirmMsgListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "confirm.callback.queue"),
                    exchange = @Exchange(name = "confirm.callback.exchange", type = ExchangeTypes.DIRECT),
                    key = {"confirm.callback"}
            )
    )
    public void listenSimpleQueue(String msg) {
        log.info("消费者接收到confirm.callback.queue的消息：【{}】", msg);
        // 模拟异常
        System.out.println(1 / 0);
        log.debug("消息处理完成！");
    }
}

