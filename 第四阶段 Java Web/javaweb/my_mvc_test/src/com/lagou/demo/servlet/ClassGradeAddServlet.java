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

/**
 * 班级增加的Servlet
 */
@WebServlet(name = "ClassGradeAddServlet",urlPatterns = "/classGradeAddServlet")
public class ClassGradeAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码问题
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前台页面发过来的标志位
        String flag = request.getParameter("flag");
        System.out.println("获取到的标志位是：" + flag);
        String className = request.getParameter("className");
        String gradeName = request.getParameter("gradeName");
        String headTeacherName = request.getParameter("headTeacherName");
        String classSlogan = request.getParameter("classSlogan");
        String str = request.getParameter("classNumber");
        // 将从前台获取到的班级人数转换为int类型
        int classNumber = Integer.parseInt(str);

        // 2.打包数据后通过Service层实现对应的功能
        ClassGradeService classGradeService = new ClassGradeServiceImpl();
        // 根据隐藏域的结果分别实现增加和修改功能
        int res = 0;
        // 定义classGrade对象
        ClassGrade classGrade = null;
        if ("add".equals(flag)){
            classGrade = new ClassGrade(className,gradeName,headTeacherName,classSlogan,classNumber);
            res = classGradeService.classGradeAddService(classGrade);
            System.out.println("插入数据的结果为：" + res);
        } else {
            classGrade = new ClassGrade(Integer.parseInt(flag),className,gradeName,headTeacherName,classSlogan,classNumber);
            res = classGradeService.classGradeUpdateService(classGrade);
            System.out.println("修改数据的结果为：" + res);
        }

        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/classGradePageQueryServlet";
        if(0 != res) {
            writer.print("<script>alert('管理班级信息成功！'); location.href='" + hrefPath
                    + "';</script>");
        }else{
            writer.print("<script>alert('管理班级信息失败！'); location.href='" + hrefPath
                    + "';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
