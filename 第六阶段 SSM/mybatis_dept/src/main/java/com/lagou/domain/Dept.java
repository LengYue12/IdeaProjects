package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/26 20:34
 * @description
 */
/*
    部门实体类
 */
public class Dept {

    // 部门号
    private Integer dept_id;
    // 部门名
    private String dept_name;
    // 部门主管
    private String major_name;
    // 联系电话
    private String telephone;
    // 邮箱号
    private String email;

    @Override
    public String toString() {
        return "Dept{" +
                "dept_id=" + dept_id +
                ", dept_name='" + dept_name + '\'' +
                ", major_name='" + major_name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
