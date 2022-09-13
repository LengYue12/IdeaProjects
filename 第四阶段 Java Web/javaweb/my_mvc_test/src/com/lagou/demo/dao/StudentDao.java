package com.lagou.demo.dao;

import com.lagou.demo.entity.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * 实现各种功能的接口
 * M层（DAO层，对数据库的操作）
 */
public interface StudentDao {

    // 学员信息增加
    public abstract int insert(Student student);
    // 学员信息修改
    public abstract int update(Student student);
    // 学员信息删除
    public abstract int delete(int id);
    // 学员信息查找
    public abstract Student findById(int id);
    // 学员信息显示
    public abstract List<Student> selectAll();
    // 根据班级编号查找学生功能
    public abstract Student studentFindByClassId(int classId);
}
