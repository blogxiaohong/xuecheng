package com.xuecheng.test.rabbitmq.lisener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue",durable = "true"),
            exchange = @Exchange(value = "spring.test.exchange",type = ExchangeTypes.TOPIC,ignoreDeclarationExceptions = "true"),
            key = "#.#"
    ))
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
