package com.zxj.ovo.listener.advance.death.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 什么样的消息会成为死信？
 * - 消息被消费者reject或者返回nack
 * - 消息超时未消费
 * - 队列满了
 * 死信交换机的使用场景是什么？
 * - 如果队列绑定了死信交换机，死信会投递到死信交换机；
 * - 可以利用死信交换机收集所有消费者处理失败的消息（死信），交由人工处理，进一步提高消息队列的可靠性。
 */
@Component
public class DeathLetterConfig {

    // 声明普通的交换机
    @Bean
    public DirectExchange srcExchange() {
        return new DirectExchange("src.exchange", true, false);
    }


    // 声明普通的队列，并为其指定死信交换机
    @Bean
    public Queue srcQueue() {
        return QueueBuilder.durable("src.queue")
                .deadLetterExchange("death.exchange")
                .deadLetterRoutingKey("death")
                .ttl(10000)
                .build();
    }


    // 将普通队列和普通交换机绑定并指定路由键
    @Bean
    public Binding srcBinding(Queue srcQueue, DirectExchange srcExchange) {
        return BindingBuilder.bind(srcQueue).to(srcExchange).with("src");
    }


    // 声明死信交换机
    @Bean
    public DirectExchange deathExchange() {
        return new DirectExchange("death.exchange", true, false);
    }


    // 声明死信队列
    @Bean
    public Queue deathQueue() {
        return new Queue("death.queue", true, false, false);
    }


    // 将死信队列和死信交换机绑定并指定路由键
    @Bean
    public Binding deathBinding(Queue deathQueue, DirectExchange deathExchange) {
        return BindingBuilder.bind(deathQueue).to(deathExchange).with("death");
    }
}
