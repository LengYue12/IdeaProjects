package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {SpringBootConfiguration.class})
@SpringBootApplication
//@MapperScan(basePackages = "com.lagou.dao")
public class RunBoot {

    public static void main(String[] args) {
        SpringApplication.run(RunBoot.class,args);
    }

}
