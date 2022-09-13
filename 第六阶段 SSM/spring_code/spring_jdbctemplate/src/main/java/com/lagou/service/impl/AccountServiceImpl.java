package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/19 18:16
 * @description
 */
@Service("accountService")    // 生成该类实例存到IOC容器中
public class AccountServiceImpl implements AccountService {

    // 在Service层中调用dao层方法
    // 所以需要注入dao层的对象
    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void save(Account account) {

        accountDao.save(account);
    }

    @Override
    public void update(Account account) {

        accountDao.update(account);
    }

    @Override
    public void delete(Integer id) {

        accountDao.delete(id);
    }
}
