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
 * @CreateTime 2022/9/8 19:30
 * @Description
 */
@Mapper
public interface UsersDao {

    /**
     * 新增用户
     */
    @Insert("insert into users(id,username,phone,status) values(#{id},#{username},#{phone},#{status})")
    void insertUser(@Param("id") Long id,@Param("username") String username,@Param("phone")String phone,@Param("status")String status);


    /**
     * 查询用户
     */
    @Select({"<script>" +
        "select * from users u where u.id in " +
        "<foreach collection='userIds' item='id' open='(' separator = ',' close = ')'> #{id} </foreach>"
        + "</script>"
    })
    List<Map> findUserByIds(@Param("userIds") List<Long> userIds);
}
