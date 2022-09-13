package com.lagou.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @BelongsProject mongo-com.lagou.test
 * @Author lengy
 * @CreateTime 2022/8/1 22:25
 * @Description 员工实体类
 */
@Document(collection = "emps")  // 指明集合名
// 实体类对应的集合
public class Emp implements Serializable {

    private String id;
    private String name;
    private String sex;
    private double age;
    private String job;
    private double salary;

    public Emp() {
    }

    public Emp(String name, String sex, double age, String job, double salary) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.job = job;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Emp{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", sex='" + sex + '\'' +
            ", age=" + age +
            ", job='" + job + '\'' +
            ", salary=" + salary +
            '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
