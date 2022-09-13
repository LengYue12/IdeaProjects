package util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 16:52
 * @Description 专门与RabbitMQ获得连接
 */
public class ConnectionUtil {

    public static Connection getConnection() throws Exception {

        // 1.创建 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 2.在工厂对象中设置MQ的连接信息（ip，port，vhost，username，password）
        connectionFactory.setHost("192.168.114.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/lagou");
        connectionFactory.setUsername("lengyue");
        connectionFactory.setPassword("123456");

        // 3.通过工厂获得与MQ的连接
        Connection connection = connectionFactory.newConnection();
        return connection;

    }


    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();

        System.out.println("connection = " + connection);

        connection.close();
    }
}
