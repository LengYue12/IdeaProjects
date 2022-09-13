package ps;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_fanout_queue_1",false,false,false,null);
        // 绑定路由（关注）
        channel.queueBind("test_exchange_fanout_queue_1","test_exchange_fanout","");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_fanout_queue_1",true,consumer);
    }
}
