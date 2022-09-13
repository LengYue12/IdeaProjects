package com.lagou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lagou.pojo.OrderVO;
import com.lagou.pojo.Orders;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 14:11
 * @Description
 */
public interface OrderService {

    /**
     * 根据订单id查询订单
     */
    Orders findById(Integer oid);

    /**
     * 分页查询
     */
    IPage<Orders> selectPage(OrderVO orderVO);
}
