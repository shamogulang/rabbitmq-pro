package cn.yishijie.helloworld;

import cn.yishijie.common.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author chenjianhui on 2019/11/06
 */
public class Receiver {

    //队列名字要和send中的一样
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = null;
        try {
             channel = connection.createChannel();
             channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" server received message: '" + message + "'");
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
