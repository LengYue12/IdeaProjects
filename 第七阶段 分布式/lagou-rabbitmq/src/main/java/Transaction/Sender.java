package Transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型，持久化）
        // topic:模糊匹配的定向分发
        channel.exchangeDeclare("test_transaction","topic");
        channel.txSelect(); // 开启事务
        try {
            channel.basicPublish("test_transaction","product.price", null,"商品1降价".getBytes());
            System.out.println(1/0);
            channel.basicPublish("test_transaction","product.price", null,"商品2降价".getBytes());
            System.out.println("【生产者：消息已全部发送！】");

            channel.txCommit(); // 提交事务  一起成功
        } catch (Exception e) {
            System.out.println("消息全部撤销!");
            channel.txRollback();   // 事务回滚（一起失败）
            e.printStackTrace();
        } finally {

        channel.close();
        connection.close();
        }

    }
}
