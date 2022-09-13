package com.lagou.module01.factory;

import com.lagou.module01.dao.StudentDao;
import com.lagou.module01.dao.StudentDaoImpl;
import com.lagou.module01.dao.UserDao;
import com.lagou.module01.dao.UserDaoImpl;

/**
 * 编程实现工厂类来进行对象的创建
 */
public class DaoFactory {

    /**
     * 通过静态工程方法模式来实现StudentDao实现类对象的创建并返回
     * @return
     */
    public static StudentDao getStudentDao() {
        return new StudentDaoImpl();
    }

    /**
     * 通过静态工程方法模式来实现UserDao实现类对象的创建并返回
     * @return
     */
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
