package com.zxj.ovo.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;


/**
 * 丢失情况：exchange---->queue   比如 exchange 绑定的 queue 找不到或者不存在
 * 发布者回执机制 publisher-return
 * 成功：不做处理
 * 失败：返回ack
 */
@Slf4j
@Configuration
public class ReturnCallbackConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // 获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 设置ReturnCallback   匿名函数new RabbitTemplate.ReturnCallback()
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 投递失败，记录日志
            log.info("消息发送失败，应答码{}，原因{}，交换机{}，路由键{},消息{}",
                    replyCode, replyText, exchange, routingKey, message.toString());
            // 如果有业务需要，可以重发消息
        });
    }
}
