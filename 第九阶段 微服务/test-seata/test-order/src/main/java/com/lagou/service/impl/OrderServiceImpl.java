package com.lagou.service.impl;

import com.lagou.dao.OrderDao;
import com.lagou.entity.Order;
import com.lagou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:52
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public int saveOrder(Order order) {

        return orderDao.insert(order);
    }
}
