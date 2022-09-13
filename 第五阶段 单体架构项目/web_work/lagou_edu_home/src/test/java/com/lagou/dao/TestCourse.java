package com.lagou.dao; /**
 * @author 张舒
 * @date 2022/5/12 17:49
 * @description
 */

import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.List;

/**
 * 测试DAO层的代码
 */
public class TestCourse {

    CourseDao courseDao = new CourseDaoImpl();

    // 测试查询课程列表信息
    @Test
    public void findCourseList(){

        List<Course> courseList = courseDao.findCourseList();

        System.out.println(courseList);
    }

    // 测试根据条件查询课程信息
    @Test
    public void findByCourseNameAndStatus(){

        List<Course> courseList = courseDao.findByCourseNameAndStatus(null, "1");

        System.out.println(courseList);
    }

    // 测试保存课程营销信息
    @Test
    public void courseSalesInfo(){

        // 1.创建course对象
        Course course = new Course();
        course.setCourse_name("本草纲目");
        course.setBrief("如果华佗在世，崇洋都被医治");
        course.setTeacher_name("周杰伦");
        course.setTeacher_info("亚洲天王");
        course.setPreview_first_field("共10讲");
        course.setPreview_second_field("每周一更新");
        course.setDiscounts(88.88);
        course.setPrice(100.0);
        course.setPrice_tag("最新优惠价");
        course.setShare_image_title("哈哈");
        course.setShare_title("嘻嘻");
        course.setShare_description("好好听歌");
        course.setCourse_description("我动作轻松自在，跳个大概");
        course.setCourse_img_url("https://www.xx.com/xxx.jpg");
        course.setStatus(1);    // 1 上架， 0下架
        String dateFormart = DateUtils.getDateFormart();
        course.setCreate_time(dateFormart);
        course.setUpdate_time(dateFormart);

        int i = courseDao.courseSalesInfo(course);
        System.out.println(i);
    }

    // 测试修改课程信息
    @Test
    public void testUpdateCourse(){

        // 1.根据id查询课程信息
        Course course = courseDao.findCourseById(1);

        System.out.println(course);

        // 2.修改课程的营销信息
        course.setCourse_name("100个Java面试考点");
        course.setTeacher_name("王老师");
        course.setTeacher_info("10年经验");
        course.setDiscounts(800.0);

        int i = courseDao.updateCourseSalesInfo(course);
        System.out.println(i);
    }

    // 测试修改课程状态
    @Test
    public void testUpdateCourseStatus(){

        Course course = new Course();
        course.setId(1);
        course.setStatus(0);
        int i = courseDao.updateCourseStatus(course);
        System.out.println(i);
    }
}
