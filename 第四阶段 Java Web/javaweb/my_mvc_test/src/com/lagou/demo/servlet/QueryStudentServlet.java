package com.lagou.demo.servlet;

import com.lagou.demo.entity.Student;
import com.lagou.demo.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 查询学员信息功能的Servlet
 * C层
 */
@WebServlet(name = "QueryStudentServlet", urlPatterns = "/query")
public class QueryStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取前台数据
        int id = Integer.parseInt(request.getParameter("id"));

        // 创建StudentService类型的对象去实现查询学员信息的功能
        StudentService studentService = new StudentService();
        Student student = studentService.findByIdStudent(id);

        /**
         * 如果student不为null，就说明查询成功
         * 否则查询失败
         */
        if (null != student){
            System.out.println("查询成功");
            request.getSession().setAttribute("student",student);
            request.getRequestDispatcher("query.jsp").forward(request,response);
        } else {
            System.out.println("查询失败");
            // 服务器跳转
            request.setAttribute("error","查询失败，请重新输入！");
            request.getRequestDispatcher("query.jsp").forward(request,response);
        }

        // 查询信息功能完成后跳转回主界面
        response.sendRedirect("main.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
