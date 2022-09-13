package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zs
 * @date 2022/6/23 21:52
 * @description
 */
@Controller     // 生成该类实例存到容器中
public class TargetController {


    @RequestMapping("/target")
    public String targetMethod(){

        System.out.println("目标方法执行了...");

        // 跳转到success页面
        return "success";
    }
}
