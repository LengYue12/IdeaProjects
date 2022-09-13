package com.lagou.dao.impl;

import com.lagou.dao.IUserDao;

/**
 * @author zs
 * @date 2022/6/11 11:43
 * @description
 */
public class UserDaoImpl implements IUserDao {

    @Override
    public void save() {
        System.out.println("dao被调用啦，保存成功...");
    }
}
