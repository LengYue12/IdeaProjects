package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @BelongsProject spring-rabbitmq-consumer
 * @Author lengy
 * @CreateTime 2022/7/27 20:51
 * @Description 运行项目
 */
public class TestConsumerRunner {
    public static void main(String[] args) throws IOException {
        // 获得容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-consumer.xml");
        // 让程序一直运行，别终止
        System.in.read();
    }
}
