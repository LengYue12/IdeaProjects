package com.lagou.module01.service;

import com.lagou.module01.entity.User;
import com.lagou.module01.dao.UserDao;
import com.lagou.module01.factory.DaoFactory;

/**
 * 编程实现用户服务实现类的功能
 */
public class UserServiceImpl implements UserService {
    /**
     * 自定义成员变量来记录用户数据访问对象
     */
    private UserDao userDao;

    /**
     * 自定义构造方法实现成员变量的初始化
     */
    public UserServiceImpl() {
        this.userDao = DaoFactory.getUserDao();
    }

    /**
     * 自定义成员方法实现根据参数指定的User对象来调用DAO层实现登录功能
     * @param user
     * @return
     */
    public User userLoginService(User user) {
        return userDao.userLogin(user);
    }
}
