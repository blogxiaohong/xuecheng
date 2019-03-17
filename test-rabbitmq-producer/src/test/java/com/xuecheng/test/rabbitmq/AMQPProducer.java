package com.xuecheng.test.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AMQPProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMessage() {
        amqpTemplate.convertAndSend("spring.test.exchange","a.b","helloworld");
    }
}
