package com.lagou.config;

/**
 * @author zs
 * @date 2022/6/19 21:46
 * @description
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 *  关于数据库的一些配置信息的配置类
 */
@PropertySource("classpath:jdbc.properties")     // 加载对应的jdbc.properties 文件
public class DataSourceConfig {

    // 注入从jdbc.properties文件中读取到的value值
    @Value("${jdbc.driverClassName}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    // 由Spring加载核心配置类时，调用getDataSource方法，获取返回值，将返回值存到IOC容器中
    @Bean   // 会把当前方法的返回值对象放进IOC容器中
    public DataSource getDataSource(){

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        return druidDataSource;
    }

}
