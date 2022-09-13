package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.pojo.Products;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/27 22:42
 * @Description
 */
public interface GoodsMapper extends BaseMapper<Products> {

    /**
     * 根据订单项表里的订单oid 查询出商品列表
     * @param oid
     * @return
     */
    // SELECT * FROM products p INNER JOIN orderitem ot ON p.pid = ot.pid WHERE ot.oid = ?
    @Select("SELECT * FROM products p INNER JOIN orderitem ot ON p.pid = ot.pid WHERE ot.oid = #{oid}")
    List<Products> getProduct(Integer oid);
}
