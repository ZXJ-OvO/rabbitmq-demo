package com.zxj.ovo.listener.retry.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 失败策略：RepublishMessageRecoverer：重试耗尽后，将失败消息投递到指定的交换机
 * 定义处理失败消息的交换机和队列
 * 失败策略的三种方式：
 * - RejectAndDontRequeueRecoverer：重试耗尽后，直接reject，丢弃消息。默认就是这种方式
 * - ImmediateRequeueMessageRecoverer：重试耗尽后，返回nack，消息重新入队
 * - RepublishMessageRecoverer：重试耗尽后，将失败消息投递到指定的交换机   --- 推荐
 */
//@Configuration
public class RepublishMessageRecovererConfig {

    // 创建处理失败消息的交换机
    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange("error.exchange", true, false);
    }


    // 创建处理失败消息的队列
    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable("error.queue").build();
    }


    // 将队列和交换机绑定
    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error.routingKey");
    }


    // 定义RepublishMessageRecoverer，关联队列和交换机   用于失败重试次数达到上限后的处理方案
    //@Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.exchange", "error.routingKey");
    }
}
