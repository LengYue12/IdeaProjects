package com.lagou.config;

/**
 * @author zs
 * @date 2022/6/12 18:46
 * @description
 */

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 *  核心配置类           替换掉applicationContext.xml 核心配置文件
 *     被 @Configuration 标注的类就为核心配置类
 */
@Configuration
// @ComponentScan   这个注解用于指定 Spring 在初始化容器时要扫描的包
// 指定属性，要对哪个包进行扫描。 表示会扫描该包及其子包下的所有类，让注解生效
@ComponentScan("com.lagou")
// 加载SpringConfig 这个核心配置类的同时，也把DataSourceConfig 这个类也引入加载   就要使用@Import注解，导入其他配置类
@Import(DataSourceConfig.class)
public class SpringConfig {

    // 配置QueryRunner  这个Bean
    @Bean("qr")         // 默认，方法的名字作为key，返回值作为value存到IOC容器          给值自定义key


    // 因为在QueryRunner 实例对象中 需要用dataSource 实例对象。
    // 所以需要注入，声明一个参数。这个方法由Spring调用
    // Spring 在调用时，就会把在 IOC容器中 已经生成的dataSource 实例对象当做参数来进行传递

    // 所以要 添加 @Autowired 注解    告诉Spring，根据DataSource 这个类型，从IOC容器中找到对应的实例对象 并注入给参数


    // @Autowired DataSource dataSource  这个 就相当于 在核心配置文件中 配置的constructor-arg 标签  有参方式依赖注入
    public QueryRunner getQueryRunner(@Autowired DataSource dataSource){

        // 根据传递进来dataSource 来创建QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }
}
