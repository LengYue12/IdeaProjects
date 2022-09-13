package test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class SenderDLX {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter-dlx.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("dlx_ttl","超时，关闭订单".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度1".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度2".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度3".getBytes());
        context.close();
    }
}
