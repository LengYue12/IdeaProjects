package com.lagou.TestServlet;

import com.lagou.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张舒
 * @date 2022/5/12 15:13
 * @description
 */

/**
 * 模拟课程模块，模块中有很多功能
 */
@WebServlet("/test")
public class TestServlet extends BaseServlet {



    /**
     * 2.模块对应的功能部分
     * 业务代码
     */
    public void addCourse(HttpServletRequest request,HttpServletResponse response){
        System.out.println("新建课程");
    }

    public void findByName(HttpServletRequest request,HttpServletResponse response){
        System.out.println("根据课程名称查询");
    }

    public void findByStatus(HttpServletRequest request,HttpServletResponse response){
        System.out.println("根据课程状态查询");
    }
}
