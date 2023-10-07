package com.zxj.ovo.listener.advance.persistence.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit持久化规则：交换机持久化、队列持久化、消息持久化
 */
@Configuration
public class PersistenceConfig {

    // 交换机持久化
    @Bean
    public DirectExchange persistenceExchange() {
        // RabbitMQ中交换机默认是非持久化的，mq重启后就丢失。
        // 事实上，默认情况下，由SpringAMQP声明的交换机都是持久化的。
        // 三个参数：交换机名称、是否持久化、当没有queue与其绑定时是否自动删除
        return new DirectExchange("persistence.exchange", true, false);
    }


    // 队列持久化
    @Bean
    public Queue persistenceQueue() {
        // RabbitMQ中队列默认是非持久化的，mq重启后就丢失。
        // 事实上，默认情况下，由SpringAMQP声明的队列都是持久化的。
        // 使用QueueBuilder构建队列，durable就是持久化的
        return QueueBuilder.durable("persistence.queue").build();
    }


    // 将上面两个持久化的元素绑定在一起
    @Bean
    public Binding binding2TestPersistence(Queue persistenceQueue, DirectExchange persistenceExchange) {
        return BindingBuilder.bind(persistenceQueue).to(persistenceExchange).with("persistence.routingKey");
    }

    // RabbitMQ持久化：消息持久化
    // 利用SpringAMQP发送消息时，可以设置消息的属性（MessageProperties），指定delivery-mode：
    // 默认情况下，SpringAMQP发出的任何消息都是持久化的，不用特意指定。

}
