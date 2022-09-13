package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 19:57
 * @Description
 */
@Controller
@RequestMapping("/page")
public class IndexController {


    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }
}
