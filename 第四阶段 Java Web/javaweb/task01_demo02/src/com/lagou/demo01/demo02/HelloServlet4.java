package com.lagou.demo01.demo02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet4", urlPatterns = "/hello4")
public class HelloServlet4 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Post请求方式...");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get请求方式...");
        this.doPost(request,response);
    }

    /*@Override
    protected void com.lagou.demo.service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("原来使用注解可以如此潇洒...");
        String method = req.getMethod();
        System.out.println("获取到的请求方式为：" + method);
        if ("get".equalsIgnoreCase(method)){
            doGet(req,resp);
        }
        if ("post".equalsIgnoreCase(method)){
            doPost(req,resp);
        }
    }*/
}
