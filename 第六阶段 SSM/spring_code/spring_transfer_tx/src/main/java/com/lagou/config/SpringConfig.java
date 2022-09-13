package com.lagou.config;

/**
 * @author zs
 * @date 2022/6/19 21:40
 * @description
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *  让该类变成核心配置类，在该类上添加一些对应的注解把核心配置文件里面的标签替换掉
 */
@Configuration      // 让该类变成核心配置类，声明该类为核心配置类
@ComponentScan("com.lagou")      // 替换IOC注解扫描标签，包扫描
@Import(DataSourceConfig.class)     // 导入其他配置类
// 注解方式的  事务注解驱动的配置
@EnableTransactionManagement        // 事务的注解驱动，借助了这个注解 替换了 <tx:annotation-driven/> 这个标签，开启了事务的注解支持
public class SpringConfig {


    // JdbcTemplate 模板对象
    // 在调用这个方法时，在参数前面添加 @Autowired 这个注解
    // Spring在调用该方法时，就会从IOC容器中取出DataSource类型的实例对象 注入 给参数
    // 根据类型进行注入
    @Bean
    public JdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource){

        return new JdbcTemplate(dataSource);
    }



    // 事务管理器对象
    // 把返回值存到IOC容器中
    @Bean
    public PlatformTransactionManager getPlatformTransactionManager(@Autowired DataSource dataSource){

        return new DataSourceTransactionManager(dataSource);
    }

}
