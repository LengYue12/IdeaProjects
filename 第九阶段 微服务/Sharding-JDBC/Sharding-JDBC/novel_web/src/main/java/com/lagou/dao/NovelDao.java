package com.lagou.dao;

import com.lagou.pojo.Novel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 17:00
 * @Description
 */
@Mapper
public interface NovelDao {

    /**
     * 读写分离
     * 插入方法
     */
    @Insert("insert into novel(id,title,author,pic,content) values(#{id},#{title},#{author},#{pic},#{content})")
    void insertNovel(Novel novel);


    /**
     * 读写分离 查询
     * 根据id查询方法
     */
    @Select("select * from novel where id = #{id}")
    Novel findById(Long id);

}
