package com.lagou.demo.service;

import com.lagou.demo.dao.StudentDao;
import com.lagou.demo.entity.Student;
import com.lagou.demo.factory.StudentDaoFactory;

import java.util.List;

/**
 * 业务逻辑层：实现对DAO层的逻辑处理
 * M层
 */
public class StudentService {

    private StudentDao studentDao;

    public StudentService(){
        this.studentDao = StudentDaoFactory.getStudentDao();
    }

    /**
     * 自定义成员方法实现增加学员的方法
     * @param student
     * @return
     */
    public int insertStudent(Student student){return studentDao.insert(student);}

    /**
     * 自定义成员方法实现修改学员的方法
     * @param student
     * @return
     */
    public int updateStudent(Student student){return studentDao.update(student);}

    /**
     * 自定义成员方法根据学员id实现删除学员的方法
     * @param id
     * @return
     */
    public int deleteStudent(int id){return studentDao.delete(id);}

    /**
     * 自定义成员方法根据学员id实现查询单个学员的方法
     * @param id
     * @return
     */
    public Student findByIdStudent(int id){return studentDao.findById(id);}

    /**
     * 自定义成员方法实现学员信息的全部显示
     */
    public List<Student> selectAllStudent(){return studentDao.selectAll();}

    /**
     * 自定义抽象方法实现查询学生的功能，按照班级编号查找
     * @param classId
     * @return
     */
    public Student studentFindByClassIdService(int classId) {return studentDao.studentFindByClassId(classId);}
}
