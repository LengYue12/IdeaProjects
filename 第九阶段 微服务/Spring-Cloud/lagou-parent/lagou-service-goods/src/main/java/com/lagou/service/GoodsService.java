package com.lagou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lagou.pojo.ProductVO;
import com.lagou.pojo.Products;

import java.util.List;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/27 22:39
 * @Description
 */
public interface GoodsService {

    /**
     * 通过id查询商品
     */
    Products findById(int pid);


    /**
     * 通过id删除商品
     */
    void deleteById(int pid);


    /**
     * 通过id编辑商品
     */
    void updateById(Products products);


    /**
     * 分页查询
     */
    IPage<Products> selectPage(ProductVO productVO);


    /**
     * 根据订单id查询商品列表
     */
    List<Products> getProduct(Integer oid);
}
