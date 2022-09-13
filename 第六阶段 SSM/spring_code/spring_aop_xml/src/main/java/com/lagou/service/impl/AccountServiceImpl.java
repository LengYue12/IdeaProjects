package com.lagou.service.impl;

import com.lagou.service.AccountService;

/**
 * @author zs
 * @date 2022/6/16 17:25
 * @description
 */
public class AccountServiceImpl implements AccountService {

    /*
        目标方法：（切入点：要进行拦截增强的方法）
     */
    @Override
    public void transfer() {
        System.out.println("转账方法执行了...");
        //System.out.println(1/0);
    }
}
