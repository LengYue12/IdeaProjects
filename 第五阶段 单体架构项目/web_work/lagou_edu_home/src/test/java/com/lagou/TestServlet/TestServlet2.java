package com.lagou.TestServlet;

import com.lagou.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张舒
 * @date 2022/5/12 15:30
 * @description
 */
@WebServlet("/test2")
public class TestServlet2 extends BaseServlet {

    public void show(HttpServletRequest request, HttpServletResponse response){
        System.out.println("TestServlet2中的show方法执行了！");
    }
}
