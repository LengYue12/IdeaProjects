package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zs
 * @date 2022/6/15 17:38
 * @description
 *
 *      把当前实例对象交个IOC容器管理，同时还要为当前实例对象完成依赖注入
 */

// 采用注解方式，生成当前实例存到IOC容器中
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    // 在当前业务层中要调用持久层方法，所以就要注入持久层对象

    // 完成依赖注入，根据类型在当前实例对象中注入AccountDao实例对象
    @Autowired
    private AccountDao accountDao;



    /*
        转账方法      切入点       当前要增强的业务逻辑：就是为该方法上添加上事务控制的效果
            要对 transfer 这个方法进行拦截增强
     */
    @Override
    public void transfer(String outUser, String inUser, Double money) {

            // 调用了减钱方法，转出操作
            accountDao.out(outUser,money);

            int i = 1/0;

            // 调用了加钱方法，转入操作
            accountDao.in(inUser,money);

    }

    @Override
    public void save() {
        System.out.println("save方法");
    }

    @Override
    public void update() {
        System.out.println("update方法");
    }

    @Override
    public void delete() {
        System.out.println("delete方法");
    }
}
