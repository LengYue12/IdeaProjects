package com.lagou.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 17:07
 * @Description
 */
@Mapper
public interface PayOrderDao {

    /**
     * 新增订单
     * @param user_id
     * @param product_name
     * @param count
     */
    @Insert("INSERT INTO pay_order(user_id,product_name,COUNT) VALUES(#{user_id},#{product_name},#{count})")
    void insertPayOrder(@Param("user_id")int user_id,@Param("product_name")String product_name,@Param("count")int count);


    /**
     * 根据Id 查询订单
     */
    @Select({"<script>" +
        "select * from pay_order p where p.order_id in " +
            "<foreach collection='orderIds' item='id' open='(' separator = ',' close = ')'> #{id} </foreach>"
        + "</script>"
    })
    List<Map> findOrderByIds(@Param("orderIds") List<Long> orderIds);
}
