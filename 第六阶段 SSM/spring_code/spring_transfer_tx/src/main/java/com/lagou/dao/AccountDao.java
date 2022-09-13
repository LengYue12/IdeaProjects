package com.lagou.dao;

/**
 * @author zs
 * @date 2022/6/19 19:32
 * @description
 */
public interface AccountDao {

    /*
        减钱：转出操作
     */
    public void out(String outUser,Double money);

    /*
        加钱：转入操作
     */
    public void in(String inUser,Double money);
}
