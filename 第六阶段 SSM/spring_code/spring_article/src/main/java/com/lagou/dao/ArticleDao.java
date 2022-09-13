package com.lagou.dao;

import com.lagou.domain.Article;

/**
 * @author zs
 * @date 2022/6/20 20:13
 * @description
 */
/*
    Dao层接口
 */
public interface ArticleDao {

    // 添加文章信息方法
    public void save(Article article);
}
