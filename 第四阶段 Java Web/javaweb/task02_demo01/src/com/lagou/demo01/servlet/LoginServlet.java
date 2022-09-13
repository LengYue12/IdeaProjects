package com.lagou.demo01.servlet;

import com.lagou.demo01.dao.UserDao;
import com.lagou.demo01.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取请求对象中保存的用户名和密码信息
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println("获取到的用户名为：" + userName + "，获取到的密码为：" + password);
        // 2.将接收到的用户名和密码信息打包成用户对象交给DAO层进行处理
        User user = new User(userName,password);
        UserDao userDao = new UserDao();
        Boolean b1 = userDao.loginUser(user);
        // 3.将处理结果响应到浏览器
        response.setContentType("text/html;charset=utf-8");
        if (b1) {
            System.out.println("登录成功！");
            response.getWriter().write("<h1>登录成功！</h1>");
        } else {
            System.out.println("登录失败！");
            response.getWriter().write("<h1>登录失败！</h1>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
