package com.lagou.demo.entity;

/**
 * 管理员类
 * M层（封装数据的JavaBean）
 * 作用：定义各数据表的实体类
 * 命名：类名=数据表名，属性名=列名
 * 包含：属性、构造方法（无参 & 有参）、getter & setter
 */
public class User {

    private int id;
    private String userName;
    private String password;

    public User() {
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
