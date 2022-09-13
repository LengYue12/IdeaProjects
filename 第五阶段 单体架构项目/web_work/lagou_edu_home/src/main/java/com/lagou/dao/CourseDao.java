package com.lagou.dao;

/**
 * @author 张舒
 * @date 2022/5/12 17:04
 * @description
 */

import com.lagou.pojo.Course;

import java.util.List;

/**
 * 课程模块 DAO层接口
 */
public interface CourseDao {

    // 查询课程列表信息的抽象方法
    public List<Course> findCourseList();

    // 根据课程名称，课程状态 查询课程信息
    public List<Course> findByCourseNameAndStatus(String course_name,String status);

    // 保存课程相关的营销信息
    public int courseSalesInfo(Course course);

    // 根据id查询课程信息
    public Course findCourseById(int id);

    // 修改课程营销信息
    public int updateCourseSalesInfo(Course course);

    // 修改课程状态
    public int updateCourseStatus(Course course);
}
