package com.lagou.module01.service;

import com.lagou.module01.entity.PageBean;
import com.lagou.module01.entity.Student;

import java.util.List;

/**
 * 编程实现学生服务接口
 */
public interface StudentService {

    /**
     * 自定义抽象方法描述显示所有学生的功能
     * @param pageBean
     * @return
     */
    List<Student> studentShowService(PageBean pageBean);

    /**
     * 自定义抽象方法实现增加学生的功能
     * @param student
     * @return
     */
    int studentAddService(Student student);

    /**
     * 自定义抽象方法实现删除学生的功能，按照学号删除
     * @param ids
     * @return
     */
    int studentDeleteService(String[] ids);

    /**
     * 自定义抽象方法实现修改学生的功能
     * @param student
     * @return
     */
    int studentModifyService(Student student);

    /**
     * 自定义抽象方法实现查找学生的功能，按照学号查找
     * @param id
     * @return
     */
    public abstract Student studentFindService(int id);
}
