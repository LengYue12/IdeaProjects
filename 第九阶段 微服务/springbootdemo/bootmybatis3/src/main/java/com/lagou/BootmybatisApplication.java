package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 添加这个注解后，当启动类启动时，就会去mapper包下查找对应的mapper接口，并且生成对应的动态代理类
@MapperScan("com.lagou.mapper") // 指定扫描mapper的包名
public class BootmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootmybatisApplication.class, args);
    }

}
