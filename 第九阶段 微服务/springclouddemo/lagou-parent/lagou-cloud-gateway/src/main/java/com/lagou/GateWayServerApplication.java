package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/24 12:54
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient  // 注册到Eureka
public class GateWayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayServerApplication.class,args);
    }
}
