package com.lagou.domain;

import java.util.Date;

/**
 * @author zs
 * @date 2022/6/26 21:14
 * @description
 */
/*
    通过员工表关系转化的员工实体类
 */
public class Employee {

    // 员工号
    private Integer emp_id;

    // 姓名
    private String emp_name;

    // 职位
    private String job_name;

    // 入职时间
    private Date join_date;

    // 联系方式
    private String telephone;

    // 外键
    private Integer dept_id;

    // 部门对象
    private Dept dept;

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", emp_name='" + emp_name + '\'' +
                ", job_name='" + job_name + '\'' +
                ", join_date=" + join_date +
                ", telephone='" + telephone + '\'' +
                ", dept_id=" + dept_id +
                ", dept=" + dept +
                '}';
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
