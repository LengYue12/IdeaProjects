package direct;

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

        // 声明路由(路由名，路由类型）
        // direct:根据路由键进行定向分发消息
        channel.exchangeDeclare("test_exchange_direct","direct");

        String msg = "查询用户";
        channel.basicPublish("test_exchange_direct","select",null,msg.getBytes());
        System.out.println("【用户系统】：" + msg);

        channel.close();
        connection.close();

    }
}
