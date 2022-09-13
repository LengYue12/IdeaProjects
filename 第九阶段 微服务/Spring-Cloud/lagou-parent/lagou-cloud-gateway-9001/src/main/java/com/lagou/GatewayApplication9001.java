package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 19:35
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication9001 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication9001.class,args);
    }
}
