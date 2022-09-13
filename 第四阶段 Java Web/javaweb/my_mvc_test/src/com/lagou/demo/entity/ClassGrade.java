package com.lagou.demo.entity;

/**
 * 描述班级的实体类
 */
public class ClassGrade {
    // 班级编号
    private int classId;
    // 班级名称
    private String className;
    // 年级
    private String gradeName;
    // 班主任名称
    private String headTeacherName;
    // 班级口号
    private String classSlogan;
    // 班级人数
    private int classNumber;

    public ClassGrade() {
    }

    public ClassGrade(int classId, String className, String gradeName, String headTeacherName, String classSlogan, int classNumber) {
        this.classId = classId;
        this.className = className;
        this.gradeName = gradeName;
        this.headTeacherName = headTeacherName;
        this.classSlogan = classSlogan;
        this.classNumber = classNumber;
    }

    public ClassGrade(String className, String gradeName, String headTeacherName, String classSlogan, int classNumber) {
        this.className = className;
        this.gradeName = gradeName;
        this.headTeacherName = headTeacherName;
        this.classSlogan = classSlogan;
        this.classNumber = classNumber;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getHeadTeacherName() {
        return headTeacherName;
    }

    public void setHeadTeacherName(String headTeacherName) {
        this.headTeacherName = headTeacherName;
    }

    public String getClassSlogan() {
        return classSlogan;
    }

    public void setClassSlogan(String classSlogan) {
        this.classSlogan = classSlogan;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public String toString() {
        return "classGrade{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", headTeacherName='" + headTeacherName + '\'' +
                ", classSlogan='" + classSlogan + '\'' +
                ", classNumber=" + classNumber +
                '}';
    }
}
