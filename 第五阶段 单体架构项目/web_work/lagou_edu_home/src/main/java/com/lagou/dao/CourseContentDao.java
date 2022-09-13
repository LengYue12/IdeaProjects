package com.lagou.dao;

/**
 * @author 张舒
 * @date 2022/5/13 15:13
 * @description
 */

import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;

import java.util.List;

/**
 * 课程内容管理 Dao层接口
 */
public interface CourseContentDao {

    // 两个方法配合使用，最终查出课程相关的内容信息
    // 根据课程ID查询课程相关信息   返回List集合，泛型是章节，因为在章节表中有一个表示一对多关系的集合泛型是课时信息
    // 返回章节集合，在章节集合中，把课时信息保存到lessonList集合，所以返回值是Course_Section类型的集合
    // 根据课程id查询该课程下面的章节和课时信息
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId);

    // 根据章节id 查询章节相关的课时信息
    public List<Course_Lesson> findLessonBySectionId(int sectionId);

    // 如果要新建一个章节，首先要查询一下课程名称做回显
    // 根据课程id 回显课程信息
    public Course findCourseById(int courseId);

    // 保存章节信息
    public int saveSection(Course_Section section);

    // 修改章节信息
    public int updateSection(Course_Section section);

    // 修改章节状态
    public int updateSectionStatus(int id,int status);

    // 添加课时信息
    public int saveLesson(Course_Lesson lesson);

    // 修改课时信息
    public int updateLesson(Course_Lesson lesson);
}
