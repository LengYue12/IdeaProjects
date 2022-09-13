package com.lagou.dao;

/**
 * @author zs
 * @date 2022/6/15 17:21
 * @description
 */
public interface AccountDao {

    // 转账本质就是：同时调用这两个方法，实现一个账户加钱，一个账户减钱

    // 转出操作，减钱          表示哪个账户要转出多少钱
    public void out(String outUser,Double money);

    // 转入操作，加钱          表示哪个账户要增加多少钱
    public void in(String inUser,Double money);
}
