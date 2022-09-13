package com.lagou.demo.service;

import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.PageBean;

import java.util.List;

/**
 * 编程实现班级信息接口
 */
public interface ClassGradeService {

    /**
     * 自定义抽象方法显示班级信息的功能
     * @param pageBean
     * @return
     */
    List<ClassGrade> classGradePageQueryService(PageBean pageBean);

    /**
     * 自定义抽象方法实现创建班级的功能
     * @param classGrade
     * @return
     */
    int classGradeAddService(ClassGrade classGrade);

    /**
     * 自定义抽象方法实现修改班级信息的功能
     * @param classGrade
     * @return
     */
    int classGradeUpdateService(ClassGrade classGrade);

    /**
     * 自定义抽象方法实现删除班级的功能，按照班级编号删除
     * @param classIds
     * @return
     */
    int classGradeDeleteService(String[] classIds);


    /**
     * 自定义抽象方法实现查询班级的功能，按照班级编号查询
     * @param classId
     * @return
     */
    ClassGrade classGradeQueryService(int classId);
}
