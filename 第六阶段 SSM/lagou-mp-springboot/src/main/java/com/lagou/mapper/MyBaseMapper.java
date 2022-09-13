package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.pojo.User;

import java.util.List;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/20 20:49
 * @Description
 */
/*
    通用的Mapper接口，以后创建其他mapper接口时，不再继承BaseMapper，而是继承MyBaseMapper
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 查询所有用户
     */
    List<User> findAll();

}
