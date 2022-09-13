package com.lagou.demo01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zs
 * @date 2022/5/15 19:16
 * @description
 */
@WebServlet("/login2")
public class LoginServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码，防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 1.获取请求数据
        String name = req.getParameter("name");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2.进行响应
        System.out.println(name);
        resp.getWriter().write(name);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
