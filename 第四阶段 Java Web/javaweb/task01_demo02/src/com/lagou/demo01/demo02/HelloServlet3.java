package com.lagou.demo01.demo02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet3 extends HttpServlet {
    public HelloServlet3() {
        System.out.println("构造方法调用了");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是采用继承HttpServlet类的方式创建，以后的开发中推荐该方式！");
    }

    @Override
    public void destroy() {
        System.out.println("销毁操作开始喽...");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("初始化操作开始喽...");
    }
}
