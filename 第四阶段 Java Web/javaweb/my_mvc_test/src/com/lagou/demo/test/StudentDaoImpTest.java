package com.lagou.demo.test;

import com.lagou.demo.entity.Student;
import com.lagou.demo.service.StudentService;

import java.util.List;

public class StudentDaoImpTest {

    public static void main(String[] args) {

        StudentService studentService = new StudentService();
//        int i = studentService.insertStudent(new Student(1, "张飞", "男", "2020-11-09", "1234@qq.com", "武将"));
        List<Student> list = studentService.selectAllStudent();
        System.out.println(list);
    }
}
