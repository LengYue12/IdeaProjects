package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.service.impl.CourseContentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 张舒
 * @date 2022/5/13 15:19
 * @description
 */
@WebServlet("/courseContent")
public class CourseContentServlet extends BaseServlet {

    // 展示对应课程的章节与课时信息
    // 根据课程ID查询章节与课时信息
    public void findSectionAndLessonByCourseId(HttpServletRequest request,HttpServletResponse response){

        try {
            // 1.获取参数
            String course_id = request.getParameter("course_id");

            // 2.业务处理
            // 获取集合，保存的是章节和课时信息
            CourseContentService contentService = new CourseContentServiceImpl();
            List<Course_Section> sectionList = contentService.findSectionAndLessonByCourseId(Integer.parseInt(course_id));

            // 3.返回结果，JSON数据格式的
            String jsonString = JSON.toJSONString(sectionList);
            response.getWriter().print(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程id 回显章节对应的课程信息
    public void findCourseById(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.获取参数
            String course_id = request.getParameter("course_id");

            // 2.业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            Course course = contentService.findCourseById(Integer.parseInt(course_id));

            // 3.返回JSON数据
            // 获取需要用到的字段
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,"id","course_name");

            String jsonString = JSON.toJSONString(course, filter);

            // 响应结果
            response.getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存和修改 章节信息
     * @param request
     * @param response
     */
    public void saveOrUpdateSection(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.获取参数    从域对象中获取
            Map<String,Object> map = (Map) request.getAttribute("map");

            // 2.创建Course_Section对象
            Course_Section section = new Course_Section();

            // 3.使用BeanUtils工具类，将map中的数据封装到 section对象中
            //BeanUtils.populate(section,map);
            BeanUtils.copyProperties(section,map.get("section"));

            // 4.业务处理
            CourseContentService contentService = new CourseContentServiceImpl();

            // 作判断，如果章节对象中id为0，就说明是修改操作，否则是新增操作
            // 判断是否携带id
            if (section.getId() == 0) {
                // 新增操作
                String result = contentService.saveSection(section);

                // 5.响应结果
                response.getWriter().print(result);

            } else {
                // 有id值，修改操作
                String result = contentService.updateSection(section);
                response.getWriter().print(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改章节状态
    public void updateSectionStatus(HttpServletRequest request,HttpServletResponse response){

        try {
            // 1.获取参数
            int id = Integer.parseInt(request.getParameter("id"));  // 章节id
            int status = Integer.parseInt(request.getParameter("status")); // 章节状态

            // 2.业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            String result = contentService.updateSectionStatus(id, status);

            // 3.返回结果
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存&修改课时接口
     */
    public void saveOrUpdateLesson(HttpServletRequest request, HttpServletResponse response){

        try {
            // 1.获取参数   从域对象中获取
            Map<String,Object> map = (Map)request.getAttribute("map");

            // 2.创建Course_Lesson对象
            Course_Lesson lesson = new Course_Lesson();

            // 3.使用BeanUtils工具类，将map中的数据封装到 section对象中
            //BeanUtils.populate(lesson,map);
            BeanUtils.copyProperties(lesson,map.get("lesson"));

            // 4.业务处理
            CourseContentService contentService = new CourseContentServiceImpl();

            // 创建接收的状态码，用于返回给前台
            String result = null;
            // 作判断，如果前台传过来的课时对象里的id不为0，就说明是携带了id，是修改操作，否则就是新增操作
            // 判断是否携带id
            if (lesson.getId() == 0) {
                // id为0，说明前台没有携带id，所以是新增操作
                result = contentService.saveLesson(lesson);
            } else {
                // id不为0，说明是修改操作，携带了id，调用修改业务处理
                result = contentService.updateLesson(lesson);
            }
            // 5.响应结果
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
