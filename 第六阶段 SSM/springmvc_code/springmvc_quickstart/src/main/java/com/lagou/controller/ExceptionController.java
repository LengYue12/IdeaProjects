package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zs
 * @date 2022/6/23 20:47
 * @description
 */
@Controller
public class ExceptionController {



    /*
        通过访问Controller里的方法产生异常，最终跳转到异常处理器里面的方法进行处理，并跳转页面
     */
    // 进行访问，出现异常    就会走到自定义异常类 GlobalExceptionResolve 类 里的resolveException 方法进行异常处理
    // 向模型中添加异常信息，最终跳转到error页面，进行异常展示
    @RequestMapping("/testException")
    public String testException(){


        int i = 1/0;

        return "success";
    }
}
