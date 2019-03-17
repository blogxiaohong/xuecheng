package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，RabbitMQ 可以设置多个虚拟机，每个虚拟机相当于一个独立的 MQ,默认的虚拟机名称为 /
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE, true, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /**
             * 当接收到消息后此方法将被调用
             * @param consumerTag   消费者标签 用来标识消费者的，在监听队列时设置channel.basicConsume
             * @param envelope  信封
             * @param properties    消费者属性
             * @param body  消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                long deliveryTag = envelope.getDeliveryTag(); //消息ID，mq 在通道中用于标识消息的 ID，可以用来确认消息已修改
                //消息内容
                String message = new String(body, "UTF-8");
                System.out.println("receive message:" + message);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE, true, defaultConsumer);
    }
}
