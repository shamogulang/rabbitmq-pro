package cn.yishijie.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author chenjianhui on 2019/11/07
 */
public class Publish {

    private static final String EXCHANGE_NAME = "logs";

    //发布订阅开启两个订阅者，当一个发布者push消息的时候，两个订阅者都会收到消息
    //fanout能进行广播
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //定义交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "publish subscribe...";

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
