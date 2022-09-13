package com.lagou.module01.dao;

import com.lagou.module01.entity.User;

/**
 * 编程实现用户数据访问层的接口
 */
public interface UserDao {

    /**
     * 自定义抽象方法描述用户登录功能的实现
     * @param user
     * @return
     */
    User userLogin(User user);
}
