package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) {

        //建立连接工厂，设置连接信息
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //创建虚拟主机，RabbitMQ 可以创建多个虚拟主机，每一个虚拟主机相当于一个独立的 MQ，默认的虚拟主机名称为 /
        connectionFactory.setVirtualHost("/");

        //获取连接
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();

            //创建通道,每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();

            /*
             * 声明队列,如果 RabbitMQ 中不存在此队列，则创建一个新的；如果存在，则不创建
             * String queue, 队列名称
             * boolean durable, 是否持久化，如果持久化，mq重启后队列还在
             * boolean exclusive, 是否独占此队列，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
             * boolean autoDelete, 队列不再使用时是否自动删除，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * Map<String, Object> arguments 队列参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */
            channel.queueDeclare(QUEUE, true, false, false, null);

            //发布消息
            String message = "helloworld 王树宏";
            /*
             * String exchange, 交换机，如果不指定将使用mq的默认交换机（设置为""）
             * String routingKey, 路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * BasicProperties props, 消息的属性
             * byte[] body 消息内容
             */
            channel.basicPublish("",QUEUE,null,message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            //关闭通道
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            //关闭通道
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
