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
 * @CreateTime 2022/9/8 20:05
 * @Description
 */
@Mapper
public interface ProductsDao {

    /**
     * 读写分离插入
     */
    @Insert("insert into products(pid,pname,price,flag) values(#{pid},#{pname},#{price},#{flag})")
    void insertProduct(@Param("pid")Long pid,@Param("pname")String pname,@Param("price")int price,@Param("flag")String flag);


    /**
     * 读写分离 查询
     */
    @Select("select * from products")
    List<Map> findAll();


}
