package com.lagou.demo.servlet;

import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.PageBean;
import com.lagou.demo.service.ClassGradeService;
import com.lagou.demo.service.ClassGradeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 班级分页查询的Servlet
 */
@WebServlet(name = "ClassGradePageQueryServlet", urlPatterns = "/classGradePageQueryServlet")
public class ClassGradePageQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前端页面传来的页码数值并构造分页查询的对象
        String page = request.getParameter("page");
        //System.out.println("前台传过来的页码是：" + page);
        // 这里创建默认值为1，也就是第一个默认查询第一页数据内容
        PageBean pageBean = new PageBean(1, 10);
        if (null != page && page.length() > 0) {
            pageBean.setPage(Integer.parseInt(page));
        }
        // 通过Service层以及Dao层获取所有的班级信息
        ClassGradeService classGradeService = new ClassGradeServiceImpl();
        List<ClassGrade> classGradeList = classGradeService.classGradePageQueryService(pageBean);
        // 将获取到的所有班级信息放到内置对象，通过客户端跳转的方式显示出来
        request.getSession().setAttribute("classGradeList", classGradeList);
        response.sendRedirect("manageClassGrade.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
