package com.lagou.service;

import com.lagou.domain.Dept;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 23:51
 * @description
 */
public interface DeptService {

    // 根据dept_id 查询部门
    Dept findById(Integer dept_id);

    List<Dept> find();
}
