package com.lagou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject security-demo
 * @Author lengy
 * @CreateTime 2022/8/17 22:34
 * @Description Spring security 入门
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello,security！";
    }
}
