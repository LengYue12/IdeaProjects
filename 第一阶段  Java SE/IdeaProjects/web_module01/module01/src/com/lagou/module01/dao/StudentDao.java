package com.lagou.module01.dao;

import com.lagou.module01.entity.PageBean;
import com.lagou.module01.entity.Student;

import java.util.List;

/**
 * 编程实现学生数据访问层的接口
 */
public interface StudentDao {

    /**
     * 自定义抽象方法描述显示所有学生的功能
     * @param pageBean
     * @return
     */
    List<Student> studentShow(PageBean pageBean);

    /**
     * 自定义抽象方法实现增加学生的功能
     * @param student
     * @return
     */
    int studentAdd(Student student);

    /**
     * 自定义抽象方法实现删除学生的功能，按照学号删除
     * @param ids
     * @return
     */
    int studentDelete(String[] ids);

    /**
     * 自定义抽象方法实现修改学生的功能
     * @param student
     * @return
     */
    int studentModify(Student student);

    /**
     * 自定义抽象方法实现查找学生的功能，按照学号查找
     * @param id
     * @return
     */
    Student studentFind(int id);
}
