package com.lagou.dao;

import com.lagou.domain.Account;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/25 13:07
 * @description
 */
/*
    Dao层接口
 */
public interface AccountDao {

    /*
        查询所有账户
     */
    public List<Account> findAll();

    void save(Account account);

    Account findById(Integer id);

    void update(Account account);

    void deleteBatch(Integer[] ids);
}
