package com.zxj.ovo.listener.death;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 测试死信队列，注意需要将错误交换机和错误队列注释掉，否则会被错误交换机和错误队列消费，因为此处的测试逻辑是基于消费时发生的错误
 * 消费时发生的错误是错误交换机和错误队列的工作，而死信交换机和死信队列的工作是处理不能被消费的消息
 */
@Slf4j
@Component
public class DeathQueueListener {

    @RabbitListener(queues = "death.queue")
    public void receiveDeath(String msg) {
        // 下方的手动抛出异常，使得消息被拒绝，从而进入死信交换机，然后被死信队列消费
        log.info("死信消费者收到消息：{}", msg);
    }


    @SneakyThrows
    @RabbitListener(queues = "src.queue")
    public void receiveSrc(Message message) {
        // sleep(13000);

        byte[] body = message.getBody();
        String string = Arrays.toString(body);
        log.info("普通原消费者收到消息：{}", string);
        // 拒绝消息，让消息变成死信，该异常表示AMQP的basic.reject命令
        // throw new AmqpRejectAndDontRequeueException("Reject message");

        Thread.sleep(10000);  // 模拟测试死信队列的消息超时

    }
}

/*
在你的代码示例中，消费者在接收一条新消息后会沉睡13秒。然而，你可能误解了 RabbitMQ 的超时机制工作方式。
一条消息已经分派给消费者，即使消费者没有确认消费完成（不论是因为处理时间太长或者程序出现异常），它也不会被 RabbitMQ 认为是超时的。消息的 TTL（Time-to-Live）是指消息在队列中等待被消费的最大时间，而不是消费者处理消息的最大时间。换句话说，一旦消息分派给消费者，RabbitMQ 就不再对其进行超时处理。
那么你应该怎样模拟消息超时呢？答案是增加更多的消息，并且限制 RabbitMQ 一次只能分派一条消息给消费者：
限制消费者一次只能接收一条消息：你可以通过 BasicQos 方法设定prefetchCount=1来实现。这样就限定了 RabbitMQ 每次最多发送一条消息给消费者，即使有空闲的消费者也不会分派新的消息过去，除非消费者已经确认处理完了当前的消息。
在你的消息生产者中发送多条消息到队列。由于消费者处理消息的速度较慢（每次沉睡13秒），队列中多余的消息会在10秒后变成死信，因为它们在队列中等待消费超时。
注意，如果你将这两个改动应用到你的代码，可能还需要继续考虑：队列的消息个数、消费者的数量、每个消费者处理消息的时间等因素。如果你的目标是让一些消息超时并转移到死信队列，需要确保队列中的消息数量和处理速度设置得合适，以使得部分消息可以在设定的 TTL 时间内未被消费。
 */

/*
这里的超时主要指两种情况：
队列设置的超时时间：这是指在RabbitMQ中为队列设置的x-message-ttl属性。当队列中的消息处于准备送达态（Ready）超过了这个设置的时间，它会被自动标记为死信，然后发送到死信交换机。这是队列级别的设置，会影响到整个队列里面的所有消息。
消息本身设置的超时时间：这是指在发送消息时设定的expiration属性。这个属性指定了消息自身的生存期，如果超过了这个时间，消息还没有被消费，它会被认为是死信。这是消息级别的设置，只会影响具体的这一个消息。
两者的区别在于，队列的超时设置影响整个队列所有消息，而消息的超时设置只影响特定的消息。如果两种情况同时存在，那么任何一种情况先满足，消息就会变成死信。
举例来说，如果队列设置了10分钟的超时时间，然后你发送了一条消息，但没有设置消息的超时（也就是消息本身的超时时间是无限），那么无论这条消息在队列里面等待了多久，只要超过10分钟没有被消费，它就会被变为死信。反之，如果你在发送消息时设置了1分钟的超时时间，但队列并没有设置超时，那么无论队列里面等待了多久，只要这条消息超过1分钟没有被消费，它就会变为死信。
 */
