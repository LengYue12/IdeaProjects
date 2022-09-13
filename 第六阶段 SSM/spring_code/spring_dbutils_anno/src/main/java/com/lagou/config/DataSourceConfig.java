package com.lagou.config;

/**
 * @author zs
 * @date 2022/6/12 19:29
 * @description
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 *  数据源信息的配置类
 */
// @PropertySource()    这个注解用于 加载properties文件中的配置       需要去指定要加载的properties文件路径  其实就是标签中的location 的值
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {

    // 借助@Value 注解，把jdbc.properties 里面的内容引入到成员变量中
    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;


    // 通过使用 @Bean注解  注解方法 获取返回值的方式   把DataSource 这个实例对象存到IOC容器中

    // @Bean   对于jar包中的类，采用这个注解  去配置非自定义的类的配置
    // 使用在方法上，会把该方法的返回值村到 IOC容器中

    @Bean("dataSource")     // 起key值，不起的话  对象在IOC容器中对应的key就是方法名
    public DataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        // 返回druidDataSource    @Bean注解 就会把返回值放进IOC容器中
        return druidDataSource;
    }
}
