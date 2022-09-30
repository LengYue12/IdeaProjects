package com.lagou.service.impl;

import com.lagou.remote.AccountRemoteService;
import com.lagou.remote.OrderRemoteService;
import com.lagou.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 14:05
 * @Description
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private AccountRemoteService accountRemoteService;

    @Autowired
    private OrderRemoteService orderRemoteService;

    @Override
    @GlobalTransactional // 全局事务
    public boolean business() {


        accountRemoteService.update();
        int i = 1/0;
        orderRemoteService.save();

        return true;
    }
}
