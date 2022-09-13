package com.lagou.module01.servlet;

import com.lagou.module01.entity.User;
import com.lagou.module01.service.UserService;
import com.lagou.module01.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminLoginServlet", urlPatterns = "/adminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取请求中的用户名和密码信息并打印出来
        String userName = request.getParameter("userName");
        //System.out.println("获取到的用户名为：" + userName);
        String password = request.getParameter("password");
        //System.out.println("获取到的密码为：" + password);
        // 2.创建UserService类型的对象去实现数据的校验功能
        UserService userService = new UserServiceImpl();
        User user = userService.userLoginService(new User(userName, password));
        if (null == user) {
            System.out.println("登录失败，用户名或密码错误！");
            request.setAttribute("error", "登录失败，用户名或密码错误！");
            // 实现服务器跳转  共享request和response对象
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.out.println("登录成功，欢迎使用！");
            // 通过服务器跳转的方式 实现内部Servlet之间的跳转
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/studentPageQueryServlet");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
