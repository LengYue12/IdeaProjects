package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 张舒
 * @date 2022/5/12 17:07
 * @description
 */
@WebServlet("/course")
public class CourseServlet extends BaseServlet {

    // 查询课程信息列表
    public void findCourseList(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.在BaseServlet中已获取了前台传过来的方法名和调用对应的方法
            // 在Servlet里只写业务相关的代码

            // 2.业务处理
            CourseService courseService = new CourseServiceImpl();
            List<Course> courseList = courseService.findCourseList();

            // 3.响应结果
            // 根据接口文档要求，响应结果为JSON数据格式，所以需要转换
            // 使用SimplePropertyPreFilter 指定要转换的字段
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,"id","course_name",
                    "price","sort_num","status");
            String jsonString = JSON.toJSONString(courseList,filter);
            response.getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据条件查询课程信息
    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.接收参数
            String course_name = request.getParameter("course_name");
            String status = request.getParameter("status");

            // 2.业务处理
            CourseService courseService = new CourseServiceImpl();
            List<Course> courseList = courseService.findByCourseNameAndStatus(course_name, status);

            // 3.返回结果 响应JSON数据
            // 使用SimplePropertyPreFilter来指定 要转换的JSON数据
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,"id","course_name","price",
                    "sort_num","status");

            String jsonString = JSON.toJSONString(courseList, filter);
            response.getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程id 查询课程信息
    public void findCourseById(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.接收参数，methodName已在BaseServlet接收
            String id = request.getParameter("id");

            // 2.业务处理
            CourseService courseService = new CourseServiceImpl();
            Course course = courseService.findCourseById(Integer.parseInt(id));

            // 3.返回结果，JSON数据格式的
            // 指定转换的JSON数据
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,"id",
                    "course_name","brief","teacher_name","teacher_info","price","price_tag","discounts",
                    "preview_first_field","preview_second_field","course_img_url","share_title","share_description",
                    "course_description","share_image_title");

            String jsonString = JSON.toJSONString(course,filter);
            response.getWriter().print(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 修改课程状态

    /**
     * 因为前台传过来的只有一个id，所以我们要先根据id把课程信息查出来
     * 然后再对课程状态进行判断，做取反设置
     * 因为只要访问到了这个接口，就是要修改课程状态的
     * 所以判断课程状态，若为1，设置为0。若为0，设置为1
     * @param request
     * @param response
     */
    public void updateCourseStatus(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.获取参数
            String id = request.getParameter("id");

            // 2.业务处理，调用Service层
            // 先通过id把对应的课程信息查出来，在修改状态
            CourseService courseService = new CourseServiceImpl();

            // 3.根据课程id，查询课程信息
            Course course = courseService.findCourseById(Integer.parseInt(id));

            // 4.判断课程信息状态，进行取反设置
            int status = course.getStatus();
            // 如果当前状态为0，访问到这个接口就是要修改状态，所以就把状态改了
            if (status == 0) {
                // 如果是0，设置为1
                course.setStatus(1);
            } else {
                // 如果是1，设置为0
                course.setStatus(0);
            }

            // 5.设置更新时间
            course.setUpdate_time(DateUtils.getDateFormart());

            // 6.修改状态
            // 返回修改后的课程状态信息
            Map<String, Integer> map = courseService.updateCourseStatus(course);

            // 7.响应结果
            String result = JSON.toJSONString(map);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
