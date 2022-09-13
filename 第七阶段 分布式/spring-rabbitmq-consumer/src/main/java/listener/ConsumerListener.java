package listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject spring-rabbitmq-consumer
 * @Author lengy
 * @CreateTime 2022/7/27 20:39
 * @Description 消费者监听队列
 */
@Component
public class ConsumerListener extends AbstractAdaptableMessageListener {


    // Jackson提供序列化和反序列化中使用最多的类，用来转换json的
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 将message对象转换成json
        try {
//            JsonNode jsonNode = MAPPER.readTree(message.getBody());
//            String name = jsonNode.get("name").asText();
//            String email = jsonNode.get("email").asText();
//            System.out.println("从队列中获取：【" + name + "的邮箱是：" + email + "】");

            // 手动确认消息(参数1，参数2)
            /**
             * 参数1：RabbitMQ向该channel投递的这条消息的唯一标识id，此id是一个单调递增的正整数
             * 参数2：为了减少网络流量，手动确认可以被批量处理，当该参数为true时，则可以一次性确认msgId小于等于传入值的所有消息
             */
            String s = new String(message.getBody());
            System.out.println("s = " + s);
            long msgId = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(msgId,true);

            Thread.sleep(3000);
            System.out.println("休息3秒后，再继续接收消息！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
