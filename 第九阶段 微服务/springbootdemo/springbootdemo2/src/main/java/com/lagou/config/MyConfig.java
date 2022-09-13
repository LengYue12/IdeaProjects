package com.lagou.config;

import com.lagou.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 22:20
 * @Description
 */
@Configuration  // 标识当前类是一个配置类，SpringBoot会扫描该类，将所有标识@Bean注解的方法的返回值注入到容器中
public class MyConfig {


    @Bean   // 注入的名称就是方法的名称,注入的类型就是返回值的类型
    public MyService myService(){
        return new MyService();
    }

    @Bean("service_")
    public MyService myService2(){
        return new MyService();
    }
}
