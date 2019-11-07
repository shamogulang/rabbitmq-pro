package cn.yishijie.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjianhui on 2019/11/06
 */
public class Send {

    //消息队列的名字
    private final static String QUEUE_NAME = "hello";

    //connectFactory->connection->channel->queueDeclare
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
            /**
             * 频道声明队列：
             *  queueDeclare()不带参数方法默认创建一个由RabbitMq命名的（amq.gen-LHQZz...）
             * 名称，这种队列也称之为匿名队列，排他 的，自动删除的，非持久化的队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
             * queue:队列名称
             * durable：是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，
             * 保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
             * exclusive ： 是否排他的，true，排他。如果一个队列声明为排他队列，该队列公对首次声明它的连接可见，并在连接断开时自动删除，
             * 排序是基于连接的Connection可见的，同一个连接的不同信道是可以同时访问同一个连接创建的排他队列，
             * 首次--是指如果一个连接已经声明了一个排他队列，其它连接是不允许建立同名的排他队列，这个与普通队列不同，即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除，这个队列适用于一个客户端同是发送和读取消息的应用场景
             * autoDelete ：是否自动删除,true，自动删除，自动删除的前提：至少有一个消息者连接到这个队列，之后所有与这个队列连接的消息都断开时，才会自动删除，，
             * 备注：生产者客户端创建这个队列，或者没有消息者客户端连接这个队列时，不会自动删除这个队列
             * arguments：用于设置队列消息的一些规则，比如x-message-ttl表示队列中的消息什么时候会自动被清除
             */
            Map<String, Object> arguments = new HashMap<>();
            //arguments.put("x-message-ttl", 100000);//设置消息10秒钟后自动清除
            channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);
            String message = "Hello World!";
            /**
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             * 第三个参数props：设置投递模式为持久化，如果此值是persistent
             * ，则此消息存储在磁盘上。如果服务器重启，系统会保证收到的持久化消息未丢失，将消息以持久化方式发布时，会对性能造成一定的影响
             * channel.basicPublish("", TASK_QUEUE_NAME,
             *                     MessageProperties.PERSISTENT_TEXT_PLAIN,
             *                     message.getBytes("UTF-8"));
             */
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
