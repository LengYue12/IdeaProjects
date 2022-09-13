package com.lagou.mapper;

import com.lagou.domain.Article;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/9 20:07
 * @description
 */

/*
    文章表的Mapper接口
 */
public interface ArticleMapper {

    /*
        查询文章信息，及关联的评论信息
     */

    public List<Article> findAll();
}
