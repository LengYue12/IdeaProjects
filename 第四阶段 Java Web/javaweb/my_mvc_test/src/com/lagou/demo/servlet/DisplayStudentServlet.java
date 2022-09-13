package com.lagou.demo.servlet;

import com.lagou.demo.entity.Student;
import com.lagou.demo.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 显示学员信息功能的Servlet
 */
@WebServlet(name = "DisplayStudentServlet", urlPatterns = "/display")
public class DisplayStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 创建StudentService类型的对象去实现查询学员信息的功能
        StudentService studentService = new StudentService();
        List<Student> list = studentService.selectAllStudent();

        /**
         * 如果list不为null，就说明显示成功
         * 否则查询失败
         */
        if (list != null) {
            System.out.println("显示成功");
            request.getSession().setAttribute("list",list);
            request.getRequestDispatcher("display.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
