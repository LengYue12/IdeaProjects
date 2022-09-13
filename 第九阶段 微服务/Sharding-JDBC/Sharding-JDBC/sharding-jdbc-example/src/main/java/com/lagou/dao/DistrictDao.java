package com.lagou.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 19:42
 * @Description
 */
@Mapper
public interface DistrictDao {


    /**
     * 插入
     */
    @Insert("INSERT INTO district(district_name,level) VALUES(#{district_name},#{level})")
    void insertDist(@Param("district_name") String district_name,@Param("level") int level);


    /**
     * 删除数据
     */
    @Delete("delete from district where id = #{id}")
    void deleteDict(@Param("id")Long id);
}
