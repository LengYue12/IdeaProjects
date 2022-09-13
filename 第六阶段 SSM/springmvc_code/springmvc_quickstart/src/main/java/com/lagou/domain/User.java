package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/21 20:07
 * @description
 */
/*
    User实体类
 */
public class User {

    // 属性值要和请求参数的name值保持一致，就可以完成自动映射封装
    private Integer id;
    private String username;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    // springmvc就是通过调用set方法完成值的注入
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
