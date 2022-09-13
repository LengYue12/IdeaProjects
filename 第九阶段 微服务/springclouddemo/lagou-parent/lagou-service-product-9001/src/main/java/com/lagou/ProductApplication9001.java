package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 19:20
 * @Description
 */
@SpringBootApplication
//@EnableEurekaClient // 表示将当前项目作为 Eureka Client 注册到Eureka Server，只能在Eureka环境中使用
@EnableDiscoveryClient  // 也是将当前项目标识为注册中心的客户端，向注册中心进行注册，可以在所有的服务注册中心环境下使用
@MapperScan("com.lagou.mapper")
public class ProductApplication9001 {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication9001.class,args);
    }
}
