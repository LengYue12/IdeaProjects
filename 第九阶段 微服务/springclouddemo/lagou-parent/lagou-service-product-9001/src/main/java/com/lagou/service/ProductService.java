package com.lagou.service;

import com.lagou.common.pojo.Products;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 19:41
 * @Description
 */
public interface ProductService {

    /**
     * 通过商品id查询商品信息
     * @param id
     * @return
     */
    Products findById(Integer id);
}
