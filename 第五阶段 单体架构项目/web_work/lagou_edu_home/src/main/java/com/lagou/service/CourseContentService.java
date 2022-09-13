package com.lagou.service;

/**
 * @author 张舒
 * @date 2022/5/13 15:16
 * @description
 */

import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;

import java.util.List;

/**
 * 课程内容管理 Service层接口
 */
public interface CourseContentService {

    // 根据课程id查询课程的内容
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId);

    // 根据课程id查询课程名称
    public Course findCourseById(int courseId);

    // 保存章节信息
    public String saveSection(Course_Section section);

    // 修改章节信息
    public String updateSection(Course_Section section);

    // 修改章节状态
    public String updateSectionStatus(int id,int status);

    // 保存课时信息
    public String saveLesson(Course_Lesson lesson);

    // 修改课时信息
    public String updateLesson(Course_Lesson lesson);
}
