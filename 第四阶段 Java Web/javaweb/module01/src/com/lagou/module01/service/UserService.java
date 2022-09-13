package com.lagou.module01.service;

import com.lagou.module01.entity.User;

/**
 * 编程实现用户服务接口
 */
public interface UserService {

    /**
     * 自定义成员方法实现根据参数指定的User对象来调用DAO层实现登录功能
     * @param user
     * @return
     */
     User userLoginService(User user);
}
