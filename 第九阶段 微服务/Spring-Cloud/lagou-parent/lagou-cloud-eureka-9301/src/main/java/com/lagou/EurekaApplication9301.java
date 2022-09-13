package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 15:46
 * @Description
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication9301 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication9301.class,args);
    }
}
