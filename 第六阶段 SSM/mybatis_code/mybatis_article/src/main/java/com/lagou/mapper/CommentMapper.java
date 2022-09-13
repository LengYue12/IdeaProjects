package com.lagou.mapper;

import com.lagou.domain.Comment;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/9 20:29
 * @description
 */

/*
    评论表的Mapper接口
 */
public interface CommentMapper {


    /*
        根据文章 外键a_id查询出对应的评论信息
     */
    public List<Comment> findById(Integer a_id);
}
