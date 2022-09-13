package com.lagou.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zs
 * @date 2022/6/15 20:11
 * @description
 */
/*
    事务管理器工具类：包含：开启事务、提交事务、回滚事务、释放资源方法
        通知类
 */

// 使用注解配置AOP方式实现   对转账案列进行优化
@Aspect         // 表明该类为切面类，Spring会为该类实现织入增强，并且生成对应的代理对象
@Component("transactionManager")      // 生成该类的实例存到IOC容器中
@EnableAspectJAutoProxy               // 使用注解方式 开启AOP自动代理
public class TransactionManager {

    @Autowired  // 注入
    private ConnectionUtils connectionUtils;

    /*
        完成切入点和通知的结合
            使用环绕通知方式完成对案列的优化
                使用环绕通知可以借助代码手动控制增强逻辑的执行顺序
     */
    // @Around：环绕注解方式，写切入点表达式
    @Around("execution(* com.lagou.service.impl.AccountServiceImpl.*(..)))")
    // 表示使用该方法对 AccountServiceImpl 里面的任意方法进行环绕通知增强
    // 参数：正在执行的连接点，就是切入点
    public Object around(ProceedingJoinPoint pjp) throws SQLException {

        // 手动控制增强逻辑的执行顺序，就是事务控制相关代码


        // 在调用原方法执行进行事务增强，就是事务控制相关代码
        Object proceed = null;
        try {

            // 事务的开启       开启手动事务
            connectionUtils.getThreadConnection().setAutoCommit(false);

            // 调用原方法        切入点方法执行
            proceed = pjp.proceed();

            // 手动提交事务
            connectionUtils.getThreadConnection().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            // 产生了异常，手动回滚事务
            connectionUtils.getThreadConnection().rollback();
        } finally {
            // 最终执行的 释放资源方法

            // 将手动事务恢复成自动事务
            connectionUtils.getThreadConnection().setAutoCommit(true);

            // 将连接归还到连接池
            connectionUtils.getThreadConnection().close();
            // 解除线程绑定
            connectionUtils.removeThreadConnection();
        }

        return proceed;
    }



    /*
        开启事务
     */
    public void beginTransaction(){

        // 获取Connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            // 开启了一个手动事务，关闭自动提交
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        提交事务
     */
    public void commit(){

        // 获取的是同一个connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        回滚事务
     */
    public void rollback(){

        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        释放资源
     */
    public void release(){

        // 将手动事务改回成自动提交事务
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.setAutoCommit(true);

            // 将连接归还到连接池
            connectionUtils.getThreadConnection().close();
            // 解除线程绑定
            connectionUtils.removeThreadConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
