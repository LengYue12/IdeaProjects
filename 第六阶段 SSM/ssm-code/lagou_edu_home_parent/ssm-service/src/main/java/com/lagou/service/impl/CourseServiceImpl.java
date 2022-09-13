package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.entity.Course;
import com.lagou.entity.CourseVO;
import com.lagou.entity.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/29 13:44
 * @description
 */
@Service    // 生成该类实例存到IOC
public class CourseServiceImpl implements CourseService {

    // 注入代理对象
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        return courseMapper.findCourseByCondition(courseVO);
    }

    // 根据controller 传递过来的 coursevo 对象，来进行两个实体的封装
    // 再分别调用dao层方法 进行 课程信息和讲师信息的保存
    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        // 封装课程信息，获取到返回的id值
        Course course = new Course();

        // 就是吧courseVO 里面 名称相同的 属性值封装到 course对象中
        BeanUtils.copyProperties(course,courseVO);

        // 补全课程信息
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        // 保存课程
        courseMapper.saveCourse(course);

        // 获取新插入数据的id值
        int id = course.getId();

        // 封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);

        // 补全讲师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);

        // 保存讲师信息
        courseMapper.saveTeacher(teacher);

    }

    @Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        // 封装课程信息
        Course course = new Course();

        BeanUtils.copyProperties(course,courseVO);

        // 补全课程信息
        Date date = new Date();
        course.setUpdateTime(date);

        // 更新课程信息
        courseMapper.updateCourse(course);


        // 封装讲师信息
        Teacher teacher = new Teacher();

        BeanUtils.copyProperties(teacher,courseVO);

        // 补全讲师信息
        // 在封装讲师信息时，要设置讲师对应的 课程id
        teacher.setCourseId(course.getId());
        teacher.setUpdateTime(date);

        // 更新讲师信息
        courseMapper.updateTeacher(teacher);

    }

    /*
        课程状态管理
     */
    @Override
    public void updateCourseStatus(int courseId, int status) {

        // 1.封装数据
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        // 2.调用mapper
        courseMapper.updateCourseStatus(course);
    }
}
