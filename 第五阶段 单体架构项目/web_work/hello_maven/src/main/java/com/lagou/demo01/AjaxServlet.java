package com.lagou.demo01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zs
 * @date 2022/5/18 18:32
 * @description
 */
@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {

    private static final long serialVersionUID = 2891039710470075587L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 1.获取请求数据
        String name = req.getParameter("name");

        // 模拟业务处理的时间，造成的延时效果
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印，和响应
        System.out.println(name);
        resp.getWriter().write("你好，你好");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
