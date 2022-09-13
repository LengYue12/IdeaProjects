package com.lagou.demo.service;

import com.lagou.demo.dao.ClassGradeDao;
import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.PageBean;
import com.lagou.demo.factory.ClassGradeDaoFactory;

import java.util.List;

/**
 * 编程实现班级增删改操作
 */
public class ClassGradeServiceImpl implements ClassGradeService {

    /**
     * 自定义成员变量来记录班级数据访问对象
     */
    private ClassGradeDao classGradeDao;

    /**
     * 自定义构造方法实现成员变量的初始化
     */
    public ClassGradeServiceImpl()
    {
        classGradeDao = ClassGradeDaoFactory.getClassGradeDao();
    }

    /**
     * 自定义成员方法实现显示班级信息的功能
     * @param pageBean
     * @return
     */
    @Override
    public List<ClassGrade> classGradePageQueryService(PageBean pageBean) {
        return classGradeDao.classGradePageQuery(pageBean);
    }

    /**
     * 自定义成员方法实现创建班级信息的功能
     * @param classGrade
     * @return
     */
    @Override
    public int classGradeAddService(ClassGrade classGrade) {
        return classGradeDao.classGradeAdd(classGrade);
    }

    /**
     * 自定义成员方法实现修改班级信息的功能
     * @param classGrade
     * @return
     */
    @Override
    public int classGradeUpdateService(ClassGrade classGrade) {
        return classGradeDao.classGradeUpdate(classGrade);
    }

    /**
     * 自定义成员方法实现删除班级信息的功能
     * @param classIds
     * @return
     */
    @Override
    public int classGradeDeleteService(String[] classIds) {
        return classGradeDao.classGradeDelete(classIds);
    }

    /**
     * 自定义成员方法实现查询班级信息的功能
     * @param classId
     * @return
     */
    @Override
    public ClassGrade classGradeQueryService(int classId) {
        return classGradeDao.classGradeQuery(classId);
    }
}
