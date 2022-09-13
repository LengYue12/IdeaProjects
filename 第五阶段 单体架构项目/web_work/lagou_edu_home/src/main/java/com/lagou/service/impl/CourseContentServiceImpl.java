package com.lagou.service.impl;

import com.lagou.base.StatusCode;
import com.lagou.dao.CourseContentDao;
import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.utils.DateUtils;

import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/13 15:16
 * @description
 */

/**
 * 课程内容管理 Service层的实现类
 */
public class CourseContentServiceImpl implements CourseContentService {

    CourseContentDao contentDao = new CourseContentDaoImpl();

    // 根据课程id查询课程对应的章节与课时信息
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {

        List<Course_Section> sectionList = contentDao.findSectionAndLessonByCourseId(courseId);

        return sectionList;
    }

    // 通过课程id查询课程名称
    @Override
    public Course findCourseById(int courseId) {
        return contentDao.findCourseById(courseId);
    }

    // 保存章节信息
    @Override
    public String saveSection(Course_Section section) {

        // 1.补全章节信息
        section.setStatus(2); // 状态： 0 隐藏 1 待更新 2 已发布
        String dateFormart = DateUtils.getDateFormart();
        section.setCreate_time(dateFormart);
        section.setUpdate_time(dateFormart);

        // 2.调用dao执行插入操作
        int row = contentDao.saveSection(section);

        // 3.根据是否插入成功，封装对应信息
        if (row > 0) {
            // 保存成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 保存失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改章节信息
    @Override
    public String updateSection(Course_Section section) {

        // 1.补全信息
        String dateFormart = DateUtils.getDateFormart();
        section.setUpdate_time(dateFormart);

        // 2.调用dao
        int row = contentDao.updateSection(section);

        // 3.判断是否插入成功
        if (row>0) {
            // 修改成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 修改失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改章节状态
    @Override
    public String updateSectionStatus(int id, int status) {

        int row = contentDao.updateSectionStatus(id, status);

        // 判断是否修改成功
        if (row>0) {
            // 修改状态成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 修改失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 保存课时信息
    @Override
    public String saveLesson(Course_Lesson lesson) {

        // 1.补全课时信息
        String dateFormart = DateUtils.getDateFormart();
        lesson.setCreate_time(dateFormart);
        lesson.setUpdate_time(dateFormart);
        lesson.setStatus(2);    // 状态： 0 隐藏 1 待更新 2 已发布

        // 2.调用dao层
        int row = contentDao.saveLesson(lesson);

        // 3.判断是否插入成功
        if (row>0) {
            // 插入成功，调用枚举工具类，将结果转换为JSON格式并返回
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 插入失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改课时信息
    @Override
    public String updateLesson(Course_Lesson lesson) {

        // 1.补全信息
        lesson.setUpdate_time(DateUtils.getDateFormart());

        // 2.调用dao
        int row = contentDao.updateLesson(lesson);

        // 3.判断是否修改成功
        if (row>0) {
            // 修改成功  调用工具类返回成功状态码，格式为JSON
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 修改失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }
}
