package com.lagou.factory;

import com.lagou.dao.IUserDao;
import com.lagou.dao.impl.UserDaoImpl;

/**
 * @author zs
 * @date 2022/6/11 18:09
 * @description
 */
public class StaticFactoryBean {

    public static IUserDao createUserDao(){

        return new UserDaoImpl();
    }
}
