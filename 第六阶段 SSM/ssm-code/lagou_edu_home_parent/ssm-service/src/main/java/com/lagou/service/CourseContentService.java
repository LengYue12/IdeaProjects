package com.lagou.service;

import com.lagou.entity.Course;
import com.lagou.entity.CourseLesson;
import com.lagou.entity.CourseSection;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/29 22:13
 * @description
 */
public interface CourseContentService {

    /*
        根据课程id查询对应的课程内容（章节信息及课时信息）
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);

    /*
        回显章节对应的课程信息
            根据课程id查询课程信息
     */
    public Course findCourseByCourseId(int courseId);

    /*
        新增章节信息
     */
    public void saveSection(CourseSection courseSection);

    /*
        更新章节信息
     */
    void updateSection(CourseSection courseSection);


    /*
        修改章节状态
     */
    void updateSectionStatus(int id, int status);

    /*
        新增课时信息
     */
    void saveLesson(CourseLesson courseLesson);

    /*
        修改课时信息
     */
    void updateLesson(CourseLesson courseLesson);



    /*
        修改课时状态
     */
    void updateLessonStatus(int id, int status);
}
