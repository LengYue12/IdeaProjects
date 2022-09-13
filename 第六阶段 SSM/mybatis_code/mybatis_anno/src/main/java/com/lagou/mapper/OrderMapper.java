package com.lagou.mapper;

import com.lagou.domain.Orders;
import com.lagou.domain.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/8 16:38
 * @description
 */
public interface OrderMapper {

    /*
        查询所有订单，同时查询订单所属的用户信息
     */

    /*
        先查询出所有的订单信息，再根据订单信息查询出对应的用户信息
     */
    /*
        使用注解方式完成复杂映射开发一对一查询，从订单角度出发，立即加载
     */

    @Select("select * from orders")
    @Results({  // 代替的就是ResultMap标签
            // 完成实体中属性与表中字段的映射关系配置
            // property当前实体中的属性名    id = true  表示当前配置Result注解为主键字段
            @Result(property = "id",column = "id",id = true),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "uid",column = "uid"),
                // property = "user" 表示要配置orders实体里的user对象的映射关系
                // javaType = ""    表示当前要去封装的user对象的类型
                // one = @One(select = "namespace.id")  通过namespace.id 来定位到要执行的SQL
                // com.lagou.mapper.UserMapper.findById  通过这个namespace.id 就可以找到对应的SQL 执行
                // fetchType = FetchType.EAGER
                // 一对一采用立即加载
                // 通过这个属性 可以进行局部延迟加载配置，  配置FetchType.EAGER，表示立即加载，不进行延迟加载局部优先    这样就会查询出订单信息及关联的用户信息
                @Result(property = "user", javaType = User.class, column = "uid",one = @One(select = "com.lagou.mapper.UserMapper.findById",fetchType = FetchType.EAGER))
    })
    public List<Orders> findAllWithUser();



    /*
        根据传递过来的用户id，查询该用户所具有的订单信息，查询到后把List集合封装到user实体中的ordersList 属性中
     */

    @Select("select * from orders where uid = #{uid}")
    public List<Orders> findOrderByUid(Integer uid);


}
