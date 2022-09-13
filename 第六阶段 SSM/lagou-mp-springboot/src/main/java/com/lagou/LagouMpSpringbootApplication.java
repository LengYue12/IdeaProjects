package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 根据包名去该包下找到对应的mapper接口产生代理对象存到容器中
@MapperScan("com.lagou.mapper") // 扫描mapper接口
public class LagouMpSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouMpSpringbootApplication.class, args);
    }

}
