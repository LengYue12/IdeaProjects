package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/25 15:05
 * @description
 */
/*
    service层实现类
 */
@Service("accountService")      // 生成该类的实例对象存到IOC
@Transactional      // 开启事务控制
public class AccountServiceImpl implements AccountService {

    // 需要用到AccountDao的代理对象的
    @Autowired      // 注入生成的Dao 代理对象
    private AccountDao accountDao;



    /*
        测试Spring在ssm环境中的单独使用
     */
    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    /*
        账户添加
     */
    @Override
    public void save(Account account) {

        accountDao.save(account);
    }

    /*
        根据ID查询
     */
    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    /*
        更新操作
     */
    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    /*
        删除操作
     */
    @Override
    public void deleteBatch(Integer[] ids) {
        accountDao.deleteBatch(ids);
    }

}
