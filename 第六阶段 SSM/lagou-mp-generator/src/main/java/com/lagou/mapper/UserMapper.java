package com.lagou.mapper;

import com.lagou.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lengyue
 * @since 2022-08-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 添加用户
     */
    void save(User user);
}
