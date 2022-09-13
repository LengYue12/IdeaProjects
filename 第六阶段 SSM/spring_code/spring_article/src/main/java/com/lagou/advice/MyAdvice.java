package com.lagou.advice;

import org.springframework.stereotype.Component;

/**
 * @author zs
 * @date 2022/6/20 20:46
 * @description
 */
/*
    通知类
 */
@Component("myAdvice")      // 将该类创建权交给Spring
public class MyAdvice {

    /*
        前置通知所对应的方法
     */
    public void before(){

        System.out.println("前置增强");
    }
}
