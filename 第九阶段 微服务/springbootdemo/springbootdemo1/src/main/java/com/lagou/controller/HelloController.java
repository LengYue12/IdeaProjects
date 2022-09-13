package com.lagou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 19:41
 * @Description
 */
@RestController     // 标识这个Controller中返回JSON
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/boot")
    public String helloBoot(){

        return "Hello Spring Boot！！！";
    }
}
