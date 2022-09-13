package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.pojo.User;

import java.util.List;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/19 15:05
 * @Description
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有用户
     */
    List<User> findAll();
}
