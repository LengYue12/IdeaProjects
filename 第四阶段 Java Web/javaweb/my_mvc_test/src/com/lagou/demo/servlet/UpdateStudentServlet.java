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
 * 修改学员信息功能的Servlet
 * C层
 */
@WebServlet(name = "UpdateStudentServlet", urlPatterns = "/update")
public class UpdateStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取前台数据
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String date = request.getParameter("date");
        String email = request.getParameter("email");
        String remarks = request.getParameter("remarks");
        String classGradeId = request.getParameter("classGradeId");

        // 创建StudentService类型的对象去实现修改学员信息的功能
        StudentService studentService = new StudentService();
        int i = studentService.updateStudent(new Student(Integer.parseInt(id),name, sex, date, email, remarks,Integer.parseInt(classGradeId)));

        /**
         * 如果i为1，就说明修改成功
         * 否则修改失败
         */
        if (1 == i){
            System.out.println("修改成功");
            request.getSession().setAttribute("update","修改成功");
        } else {
            System.out.println("修改失败");
            request.getSession().setAttribute("update","修改失败，请重新输入！");
            // 服务器跳转
            request.getRequestDispatcher("update.jsp").forward(request,response);
        }

        // 修改信息功能完成后跳转回主界面
        response.sendRedirect("main.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
