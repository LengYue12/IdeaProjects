package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 21:18
 * @Description
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
//@EnableCircuitBreaker   // 启用熔断服务
@EnableFeignClients // 开启Feign客户端功能，并支持熔断
public class PageApplication9101 {

    public static void main(String[] args) {
        SpringApplication.run(PageApplication9101.class,args);
    }
}
