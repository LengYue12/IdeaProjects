package com.lagou.mapper;

import com.lagou.domain.Orders;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/4 14:39
 * @description
 */
public interface OrderMapper {

    /*
        多表查询的方式
        一对一 关联查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    public List<Orders> findAllWithUser();


    /*
        嵌套查询的方式
        一对一 嵌套查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    public List<Orders> findAllWithUser2();


    /*
        根据uid查询对应订单
     */
    public List<Orders> findByUid(Integer uid);
}
