package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 使用的Mybatis，扫描com.lagou.mapper
@MapperScan("com.lagou.mapper")
public class Springbootdemo5Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo5Application.class, args);
    }

}
