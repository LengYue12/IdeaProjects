package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
public class PageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class,args);
    }

    // 向容器中注入一个RestTemplate，封装了HttpClient
    @Bean
    @LoadBalanced   // 启用请求的负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
