package com.lagou.demo01.demo02;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ContextServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.配置参数的获取
        ServletContext servletContext = getServletConfig().getServletContext();
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String s = initParameterNames.nextElement();
            System.out.println( s + "对应的值为：" + servletContext.getInitParameter(s));
        }

        System.out.println("--------------------------------------------");
        // 2.相关路径的获取
        // 本质上就是获取工程路径      /工程名
        System.out.println( "获取上下文关联的路径信息为：" + getServletContext().getContextPath());   // /task01_demo02

        // 在服务器被解析为：http://ip地址:端口号/工程名称    获取实际路径信息
        // 获取到的是部署工程路径信息    对应  当前工程中的web目录
        String realPath = getServletContext().getRealPath("/");
        // C:\Users\zs\IdeaProjects\javaweb\out\artifacts\task01_demo02_war_exploded\
        System.out.println("获取到的实际路径信息为：" + realPath);
        System.out.println("--------------------------------------------");

        // 3.设置和获取属性信息
        getServletContext().setAttribute("key","value");
        System.out.println( "根据参数指定的属性名获取到的属性值为：" + getServletContext().getAttribute("key"));   // value
        getServletContext().removeAttribute("key");
        System.out.println( "根据参数指定的属性名获取到的属性值为：" + getServletContext().getAttribute("key"));   // null

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
