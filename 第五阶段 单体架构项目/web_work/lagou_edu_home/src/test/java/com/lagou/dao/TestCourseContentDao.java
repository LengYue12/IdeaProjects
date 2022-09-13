package com.lagou.dao;

import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/13 17:36
 * @description
 */
public class TestCourseContentDao {

    CourseContentDao contentDao = new CourseContentDaoImpl();

    // 测试 查询对应课程下的章节与课时
    @Test
    public void testFindSectionAndLessonByCourseId(){

        List<Course_Section> list = contentDao.findSectionAndLessonByCourseId(59);

        for (Course_Section section : list) {
            System.out.println(section.getId() + " " + section.getSection_name());

            // 章节对应的课时集合
            List<Course_Lesson> lessonList = section.getLessonList();

            // 遍历课时信息
            for (Course_Lesson lesson : lessonList) {
                System.out.println(lesson.getId() + " " + lesson.getTheme() + " " + lesson.getSection_id());
            }
        }
    }

    // 测试根据课程id 回显课程名称
    @Test
    public void testFindCourseById(){

        Course course = contentDao.findCourseById(59);

        System.out.println(course.getId() + " " + course.getCourse_name());
    }

    // 测试保存章节信息的功能
    @Test
    public void testSaveSection(){

        Course_Section section = new Course_Section();

        // 页面传过来的数据
        section.setCourse_id(59);
        section.setSection_name("Vue高级3");
        section.setDescription("vue相关高级");
        section.setOrder_num(9);

        // 补全信息
        String dateFormart = DateUtils.getDateFormart();
        section.setCreate_time(dateFormart);
        section.setUpdate_time(dateFormart);
        section.setStatus(2);   // 0:隐藏；1：待更新；2：已发布

        int i = contentDao.saveSection(section);
        System.out.println(i);
    }


    // 测试修改章节功能
    @Test
    public void testUpdateSection(){

        Course_Section section = new Course_Section();
        section.setId(41);
        section.setSection_name("微服务架构");
        section.setDescription("微服务架构详细讲解");
        section.setOrder_num(4);
        section.setUpdate_time(DateUtils.getDateFormart());

        int i = contentDao.updateSection(section);
        System.out.println(i);
    }

    // 测试修改章节状态功能
    @Test
    public void testUpdateSectionStatus(){

        int i = contentDao.updateSectionStatus(1, 0);
        System.out.println(i);
    }

    // 测试添加课时信息的功能
    @Test
    public void testSaveLesson(){

        // 准备lesson对象
        Course_Lesson lesson = new Course_Lesson();

        // 页面传过来的数据
        lesson.setCourse_id(76);
        lesson.setSection_id(52);
        lesson.setTheme("我是超人");
        lesson.setDuration(10);
        lesson.setIs_free(0);
        lesson.setOrder_num(1);

        // 补全信息
        String dateFormart = DateUtils.getDateFormart();
        lesson.setCreate_time(dateFormart); // 创建时间
        lesson.setUpdate_time(dateFormart); // 修改时间
        lesson.setStatus(2);                // 课时状态 0 隐藏 1 未发布 2 已发布

        int i = contentDao.saveLesson(lesson);
        System.out.println(i);
    }

    // 测试修改课时信息功能
    @Test
    public void testUpdateLesson(){

        // 准备lesson对象
        Course_Lesson lesson = new Course_Lesson();

        // 从页面传过来的数据
        lesson.setTheme("第一讲：怎么变成超人");
        lesson.setDuration(5);
        lesson.setIs_free(1);
        lesson.setOrder_num(2);
        lesson.setId(52);

        // 补全信息
        lesson.setUpdate_time(DateUtils.getDateFormart());

        int i = contentDao.updateLesson(lesson);
        System.out.println(i);
    }
}


