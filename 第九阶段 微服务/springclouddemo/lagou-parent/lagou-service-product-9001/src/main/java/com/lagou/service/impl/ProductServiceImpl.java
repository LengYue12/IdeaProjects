package com.lagou.service.impl;

import com.lagou.common.pojo.Products;
import com.lagou.mapper.ProductMapper;
import com.lagou.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 20:10
 * @Description
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Products findById(Integer id) {
        return productMapper.selectById(id);
    }
}
