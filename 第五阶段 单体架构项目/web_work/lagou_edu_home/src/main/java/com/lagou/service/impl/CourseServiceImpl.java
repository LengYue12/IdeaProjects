package com.lagou.service.impl;

import com.lagou.base.StatusCode;
import com.lagou.dao.CourseDao;
import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.utils.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张舒
 * @date 2022/5/12 17:06
 * @description
 */

/**
 * 课程模块 Service层 实现类
 */
public class CourseServiceImpl implements CourseService {

    // 创建 CourseDao
    CourseDao courseDao = new CourseDaoImpl();

    // 查询课程列表信息
    @Override
    public List<Course> findCourseList() {

        // 调用Dao层 进行查询
        return courseDao.findCourseList();
    }

    // 根据添加查询课程信息
    @Override
    public List<Course> findByCourseNameAndStatus(String course_name, String status) {

        // 调用到Dao层 进行条件查询
        return courseDao.findByCourseNameAndStatus(course_name,status);
    }

    // 保存课程相关的营销信息
    @Override
    public String courseSalesInfo(Course course) {

        // 1.补全课程信息
        String dateFormart = DateUtils.getDateFormart();
        course.setCreate_time(dateFormart); // 创建时间
        course.setUpdate_time(dateFormart); // 修改时间
        course.setStatus(1);    // 上架

        // 2.调用Dao进行插入
        int i = courseDao.courseSalesInfo(course);
        if (i>0) {
            // 保存成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 保存失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 根据id进行课程信息查询
    @Override
    public Course findCourseById(int id) {
        return courseDao.findCourseById(id);
    }

    // 修改课程信息
    @Override
    public String updateCourseSalesInfo(Course course) {

        int row = courseDao.updateCourseSalesInfo(course);

        // 判断是否保存成功
        if (row>0) {
            // 保存成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 保存失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改课程状态
    @Override
    public Map<String, Integer> updateCourseStatus(Course course) {

        // 调用dao
        int row = courseDao.updateCourseStatus(course);

        Map<String,Integer> map = new HashMap<>();

        // 判断是否修改成功
        if (row > 0){
            // 进行数据的封装
            // 判断修改后的课程状态，做封装放到map里，返回给Servlet
            if (course.getStatus() == 0){
                map.put("status",0);
            } else {
                map.put("status",1);
            }
        }
        return map;
    }
}
