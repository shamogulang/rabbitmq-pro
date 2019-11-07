package cn.yishijie.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author chenjianhui on 2019/11/06
 */
public class Receiver {

    //队列名字要和send中的一样
    private final static String QUEUE_NAME = "hello";
    //connectFactory->connection->channel->queueDeclare
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = null;
        try {
             factory.newConnection();
             channel = connection.createChannel();
             channel.queueDeclare(QUEUE_NAME, false, false, false, null);
             System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            //这里不能关闭，否则无法获取消息
            if(channel != null){
                //channel.close();
            }
            if(connection != null){
                //connection.close();
            }
        }
    }
}
