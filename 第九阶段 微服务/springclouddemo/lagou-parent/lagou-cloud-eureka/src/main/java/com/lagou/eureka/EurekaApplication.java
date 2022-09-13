package com.lagou.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/22 14:40
 * @Description
 */
@SpringBootApplication
// 标识当前项目为Eureka Server
@EnableEurekaServer // 标识当前启动类所在的项目的就是一个 Eureka Server
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
