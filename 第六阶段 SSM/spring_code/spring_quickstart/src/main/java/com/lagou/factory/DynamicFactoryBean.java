package com.lagou.factory;

import com.lagou.dao.IUserDao;
import com.lagou.dao.impl.UserDaoImpl;

/**
 * @author zs
 * @date 2022/6/11 18:19
 * @description
 */
public class DynamicFactoryBean {

    // 普通工厂类
    public IUserDao createUserDao(){

        return new UserDaoImpl();
    }
}
