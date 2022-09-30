package com.lagou.service;

import com.lagou.entity.Order;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:51
 * @Description
 */
public interface OrderService {

    int saveOrder(Order order);
}
