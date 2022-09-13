package com.lagou.dao.impl;

import com.lagou.dao.CourseDao;
import com.lagou.pojo.Course;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/12 17:05
 * @description
 */

/**
 * 课程模块 DAO层实现类
 */
public class CourseDaoImpl implements CourseDao {

    //查询课程列表信息
    @Override
    public List<Course> findCourseList() {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            String sql = "SELECT id,course_name,price,sort_num,STATUS FROM course where is_del =  ?";

            // 3.执行查询
            List<Course> courseList = qr.query(sql, new BeanListHandler<Course>(Course.class), 0);
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常返回null
            return null;
        }
    }

    /**
     * 根据课程名称和课程状态 查询课程信息
     * @param course_name
     * @param status
     * @return
     */
    // 根据条件查询课程信息
    @Override
    public List<Course> findByCourseNameAndStatus(String course_name, String status) {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            // 由于当前查询为多条件不定项查询，所以要判断查询条件后再拼接SQL
            // 2.1 创建StringBuffer对象，用来拼接SQL语句
            StringBuffer sb = new StringBuffer("SELECT id,course_name,price,sort_num,STATUS FROM course WHERE 1=1 and is_del = ?");

            // 2.2 创建创建List集合 保存参数
            List<Object> list = new ArrayList<>();
            list.add(0);

            // 2.3 判断传入的参数是否为空
            if (course_name != null && course_name != "") {
                // 若前台传过来的course_name不为空，则拼接SQL
                sb.append(" AND course_name LIKE ?");
                // Like 查询 需要拼接 %
                course_name = "%" + course_name + "%";
                // 把条件放进List集合
                list.add(course_name);
            }

            if (status != null && status != "") {
                // 若前台传过来的status不为空，则拼接SQL
                sb.append(" AND STATUS = ?");
                // 将status转为int，并放入list集合
                list.add(Integer.parseInt(status));
            }

            // 3.执行查询
            List<Course> courseList = qr.query(sb.toString(), new BeanListHandler<Course>(Course.class), list.toArray());

            // 返回结果
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常返回null
            return null;
        }
    }

    // 保存课程相关的营销信息
    @Override
    public int courseSalesInfo(Course course) {

        try {
            // 1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2.编写SQL
            String sql = "INSERT INTO course(\n" +
                    "course_name,\n" +
                    "brief,\n" +
                    "teacher_name,\n" +
                    "teacher_info,\n" +
                    "preview_first_field,\n" +
                    "preview_second_field,\n" +
                    "discounts,\n" +
                    "price,\n" +
                    "price_tag,\n" +
                    "share_image_title,\n" +
                    "share_title,\n" +
                    "share_description,\n" +
                    "course_description,\n" +
                    "course_img_url,\n" +
                    "STATUS,\n" +
                    "create_time,\n" +
                    "update_time\n" +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            // 3.准备参数
            Object[] param = {course.getCourse_name(),course.getBrief(),course.getTeacher_name(),course.getTeacher_info(),
            course.getPreview_first_field(),course.getPreview_second_field(),course.getDiscounts(),course.getPrice(),
            course.getPrice_tag(),course.getShare_image_title(),course.getShare_title(),course.getShare_description(),
            course.getCourse_description(),course.getCourse_img_url(),course.getStatus(),course.getCreate_time(),course.getUpdate_time()};

            // 4.执行插入操作
            int row = qr.update(sql,param);

            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            // 插入失败返回0
            return 0;
        }

    }

    // 根据id查询课程信息
    @Override
    public Course findCourseById(int id) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT \n" +
                    "id,\n" +
                    "course_name,\n" +
                    "brief,\n" +
                    "teacher_name,\n" +
                    "teacher_info,\n" +
                    "preview_first_field,\n" +
                    "preview_second_field,\n" +
                    "discounts,\n" +
                    "price,\n" +
                    "price_tag,\n" +
                    "share_image_title,\n" +
                    "share_title,\n" +
                    "share_description,\n" +
                    "course_description,\n" +
                    "course_img_url,\n" +
                    "STATUS\n" +
                    "FROM course WHERE id = ?;";

            Course course = qr.query(sql,new BeanHandler<Course>(Course.class),id);
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常返回null
            return null;
        }
    }

    // 修改课程营销信息
    @Override
    public int updateCourseSalesInfo(Course course) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course SET\n" +
                    "course_name = ?,\n" +
                    "brief = ?,\n" +
                    "teacher_name = ?,\n" +
                    "teacher_info = ?,\n" +
                    "preview_first_field = ?,\n" +
                    "preview_second_field = ?,\n" +
                    "discounts = ?,\n" +
                    "price = ?,\n" +
                    "price_tag = ?,\n" +
                    "share_image_title = ?,\n" +
                    "share_title = ?,\n" +
                    "share_description = ?,\n" +
                    "course_description = ?,\n" +
                    "course_img_url = ?,\n" +
                    "update_time = ?\n" +
                    "WHERE id = ?";

            Object[] param = {course.getCourse_name(),course.getBrief(),course.getTeacher_name(),course.getTeacher_info(),
            course.getPreview_first_field(),course.getPreview_second_field(),course.getDiscounts(),course.getPrice(),
            course.getPrice_tag(),course.getShare_image_title(),course.getShare_title(),course.getShare_description(),
            course.getCourse_description(),course.getCourse_img_url(),course.getUpdate_time(),course.getId()};

            int row = qr.update(sql, param);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 修改课程状态
    @Override
    public int updateCourseStatus(Course course) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course SET STATUS = ?,update_time = ?  WHERE id = ?; \n";

            Object[] param = {course.getStatus(),course.getUpdate_time(),course.getId()};

            int row = qr.update(sql, param);

            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            // 失败返回0
            return 0;
        }

    }
}
