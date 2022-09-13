package com.lagou.module01.servlet;

import com.lagou.module01.entity.Student;
import com.lagou.module01.service.StudentService;
import com.lagou.module01.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentQueryServlet", urlPatterns = "/studentQueryServlet")
public class StudentQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码的问题
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前端页面传递过来的学号
        String id = request.getParameter("id");

        // 2.通过Service层实现查找工程
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.studentFindService(Integer.parseInt(id));

        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        List<Student> studentList = new ArrayList<>();
        if(null != student) {
            // 3.判断查找成功时重新回到主页面
            // 将获取到的所有学员信息放到内置对象，通过客户端跳转的方式显示出来
            studentList.add(student);
            request.getSession().setAttribute("studentList", studentList);
            writer.print("<script>alert('查找学生成功！'); location.href='manageStudent.jsp';</script>");
        }else{
            request.getSession().setAttribute("studentList", studentList);
            writer.print("<script>alert('查找学生失败！'); location.href='manageStudent.jsp';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
