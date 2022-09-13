package com.lagou.demo01.entity;

import java.io.Serializable;
import java.util.Date;

/*
*   创建一个JavaBean类
*   用来存储数据  成员变量私有  提高get set方法 提供空参    实现序列化接口
*
* Employee 类就对应了    数据库中Employee表
*
* `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `bonus` double DEFAULT NULL,
  `join_date` date DEFAULT NULL,
*
* */
public class Employee implements Serializable {

    private int id;

    private String name;

    private String gender;

    private double salary;

    private double bonus;

    private Date join_date;

    public Employee() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", join_date=" + join_date +
                '}';
    }
}
