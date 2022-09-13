package com.lagou.module01.test;

import com.lagou.module01.entity.Student;
import com.lagou.module01.service.StudentServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * 编程实现StudentService接口的测试
 */
public class StudentServiceTest {

    public static void main(String[] args) {

        StudentServiceImpl studentService = new StudentServiceImpl();
        // 1.查询所有学生信息的测试
        List<Student> studentList = studentService.studentShowService(null);
        System.out.println("读取到的学生信息有：");
        for (Student student : studentList) {
            System.out.println(student);
        }

        System.out.println("-------------------------------------------");
        // 2.增加学生信息的测试
        int res = studentService.studentAddService(new Student("guanyu", "1", new Date(), "guanyu@163.com", "Just Do It!"));
        if (0 != res) {
            System.out.println("添加学生信息成功！");
        } else {
            System.out.println("添加学生失败！");
        }

        System.out.println("-------------------------------------------");
        // 3.查找学生信息的测试
        Student student = studentService.studentFindService(1);
        if (null != student) {
            System.out.println("查找到的学生信息是：" + student);
        } else {
            System.out.println("查找学生失败！");
        }

        System.out.println("-------------------------------------------");
        // 4.修改学生信息的测试
        res = studentService.studentModifyService(new Student(2, "guanyu", "1", new Date(), "guanyu@163.com", "Give You Some Color!"));
        if (0 != res) {
            System.out.println("修改学生信息成功！");
        } else {
            System.out.println("修改学生信息失败！");
        }

        System.out.println("-------------------------------------------");
        // 4.删除学生信息的测试
        String[] ids = {"1", "2", "3"};
        res = studentService.studentDeleteService(ids);
        if (0 != res) {
            System.out.println("删除学生成功！");
        } else {
            System.out.println("删除学生失败！");
        }
    }
}
