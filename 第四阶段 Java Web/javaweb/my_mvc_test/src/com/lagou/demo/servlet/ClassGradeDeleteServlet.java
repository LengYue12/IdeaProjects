package com.lagou.demo.servlet;

import com.lagou.demo.dao.StudentDaoImp;
import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.Student;
import com.lagou.demo.service.ClassGradeService;
import com.lagou.demo.service.ClassGradeServiceImpl;
import com.lagou.demo.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 班级删除的Servlet
 */
@WebServlet(name = "ClassGradeDeleteServlet",urlPatterns = "/classGradeDeleteServlet")
public class ClassGradeDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码方式，解决中文乱码的问题
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 1.获取前台页面传递过来的班级编号
        String[] classIds = request.getParameterValues("classId");

        // 2.通过Service层实现删除操作
        ClassGradeService classGradeService = new ClassGradeServiceImpl();

        // 为了保证删除班级之前，该班级内没有关联的学生，应该通过班级编号查询一下学生表
        StudentService studentService = new StudentService();
        // 获取输出流
        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/classGradePageQueryServlet";
        // 使用循环分别对每个班级编号进行查找
        for (String classId : classIds) {
            Student student = studentService.studentFindByClassIdService(Integer.parseInt(classId));
            if (null != student) {
                writer.print("<script>alert('删除班级失败，该班级有学生哦！');location.href='" + hrefPath
                + "';</script>");
                return;
            }
        }
        int res = classGradeService.classGradeDeleteService(classIds);
        // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
        if(0 != res) {
            writer.print("<script>alert('删除班级成功！'); location.href='" + hrefPath
                    + "';</script>");
        }else{
            writer.print("<script>alert('删除班级失败！'); location.href='" + hrefPath
                    + "';</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
