package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/27 22:11
 * @Description
 */
@SpringBootApplication
@MapperScan("com.lagou.mapper")
@EnableDiscoveryClient  //@EnableEurekaClient
public class GoodsApplication9201 {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication9201.class,args);
    }


}
