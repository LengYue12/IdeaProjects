package com.lagou.dao;

import com.lagou.entity.Course;
import com.lagou.entity.CourseVO;
import com.lagou.entity.Teacher;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/29 12:57
 * @description
 */
public interface CourseMapper {

    /*
        多条件课程列表查询
     */

    public List<Course> findCourseByCondition(CourseVO courseVO);

    /*
        新增课程信息
     */
    public void saveCourse(Course course);

    /*
        新增讲师信息
     */
    public void saveTeacher(Teacher teacher);


    /*
        回显课程信息(根据id查询对应的课程信息及关联的讲师信息)
     */
    // 返回值是CourseVO，它既能封装课程信息，也能封装讲师信息
    public CourseVO findCourseById(Integer id);


    /*
        更新课程信息
     */
    public void updateCourse(Course course);

    /*
        更新讲师信息
     */
    public void updateTeacher(Teacher teacher);

    /*
        课程状态管理
     */
    public void updateCourseStatus(Course course);
}
