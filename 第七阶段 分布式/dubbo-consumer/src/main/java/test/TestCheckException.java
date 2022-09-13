package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @BelongsProject dubbo-consumer
 * @Author lengy
 * @CreateTime 2022/7/19 19:58
 * @Description 启动时检查
 */
public class TestCheckException {

    public static void main(String[] args) throws IOException {
        // 初始化spring
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");

        System.in.read();
    }
}
