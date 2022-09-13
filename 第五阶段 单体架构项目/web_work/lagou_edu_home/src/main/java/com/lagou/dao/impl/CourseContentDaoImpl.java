package com.lagou.dao.impl;

import com.lagou.dao.CourseContentDao;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/13 15:15
 * @description
 */

/**
 * 课程内容管理Dao层实现类
 */
public class CourseContentDaoImpl implements CourseContentDao {

    // 根据课程ID查询课程相关信息
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            // 先把课程对应的章节信息查出，根据课程id查询对应的章节信息
            String sql = "SELECT\n" +
                    "id,\n" +
                    "course_id,\n" +
                    "section_name,\n" +
                    "description,\n" +
                    "order_num,\n" +
                    "STATUS\n" +
                    "FROM course_section WHERE course_id = ?";
            // 3.执行查询，获取到对应的章节信息
            List<Course_Section> sectionList = qr.query(sql, new BeanListHandler<Course_Section>(Course_Section.class), courseId);

            // 4.根据章节id查询课时信息
            // 封装对应课程的章节和课时信息
            for (Course_Section section : sectionList) {

                // 调用方法     获取章节对应的课时
                // 传课程章节id，返回一个该章节所对应的课时集合
                List<Course_Lesson> lessonList = findLessonBySectionId(section.getId());

                // 将课时数据封装到 章节对象中
                section.setLessonList(lessonList);
            }
            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据章节id查询对应的课时信息
    @Override
    public List<Course_Lesson> findLessonBySectionId(int sectionId) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT\n" +
                    "id,\n" +
                    "course_id,\n" +
                    "section_id,\n" +
                    "theme,\n" +
                    "duration,\n" +
                    "is_free,\n" +
                    "order_num,\n" +
                    "STATUS\n" +
                    "FROM course_lesson WHERE section_id = ?";

            List<Course_Lesson> lessonList = qr.query(sql, new BeanListHandler<Course_Lesson>(Course_Lesson.class),sectionId);

            return lessonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // 根据课程id 回显课程信息
    // 根据id进行查询课程名
    @Override
    public Course findCourseById(int courseId) {

        try {

            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT id,course_name FROM course WHERE id = ?";

            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), courseId);
            return course;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 保存章节信息
    @Override
    public int saveSection(Course_Section section) {

        try {

            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "INSERT INTO course_section(\n" +
                    "course_id,\n" +
                    "section_name,\n" +
                    "description,\n" +
                    "order_num,\n" +
                    "status,\n" +
                    "create_time,\n" +
                    "update_time) VALUES(?,?,?,?,?,?,?)";

            Object[] param = {section.getCourse_id(),section.getSection_name(),section.getDescription(),
            section.getOrder_num(),section.getStatus(),section.getCreate_time(),section.getUpdate_time()};

            int row = qr.update(sql, param);
            return row;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 修改章节
    @Override
    public int updateSection(Course_Section section) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course_section SET \n" +
                    "section_name = ?,\n" +
                    "description = ?,\n" +
                    "order_num = ?,\n" +
                    "update_time = ?\n" +
                    "WHERE id = ?\n";

            Object[] param = {section.getSection_name(),section.getDescription(),section.getOrder_num(),section.getUpdate_time(),
            section.getId()};

            return  qr.update(sql,param);

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 修改章节状态
    @Override
    public int updateSectionStatus(int id, int status) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course_section SET STATUS = ?,update_time = ? WHERE id = ?";

            Object[] param = {status, DateUtils.getDateFormart(),id};

            return qr.update(sql,param);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 添加课时信息
    @Override
    public int saveLesson(Course_Lesson lesson) {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            String sql = "INSERT INTO course_lesson(\n" +
                    "course_id,\n" +
                    "section_id,\n" +
                    "theme,\n" +
                    "duration,\n" +
                    "is_free,\n" +
                    "order_num,\n" +
                    "create_time,\n" +
                    "update_time,\n" +
                    "STATUS\n" +
                    ") VALUES(?,?,?,?,?,?,?,?,?)";

            // 3.准备参数
            Object[] param = {lesson.getCourse_id(),lesson.getSection_id(),lesson.getTheme(),lesson.getDuration(),
            lesson.getIs_free(),lesson.getOrder_num(),lesson.getCreate_time(),lesson.getUpdate_time(),lesson.getStatus()};

            // 4.执行插入
            return qr.update(sql,param);
        } catch (SQLException e) {
            e.printStackTrace();
            // 插入失败返回0
            return 0;
        }
    }

    // 修改课时信息
    @Override
    public int updateLesson(Course_Lesson lesson) {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            String sql = "UPDATE course_lesson SET\n" +
                    "theme = ?,\n" +
                    "duration = ?,\n" +
                    "is_free = ?,\n" +
                    "order_num = ?,\n" +
                    "update_time = ?\n" +
                    "WHERE id = ?";

            // 3.准备参数
            Object[] param = {lesson.getTheme(),lesson.getDuration(),lesson.getIs_free(),lesson.getOrder_num(),
                    lesson.getUpdate_time(),lesson.getId()};

            // 4.执行修改
            return qr.update(sql,param);
        } catch (SQLException e) {
            e.printStackTrace();
            // 修改失败返回0
            return 0;
        }

    }
}
