package com.lagou.module01.service;

import com.lagou.module01.dao.StudentDao;
import com.lagou.module01.entity.PageBean;
import com.lagou.module01.entity.Student;
import com.lagou.module01.factory.DaoFactory;

import java.util.List;

/**
 * 编程实现学生服务实现类的功能
 */
public class StudentServiceImpl implements StudentService {
    /**
     * 自定义成员变量来记录学生数据访问对象
     */
    private StudentDao studentDao;

    /**
     * 自定义构造方法实现成员变量的初始化
     */
    public StudentServiceImpl() {
        studentDao = DaoFactory.getStudentDao();
    }

    /**
     * 自定义成员方法描述显示所有学生的功能
     * @param pageBean
     * @return
     */
    @Override
    public List<Student> studentShowService(PageBean pageBean) {
        return studentDao.studentShow(pageBean);
    }

    /**
     * 自定义成员方法实现增加学生的功能
     * @param student
     * @return
     */
    @Override
    public int studentAddService(Student student) {
        return studentDao.studentAdd(student);
    }

    /**
     * 自定义成员方法实现删除学生的功能，按照学号删除
     * @param ids
     * @return
     */
    @Override
    public int studentDeleteService(String[] ids) {
        return studentDao.studentDelete(ids);
    }

    /**
     * 自定义成员方法实现修改学生的功能
     * @param student
     * @return
     */
    @Override
    public int studentModifyService(Student student) {
        return studentDao.studentModify(student);
    }

    /**
     * 自定义成员方法实现查找学生的功能，按照学号查找
     * @param id
     * @return
     */
    @Override
    public Student studentFindService(int id) {
        return studentDao.studentFind(id);
    }
}
