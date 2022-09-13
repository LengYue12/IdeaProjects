package com.lagou.service;

/**
 * @author 张舒
 * @date 2022/5/12 17:05
 * @description
 */

import com.lagou.pojo.Course;

import java.util.List;
import java.util.Map;

/**
 * 课程模块 Service层接口
 */
public interface CourseService {

    //查询课程列表信息
    public List<Course> findCourseList();

    // 根据添加查询课程信息
    public List<Course> findByCourseNameAndStatus(String course_name, String status);

    // 保存课程相关的营销信息
    public String courseSalesInfo(Course course);

    // 根据课程id进行查询
    public Course findCourseById(int id);

    // 修改课程营销信息
    public String updateCourseSalesInfo(Course course);

    // 由于接口文档要的响应结果是键值对，所以返回map
    public Map<String,Integer> updateCourseStatus(Course course);
}
