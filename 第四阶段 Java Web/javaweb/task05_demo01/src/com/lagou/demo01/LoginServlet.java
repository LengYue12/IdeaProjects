package com.lagou.demo01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收用户前端页面输入的用户名和密码信息并打印
        String userName = request.getParameter("userName");
        System.out.println("接收到的用户名为：" + userName);
        String password = request.getParameter("password");
        System.out.println("接受到的密码为：" + password);
        // 2.使用固定的用户名和密码信息来进行登录的校验
        if("admin".equalsIgnoreCase(userName) && "123456".equals(password)){
            System.out.println("登录成功，欢迎使用！");
            // 存储用户名信息
            request.getSession().setAttribute("userName",userName);
            response.sendRedirect("main.jsp");
        } else {
            System.out.println("用户名或密码错误，请重新输入！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
