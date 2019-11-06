package cn.yishijie.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author chenjianhui on 2019/11/06
 */
public class Send {

    //消息队列的名字
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = null;
        Channel channel = null;
        try {
            //设置mq服务器的host
            factory.setHost("localhost");
            //创建连接
            connection = factory.newConnection();
            //创建channel
            channel = connection.createChannel();
            //频道绑定queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            //发送消息到服务器
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("jeff chan Sent '" + message + "'");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        }

    }
}
