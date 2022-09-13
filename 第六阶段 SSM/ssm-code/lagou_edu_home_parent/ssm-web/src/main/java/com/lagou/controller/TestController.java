package com.lagou.controller;

import com.lagou.entity.Test;
import com.lagou.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/28 20:58
 * @description
 */

@RestController // 组合注解，相当于@Controller + @ResponseBody
@RequestMapping("/test")    // 一级目录
public class TestController {

    // 注入service
    @Autowired
    private TestService testService;

    // 当访问到这个方法时，就会查询数据库中的test表的全部记录，并封装成list集合，再把list集合转为JSON响应给页面
    @RequestMapping("/findAllTest")
    public List<Test> findAllTest(){

        return testService.findAllTest();
    }
}
