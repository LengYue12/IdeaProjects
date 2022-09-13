package com.lagou.service;

import com.lagou.domain.Account;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 22:45
 * @description
 */
public interface AccountService {

    /*
        查询所有账户信息方法
     */
    public List<Account> findAll();

    /*
        根据id查询账户的方法
     */
    public Account findById(Integer id);


    /*
        添加方法
     */
    public void save(Account account);

    /*
        更新方法
     */
    public void update(Account account);

    /*
        删除方法
     */
    public void delete(Integer id);
}
