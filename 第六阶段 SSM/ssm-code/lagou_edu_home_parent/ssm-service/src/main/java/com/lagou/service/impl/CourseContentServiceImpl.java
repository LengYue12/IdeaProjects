package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.entity.Course;
import com.lagou.entity.CourseLesson;
import com.lagou.entity.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/29 22:15
 * @description
 */
@Service
public class CourseContentServiceImpl implements CourseContentService {

    // 注入mapper对象
    @Autowired
    private CourseContentMapper courseContentMapper;

    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId) {
        return courseContentMapper.findSectionAndLessonByCourseId(courseId);
    }

    @Override
    public Course findCourseByCourseId(int courseId) {
        return courseContentMapper.findCourseByCourseId(courseId);
    }

    @Override
    public void saveSection(CourseSection courseSection) {
        // 1.补全信息
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);

        // 2.调用方法
        courseContentMapper.saveSection(courseSection);
    }

    /*
        更新章节信息
     */
    @Override
    public void updateSection(CourseSection courseSection) {

        // 1.补全信息
        courseSection.setUpdateTime(new Date());

        // 2.调用方法
        courseContentMapper.updateSection(courseSection);
    }


    // 修改章节状态
    @Override
    public void updateSectionStatus(int id, int status) {

        // 封装数据
        CourseSection courseSection = new CourseSection();
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());
        courseSection.setId(id);

        // 调用mapper
        courseContentMapper.updateSectionStatus(courseSection);
    }

    /*
        新增课时信息
     */
    @Override
    public void saveLesson(CourseLesson courseLesson) {

        // 1.补全信息
        Date date = new Date();
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);

        // 2.调用方法
        courseContentMapper.saveLesson(courseLesson);
    }

    /*
        更新课时信息
     */
    @Override
    public void updateLesson(CourseLesson courseLesson) {

        // 1.补全信息
        courseLesson.setUpdateTime(new Date());

        // 2.调用方法
        courseContentMapper.updateLesson(courseLesson);
    }

    @Override
    public void updateLessonStatus(int id,int status) {

        // 封装数据
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setStatus(status);
        courseLesson.setUpdateTime(new Date());
        courseLesson.setId(id);

        // 调用mapper
        courseContentMapper.updateLessonStatus(courseLesson);
    }
}
