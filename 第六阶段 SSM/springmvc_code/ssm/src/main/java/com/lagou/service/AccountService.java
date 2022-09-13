package com.lagou.service;

import com.lagou.domain.Account;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/25 15:04
 * @description
 */
/*
    service层接口
 */
public interface AccountService {

    public List<Account> findAll();

    public void save(Account account);

    Account findById(Integer id);

    void update(Account account);

    void deleteBatch(Integer[] ids);
}
