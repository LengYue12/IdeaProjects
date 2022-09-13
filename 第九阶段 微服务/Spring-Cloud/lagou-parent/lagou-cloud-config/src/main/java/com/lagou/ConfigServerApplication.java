package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/24 18:09
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
// 标识lagou-cloud-config 就是配置中心的服务器
@EnableConfigServer // 开启配置服务器功能
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}
