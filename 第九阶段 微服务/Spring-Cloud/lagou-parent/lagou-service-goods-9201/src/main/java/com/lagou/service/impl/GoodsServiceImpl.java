package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lagou.mapper.GoodsMapper;
import com.lagou.pojo.ProductVO;
import com.lagou.pojo.Products;
import com.lagou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/27 22:41
 * @Description
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Products findById(int pid) {
        return goodsMapper.selectById(pid);
    }

    @Override
    public void deleteById(int pid) {
        goodsMapper.deleteById(pid);
    }

    @Override
    public void updateById(Products products) {
        goodsMapper.updateById(products);
    }

    @Override
    public IPage<Products> selectPage(ProductVO productVO) {

        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.like("name",productVO.getName());
        wrapper.eq("goods_type",productVO.getGoodsType());
        wrapper.between("price",productVO.getMinPrice(),productVO.getMaxPrice());
        wrapper.between("goods_stock",productVO.getMinGoodsStock(),productVO.getMaxGoodsStock());
        wrapper.eq("status",productVO.getStatus());

        Page<Products> page = new Page<>(productVO.getCurrentPage(),productVO.getPageSize());

        // 分页查询，根据条件查询数据
        return goodsMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Products> getProduct(Integer oid) {
        return goodsMapper.getProduct(oid);
    }

}
