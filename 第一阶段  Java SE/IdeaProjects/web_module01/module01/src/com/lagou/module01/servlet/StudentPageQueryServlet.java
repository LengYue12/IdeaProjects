package com.lagou.module01.servlet;

import com.lagou.module01.entity.PageBean;
import com.lagou.module01.entity.Student;
import com.lagou.module01.service.StudentService;
import com.lagou.module01.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentPageQueryServlet", urlPatterns = "/studentPageQueryServlet")
public class StudentPageQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1.获取前端页面传来的页码数值并构造分页查询的对象
        String page = request.getParameter("page");
        //System.out.println("前台传过来的页码是：" + page);
        // 这里创建默认值为1，也就是第一个默认查询第一页数据内容
        PageBean pageBean = new PageBean(1, 10);
        if (null != page && page.length() > 0) {
            pageBean.setPage(Integer.parseInt(page));
        }
        // 通过Service层以及Dao层获取所有的学员信息
        StudentService studentService = new StudentServiceImpl();
        List<Student> studentList = studentService.studentShowService(pageBean);
        // 将获取到的所有学员信息放到内置对象，通过客户端跳转的方式显示出来
        request.getSession().setAttribute("studentList", studentList);
        response.sendRedirect("manageStudent.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
