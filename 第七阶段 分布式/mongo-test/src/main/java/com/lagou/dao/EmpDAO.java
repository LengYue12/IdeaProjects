package com.lagou.dao;

import com.lagou.entity.Emp;

import java.util.List;

/**
 * @BelongsProject mongo-com.lagou.test
 * @Author lengy
 * @CreateTime 2022/8/1 22:27
 * @Description 接口
 */
public interface EmpDAO {

    void save(Emp emp);


    void delete(String id);


    void update(Emp emp);


    Emp findById(String id);


    List<Emp> findListPage(Integer pageIndex, Integer pageSize, String name);
}
