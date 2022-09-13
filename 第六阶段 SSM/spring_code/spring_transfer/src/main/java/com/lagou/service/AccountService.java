package com.lagou.service;

/**
 * @author zs
 * @date 2022/6/15 17:35
 * @description
 */
public interface AccountService {

    // 转账方法
    // 指定转出账户，转入账户和操作的金额
    public void transfer(String outUser,String inUser,Double money);


    public void save();

    public void update();

    public void delete();
}
