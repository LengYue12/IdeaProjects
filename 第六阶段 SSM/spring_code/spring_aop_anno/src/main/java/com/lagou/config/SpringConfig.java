package com.lagou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zs
 * @date 2022/6/16 21:04
 * @description
 */

/**     基于注解的AOP开发
 * 在核心配置类中配置注解把 applicationContext.xml 配置文件替换掉
 *      使用核心配置类把 核心配置文件 替换掉
 */

// 让该类变成核心配置类
@Configuration
// 替换 开启IOC注解扫描的 注解
@ComponentScan("com.lagou")
// 替换 开启AOP的自动代理的标签  的注解
@EnableAspectJAutoProxy     //  开启AOP的自动代理    替代了<aop:aspectj-autoproxy> 这个标签
public class SpringConfig {
}
