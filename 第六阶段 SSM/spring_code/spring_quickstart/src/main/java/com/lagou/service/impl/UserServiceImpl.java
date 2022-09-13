package com.lagou.service.impl;

import com.lagou.dao.IUserDao;
import com.lagou.service.IUserService;

/**
 * @author zs
 * @date 2022/6/11 18:33
 * @description
 */

public class UserServiceImpl implements IUserService {


    // 声明出一个变量，用于接收注入进来的userDao实例对象
    // 就是用来接收注入进来的userDao实例
    private IUserDao userDao;

    // 借助有参构造来给userDao 成员变量属性进行赋值，让spring通过有参构造完成userDao 实例对象的注入
//    public UserServiceImpl(IUserDao userDao) {
//
//        // 当userDao实例注入进来，成员变量userDao就有值了
//        // 把接收到的userDao赋值给 成员变量userDao
//        this.userDao = userDao;
//    }


    // 通过set方法进行注入，userDao实例对象。才能调用方法
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        // 调用dao层的save方法
        // 借助spring的API 从IOC容器中获取userDao对象
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        IUserDao userDao = (IUserDao) classPathXmlApplicationContext.getBean("userDao");
//
//        userDao.save();

        // 在UserServiceImpl 中 已经完成userDao 实例对象的注入。
        // 就可以通过注入进来的userDao 去调用方法
        userDao.save();

    }
}
