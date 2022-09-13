package com.lagou.service;

/**
 * @author zs
 * @date 2022/6/19 19:39
 * @description
 */
public interface AccountService {

    /*
        转账方法
            转出账户名、转入账户名和金额
     */
    public void transfer(String outUser,String inUser,Double money);
}
