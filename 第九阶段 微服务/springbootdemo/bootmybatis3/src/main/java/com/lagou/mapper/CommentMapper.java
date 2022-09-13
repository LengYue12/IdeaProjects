package com.lagou.mapper;

import com.lagou.pojo.Comment;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/14 20:58
 * @Description 通过注解实现根据id查找评论信息
 */
public interface CommentMapper {

    @Select("select * from t_comment where id = #{id}")
    Comment findById(Integer id);
}
