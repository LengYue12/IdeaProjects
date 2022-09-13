package com.lagou.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 22:10
 * @Description
 */
@Component
@PropertySource("classpath:my.properties") // 通过该注解加载外部的自定义的配置文件，参数是配置文件的路径
@ConfigurationProperties(prefix = "product")
public class Product {

    private int id;
    private String name;

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
