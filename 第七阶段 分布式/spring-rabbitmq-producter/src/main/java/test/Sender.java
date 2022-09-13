package test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) {

        // 1.创建spring容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter.xml");
        // 2.从容器中获得 rabbit模板对象 拿到模板类，使用模板类进行消息的发送
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // 3.发消息
        Map<String,String> map = new HashMap<>();
        map.put("name","卢布");
        map.put("email","4455588@qq.com");
//        for (int i = 1; i <= 10; i++){
            rabbitTemplate.convertAndSend("msg.user",map);
            System.out.println("消息已发出...");
//        }
        context.close();
    }
}
