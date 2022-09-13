package com.lagou.controller;

import com.lagou.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 20:12
 * @Description
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/boot")
    public String hello(){

        return "Hello Spring Boot!";
    }

    @RequestMapping("/jdbc")
    public String jdbc(){
        return jdbcTemplate.toString();
    }


    @Autowired
    private Person person;

    @RequestMapping("/person")
    public String showPersonInfo(){

        return person.toString();
    }
}
