package com.lagou.service;

import com.lagou.pojo.User;

import java.util.List;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 21:42
 * @Description
 */
public interface UserService {

    /**
     * 查询所有
     * @return
     */
    List<User> queryAll();


    /**
     * 通过ID查询
     * @param id
     * @return
     */
    User findById(Integer id);


    /**
     * 新增
     * @param user
     */
    void insert(User user);


    /**
     * 通过ID删除
     * @param id
     */
    void delete(Integer id);


    /**
     * 修改
     * @param user
     */
    void update(User user);
}
