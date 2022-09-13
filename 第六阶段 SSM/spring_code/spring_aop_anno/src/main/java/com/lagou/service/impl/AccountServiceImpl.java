package com.lagou.service.impl;

import com.lagou.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author zs
 * @date 2022/6/16 20:19
 * @description
 */
@Service        // 生成该类实例存到IOC容器中
public class AccountServiceImpl implements AccountService {

    // 切入点方法
    @Override
    public void transfer() {
        System.out.println("转账方法执行了...");
    }
}
