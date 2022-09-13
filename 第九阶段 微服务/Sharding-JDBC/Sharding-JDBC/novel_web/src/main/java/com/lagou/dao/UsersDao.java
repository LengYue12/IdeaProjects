package com.lagou.dao;

import com.lagou.pojo.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 17:27
 * @Description
 */
@Mapper
public interface UsersDao {


    /**
     * 编写插入方法   users为逻辑表,users_1和users_2是实际表
     * id为分片键，
     * 分片策略为users_$->{id % 2 + 1}，id为偶数操作users_1
     * 实际表，否则操作users_2。
     */
    @Insert("insert into users(id,username,password,address) values(#{id},#{username},#{password},#{address})")
    void insertUsers(Users users);

}
