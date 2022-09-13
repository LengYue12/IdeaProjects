package com.lagou.entity;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.Date;

/*
*  JavaBean 类
* 用来存储数据   成员变量私有 提供get set方法 提供空参    实现序列化接口
*
* Employee 类就对应了 数据库中Employee表
*
*   `eid` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(6) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `empdate` date DEFAULT NULL,
* */
public class Employee implements Serializable {

    // 成员变量的名称与表中的列要一样
    private int eid;

    private String ename;

    private int age;

    private String sex;

    private double salary;

    private Date empdate;

    public Employee() {
    }

    public Employee(int eid, String ename, int age, String sex, double salary, Date empdate) {
        this.eid = eid;
        this.ename = ename;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
        this.empdate = empdate;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getEmpdate() {
        return empdate;
    }

    public void setEmpdate(Date empdate) {
        this.empdate = empdate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid=" + eid +
                ", ename='" + ename + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", salary=" + salary +
                ", empdate=" + empdate +
                '}';
    }
}
