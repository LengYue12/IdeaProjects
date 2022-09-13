package com.lagou.module01.servlet;

import com.lagou.module01.service.StudentService;
import com.lagou.module01.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "StudentDeleteServlet", urlPatterns = "/studentDeleteServlet")
public class StudentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码的问题
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前台页面传递过来的学号
        String[] ids = request.getParameterValues("id");

        // 2.通过Service层实现删除操作
        StudentService studentService = new StudentServiceImpl();
        int res = studentService.studentDeleteService(ids);

        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/studentPageQueryServlet";
        if(0 != res) {
            writer.print("<script>alert('添加学生成功！'); location.href='" + hrefPath
                    + "';</script>");
        }else{
            writer.print("<script>alert('添加学生失败！'); location.href='" + hrefPath
                    + "';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
