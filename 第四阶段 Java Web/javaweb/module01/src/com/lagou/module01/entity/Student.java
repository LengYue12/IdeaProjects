package com.lagou.module01.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 编程实现学生类的封装
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -7421583755018957680L;
    private int id;    // 用于描述学号的成员变量
    private String name;  // 用于描述姓名的成员变量
    private String sex;   // 用于描述性别的成员变量
    private Date birthday;// 用于描述出生日期的成员变量
    private String email; // 用于描述邮箱的成员变量
    private String comments;  // 用于描述备注信息的成员变量

    public Student() {
    }

    public Student(int id, String name, String sex, Date birthday, String email, String comments) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.comments = comments;
    }

    public Student(String name, String sex, Date birthday, String email, String comments) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.comments = comments;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcomments() {
        return comments;
    }

    public void setDesc(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", desc='" + comments + '\'' +
                '}';
    }
}
