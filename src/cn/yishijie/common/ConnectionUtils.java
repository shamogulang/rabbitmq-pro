package cn.yishijie.common;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author chenjianhui on 2020/06/12
 */
public class ConnectionUtils {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            //设置mq服务器的host
            factory.setHost("localhost");
            connection = factory.newConnection();
        }catch (Exception e){
            ;
        }
        return connection;
    }
}
