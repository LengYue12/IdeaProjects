package test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class Sender2 {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        // 创建消息的配置对象
        MessageProperties properties = new MessageProperties();
        // 设置过期时间3秒
        properties.setExpiration("3000");
        // 创建消息
        Message message = new Message("测试过期时间".getBytes(),properties);
        rabbitTemplate.convertAndSend("msg.user",message);
            System.out.println("消息已发出...");
        context.close();
    }
}
