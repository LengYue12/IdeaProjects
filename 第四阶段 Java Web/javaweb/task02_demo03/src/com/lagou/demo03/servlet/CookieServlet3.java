package com.lagou.demo03.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieServlet3",urlPatterns = "/cookie3")
public class CookieServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取客户端发来的Cookie信息并打印出来
        Cookie[] cookies = request.getCookies();
//        System.out.println("获取到的Cookie信息有：");
        for (Cookie cookie : cookies) {
            // 2.当获取到的Cookie对象的名字为name时，将对应的数值修改为guanyu并添加到响应信息中
            if ("name".equalsIgnoreCase(cookie.getName())){
                cookie.setValue("guanyu");
                response.addCookie(cookie);
                break;
            }
        }
        System.out.println("修改Cookie信息成功！");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
