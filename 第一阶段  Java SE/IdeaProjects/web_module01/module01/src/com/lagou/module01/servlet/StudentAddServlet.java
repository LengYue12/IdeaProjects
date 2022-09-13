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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "StudentAddServlet", urlPatterns = "/studentAddServlet")
public class StudentAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码的问题
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前台页面发过来的标志位和数据内容
        String flag = request.getParameter("flag");
        //System.out.println("获取到的标志位是：" + flag);
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String mail = request.getParameter("mail");
        String comments = request.getParameter("comments");

        // 2.打包数据后通过Service层实现对应的功能
        //System.out.println("数据打包完成！");
        StudentService studentService = new StudentServiceImpl();
        // 根据隐藏域的结果分别实现增加和修改的功能
        int res = 0;
        Student student = null;
        if ("add".equals(flag)) {
            student = new Student(name, sex, date, mail, comments);
            res = studentService.studentAddService(student);
            //System.out.println("插入数据的结果为：" + res);
        } else {
            student = new Student(Integer.parseInt(flag), name, sex, date, mail, comments);
            res = studentService.studentModifyService(student);
            System.out.println("修改数据的结果为：" + res);
        }

        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/studentPageQueryServlet";
        if(0 != res) {
            writer.print("<script>alert('管理学生信息成功！'); location.href='" + hrefPath
                    + "';</script>");
        }else{
            writer.print("<script>alert('管理学生信息失败！'); location.href='" + hrefPath
                    + "';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
