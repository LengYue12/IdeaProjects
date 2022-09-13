package com.lagou.dao;

import com.lagou.pojo.SysRegion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 18:10
 * @Description
 */
@Mapper
public interface SysRegionDao {

    /**
     * 公共表插入数据方法
     */
    @Insert("insert into sys_region(region_name,region_code,region_level) values(#{region_name}," +
        "#{region_code},#{region_level})")
    void insertRegion(SysRegion sysRegion);


}
