package com.lagou.dao;

import com.lagou.domain.Dept;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 20:39
 * @description
 */
/*
    Dao层
        采用Mybatis框架的接口代理方式，实现对部门表(tb_dept)的数据的查询
 */
public interface DeptDao {

    /*
        查询部门信息
     */
    public List<Dept> findAll();
}
