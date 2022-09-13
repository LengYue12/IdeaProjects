package com.lagou.demo.entity;

/**
 * 学生类
 * M层
 */
public class Student {

    private int id;
    private String name;
    private String sex;
    private String date;
    private String email;
    private String remarks;

    // 外键，所属班级编号
    private int classGradeId;
    // 详细的班级信息
    private ClassGrade classGrade;

    public Student() {
    }

    public Student(int id, String name, String sex, String date, String email, String remarks, int classGradeId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.email = email;
        this.remarks = remarks;
        this.classGradeId = classGradeId;
    }

    public Student(String name, String sex, String date, String email, String remarks, int classGradeId) {
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.email = email;
        this.remarks = remarks;
        this.classGradeId = classGradeId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getClassGradeId() {
        return classGradeId;
    }

    public void setClassGradeId(int classGradeId) {
        this.classGradeId = classGradeId;
    }

    public ClassGrade getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(ClassGrade classGrade) {
        this.classGrade = classGrade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                ", classGradeId=" + classGradeId +
                '}';
    }
}
