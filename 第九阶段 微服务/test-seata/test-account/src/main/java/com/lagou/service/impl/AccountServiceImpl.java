package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.entity.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:38
 * @Description
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public int updateAccountScore(int userId, int score) {
        Account account = accountDao.selectById(userId);
        // 在原来的积分之上，再加10分
        account.setScore(account.getScore()+score);

        return accountDao.updateById(account);
    }
}
