package com.lagou.demo.servlet;

import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.service.ClassGradeService;
import com.lagou.demo.service.ClassGradeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级查询的Servlet
 */
@WebServlet(name = "ClassGradeQueryServlet",urlPatterns = "/classGradeQueryServlet")
public class ClassGradeQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码的问题
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前端页面传递过来的编号
        String classId = request.getParameter("classId");

        // 2.通过Service层实现查找工程
        ClassGradeService classGradeService = new ClassGradeServiceImpl();
        ClassGrade classGrade = classGradeService.classGradeQueryService(Integer.parseInt(classId));

        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        List<ClassGrade> classGradeList = new ArrayList<>();
        if(null != classGrade) {
            // 3.判断查找成功时重新回到主页面
            // 将获取到的所有学员信息放到内置对象，通过客户端跳转的方式显示出来
            classGradeList.add(classGrade);
            request.getSession().setAttribute("classGradeList", classGradeList);
            writer.print("<script>alert('查找班级成功！'); location.href='manageClassGrade.jsp';</script>");
        }else{
            request.getSession().setAttribute("classGradeList", classGradeList);
            writer.print("<script>alert('查找班级失败！'); location.href='manageClassGrade.jsp';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
