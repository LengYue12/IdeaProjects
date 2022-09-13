package work;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消费者2
 */
public class Recer2 {

    static int i = 1;   // 统计吃掉羊肉串的数量

    public static void main(String[] args) throws Exception {

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.获得通道（信道）
        Channel channel = connection.createChannel();
        // queueDeclare 此方法有双重作用，如果队列不存在则创建，如果队列存在，则获取
        channel.queueDeclare("test_work_queue",false,false,false,null);
        channel.basicQos(1);
        // 3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override   // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【顾客2】吃掉" + s + "！！总共吃【" + i++ + "】串！");
                // 模拟网络延迟
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 手动确认（收件人信息，是否同时确认多个消息）
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 4.监听队列   false:手动消息确认
        channel.basicConsume("test_work_queue",false,consumer);
    }
}
