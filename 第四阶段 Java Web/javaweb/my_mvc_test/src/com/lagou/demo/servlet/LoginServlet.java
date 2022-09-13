package com.lagou.demo.servlet;

import com.lagou.demo.entity.User;
import com.lagou.demo.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建UserService类型的对象去实现数据的校验功能
        UserService userService = new UserService();
        User user = userService.userLoginService(new User(1, request.getParameter("userName"), request.getParameter("pwd")));
        /**
         * 如果user为空，说明账号或密码错误，就跳转到登录页面
         * 如果不为空，说明账号和密码正确，就跳转到主界面
         */
        if (null != user) {
            System.out.println("登录成功，欢迎使用！");
            // 存储用户名信息
            request.getSession().setAttribute("user",user);
            // 实现7天免登录
            // 创建Cookie对象用于存放用户名信息
            Cookie cookie = new Cookie("userName",request.getParameter("userName"));
            // 设置cookie一周内有效
            cookie.setMaxAge(3600*24*7);
            // 将cookie信息返回给浏览器存储起来
            response.addCookie(cookie);
            // 客户端跳转，访问主界面
            response.sendRedirect("mainPage.jsp");
        } else {
            System.out.println("登录失败，用户名或密码错误！");
            request.setAttribute("error","登录失败，用户名或密码错误！");
            // 服务器跳转
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
