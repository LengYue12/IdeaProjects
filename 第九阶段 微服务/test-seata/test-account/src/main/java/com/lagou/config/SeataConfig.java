package com.lagou.config;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 15:29
 * @Description
 */
import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @BelongsProject: test-seata
 * @Author: GuoAn.Sun
 * @CreateTime: 2021-03-22 16:21
 * @Description:
 */
@Configuration
public class SeataConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }

    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSource(DataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

}