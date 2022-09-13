package com.lagou.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -1225113970581096678L;

    private String userName; // 用于描述用户名信息
    private String password; // 用于描述密码信息

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
