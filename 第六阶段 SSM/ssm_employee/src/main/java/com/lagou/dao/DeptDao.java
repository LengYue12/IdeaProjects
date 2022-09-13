package com.lagou.dao;

import com.lagou.domain.Dept;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 21:44
 * @description
 */
/*
    部门Mapper接口
 */
public interface DeptDao {

    // 根据dept_id 查询部门
    Dept findById(Integer dept_id);

    List<Dept> find();
}
