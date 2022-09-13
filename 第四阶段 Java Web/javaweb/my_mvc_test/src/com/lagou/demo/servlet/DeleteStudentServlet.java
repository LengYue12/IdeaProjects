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
 * 删除学员信息功能的Servlet
 * C层
 */
@WebServlet(name = "DeleteStudentServlet", urlPatterns = "/delete")
public class DeleteStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取前台数据
        int id = Integer.parseInt(request.getParameter("id"));

        // 创建StudentService类型的对象去实现删除学员信息的功能
        StudentService studentService = new StudentService();
        int i = studentService.deleteStudent(id);

        /**
         * 如果i为1，就说明删除成功
         * 否则删除失败
         */
        if (1 == i){
            System.out.println("删除成功");
            request.getSession().setAttribute("delete","删除成功");
        } else {
            System.out.println("删除失败");
            request.getSession().setAttribute("delete","删除失败，请重新输入！");
            // 服务器跳转
            request.getRequestDispatcher("delete.jsp").forward(request,response);
        }

        // 删除信息功能完成后跳转回主界面
        response.sendRedirect("main.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
