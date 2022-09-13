package com.lagou.demo.dao;

import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.PageBean;

import java.util.List;

/**
 * 编程实现班级信息数据访问层的接口
 */
public interface ClassGradeDao {

    /**
     * 自定义抽象方法显示班级信息的功能
     * @param pageBean
     * @return
     */
    List<ClassGrade> classGradePageQuery(PageBean pageBean);

    /**
     * 自定义抽象方法实现创建班级的功能
     * @param classGrade
     * @return
     */
    int classGradeAdd(ClassGrade classGrade);

    /**
     * 自定义抽象方法实现修改班级的功能
     * @param classGrade
     * @return
     */
    int classGradeUpdate(ClassGrade classGrade);

    /**
     * 自定义抽象方法实现删除班级的功能，按照班级编号删除
     * @param classIds
     * @return
     */
    int classGradeDelete(String[] classIds);

    /**
     * 自定义抽象方法实现查询班级的功能，按照班级编号查询
     * @param classId
     * @return
     */
    ClassGrade classGradeQuery(int classId);
}
