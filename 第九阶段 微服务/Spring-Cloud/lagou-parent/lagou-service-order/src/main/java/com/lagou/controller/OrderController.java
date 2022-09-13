package com.lagou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lagou.feign.ProductFeign;
import com.lagou.pojo.OrderVO;
import com.lagou.pojo.Orders;
import com.lagou.pojo.Products;
import com.lagou.pojo.ResponseResult;
import com.lagou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 14:13
 * @Description
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeign productFeign;


    @GetMapping("/find/{oid}")
    public Orders findById(@PathVariable Integer oid){
        return orderService.findById(oid);
    }

    // 分页查询
    @GetMapping("/page")
    public ResponseResult selectPage(@RequestBody OrderVO orderVO){
        IPage<Orders> ordersIPage = orderService.selectPage(orderVO);
        Map<String, Object> map = new HashMap<>();
        map.put("list",ordersIPage.getRecords());
        map.put("total",ordersIPage.getTotal());
        return new ResponseResult(true,200,"查询成功",map);
    }

    /**
     * 根据订单id查询商品列表
     */
    @GetMapping("/query/{oid}")
    public List<Products> queryList(@PathVariable Integer oid){

        return productFeign.getProduct(oid);
    }
}
