package com.lagou.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author zs
 * @date 2022/6/16 20:20
 * @description
 */
/*
    通知类
 */
@Component      // 将该类创建权交给Spring
// 使用@Aspect注解：标注切面类。Spring会 根据 @Aspect 这个注解 帮助我们完成织入增强，并且生成代理

@Aspect         // 升级为切面类：配置切入点和通知的关系
public class MyAdvice {

    // 使用 @Pointcut注解的方式来对切点表达式进行抽取
    // 在当前通知类中 添加方法，在方法上借助 @Pointcut 这个注解，把切点表达式进行抽取

    // 在@Pointcut 这个注解中配置要去抽取的表达式
    // 相当于把重复用到的切点表达式抽取到注解中
    @Pointcut("execution(* com.lagou.service.impl.AccountServiceImpl.*(..))")
    public void myPointcut(){

    }


    // 使用@Before等注解  来标注通知方法
    // execution(切入点表达式)    对AccountServiceImpl 这个类里面的任意类型，任意个数的参数 的方法进行增强

    // 借助注解进行织入。表示要使用before这个方法 来对这个切点方法进行前置增强

    // @Before("MyAdvice.myPointcut()"):相当于引用了 抽取的切点表达式
    // 引用的时候，就写     类名.方法名
//    @Before("MyAdvice.myPointcut()")
//    public void before(){
//        System.out.println("前置通知执行了...");
//    }
//
//
//    // 对切点方法进行的后置通知
//    @AfterReturning("MyAdvice.myPointcut()")
//    public void afterReturning(){
//        System.out.println("后置通知执行了...");
//    }
//
//
//
//    // 异常通知
//    @AfterThrowing("MyAdvice.myPointcut()")
//    public void afterThrowing(){
//        System.out.println("异常通知执行了...");
//    }
//
//
//    // 最终通知
//    @After("MyAdvice.myPointcut()")
//    public void after(){
//        System.out.println("最终通知执行了...");
//    }


    // 当使用 环绕通知时，该通知要单独使用
    // 使用环绕通知，可以手动控制通知的执行顺序
    // 所以非要使用注解AOP开发的话，因为原生注解存在执行顺序的问题。
    // 那么就可以通过环绕通知来进行解决，手动在代码中控制通知的执行顺序
    @Around("MyAdvice.myPointcut()")
    public Object around(ProceedingJoinPoint pjp){


        // 切点对象调用方法，让目标方法执行
        Object proceed = null;
        try {
            System.out.println("前置通知执行了...");
            proceed = pjp.proceed();
            System.out.println("后置通知执行了...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知执行了...");
        } finally {
            System.out.println("最终通知执行了...");
        }

        return proceed;
    }
}
