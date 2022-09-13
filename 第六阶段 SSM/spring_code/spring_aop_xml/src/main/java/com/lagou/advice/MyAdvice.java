package com.lagou.advice;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author zs
 * @date 2022/6/16 17:27
 * @description
 */
/*
    通知类
 */
public class MyAdvice {


    /*
        前置通知所对应的方法
     */
    public void before(){

        System.out.println("前置通知执行了...");
    }

    /*
        后置通知所对应的方法
     */
    public void afterReturning(){

        System.out.println("后置通知执行了...");
    }


    /*
        异常通知所对应的方法
     */
    public void afterThrowing(){

        System.out.println("异常通知执行了...");
    }

    /*
        最终通知所对应的方法
     */
    public void after(){
        System.out.println("最终通知执行了...");
    }



    /*
        环绕通知所对应的方法
            环绕通知是一种可以通过手动控制增强代码在什么时候执行的通知

                环绕通知是Spring提供的一种手动可以通过代码控制增强业务逻辑执行顺序的方式
     */
    // Proceeding JoinPoint（切点对象）  ：正在执行的连接点：切点
    public Object around(ProceedingJoinPoint pjp){

        // 切点方法执行
        Object proceed = null;
        // 手动通过代码的方式去控制执行顺序
        try {
            System.out.println("前置通知执行了");
            proceed = pjp.proceed();
            System.out.println("后置通知执行了");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知执行了");
        } finally {
            System.out.println("最终通知执行了");
        }

        return proceed;
    }
}
