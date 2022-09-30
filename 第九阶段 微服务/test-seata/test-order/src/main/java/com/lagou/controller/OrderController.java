package com.lagou.controller;

import com.lagou.entity.Order;
import com.lagou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:47
 * @Description
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("save")
    public int save(){
        Order order = new Order();
        order.setId(UUID.randomUUID().toString().replaceAll("-",""));
        order.setUid(1);
        order.setPid(11);
        return orderService.saveOrder(order);
    }
}
