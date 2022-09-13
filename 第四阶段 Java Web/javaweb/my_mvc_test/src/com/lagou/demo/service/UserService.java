package com.lagou.demo.service;

import com.lagou.demo.dao.UserDao;
import com.lagou.demo.entity.User;
import com.lagou.demo.factory.UserDaoFactory;

public class UserService {

    private UserDao userDao;

    public UserService(){
        this.userDao = UserDaoFactory.getUserDao();
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
