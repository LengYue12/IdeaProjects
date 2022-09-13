package com.lagou.service;

import com.lagou.entity.Course;
import com.lagou.entity.CourseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/29 13:43
 * @description
 */
public interface CourseService {

    /*
        多条件课程列表查询
     */
    public List<Course> findCourseByCondition(CourseVO courseVO);


    /*
        添加课程及讲师信息
     */
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;


    /*
        根据ID查询课程信息
     */
    public CourseVO findCourseById(Integer id);


    /*
        更新课程及讲师信息
     */
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /*
        修改课程状态
     */
    public void updateCourseStatus(int courseId, int status);
}
