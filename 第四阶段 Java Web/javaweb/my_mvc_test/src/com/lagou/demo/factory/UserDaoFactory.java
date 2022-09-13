package com.lagou.demo.factory;


import com.lagou.demo.dao.UserDao;
import com.lagou.demo.dao.UserDaoImp;

public class UserDaoFactory {

    /**
     * 通过静态工厂方法模式来实现UserDao实现类对象的创建并返回
     * @return
     */
    public static UserDao getUserDao(){
        return new UserDaoImp();
    }
}
