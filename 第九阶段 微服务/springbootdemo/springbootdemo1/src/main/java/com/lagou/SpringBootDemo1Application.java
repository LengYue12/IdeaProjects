package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 19:46
 * @Description SpringBoot的启动类，通常放在二级包中，比如  com.lagou.SpringBootDemo1Application
 * 因为SpringBoot项目在做包扫描时，会扫描启动类所在的包及其子包下的所有内容
 */

// 标识当前类为SpringBoot项目的启动类
@SpringBootApplication
public class SpringBootDemo1Application {

    public static void main(String[] args) {
        // 样板代码
        // SpringApplication.run(当前类的字节码文件,main函数的方法参数);
        SpringApplication.run(SpringBootDemo1Application.class,args);
    }
}
