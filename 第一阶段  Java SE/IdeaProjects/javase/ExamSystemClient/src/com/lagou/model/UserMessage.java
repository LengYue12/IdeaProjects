package com.lagou.model;

import java.io.Serializable;

public class UserMessage implements Serializable {
    private static final long serialVersionUID = -4729638835655094399L;

    private String type; // 消息的类型代表具体的业务
    private User user;   // 消息的具体内容

    public UserMessage() {
    }

    public UserMessage(String type, User user) {
        this.type = type;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}
