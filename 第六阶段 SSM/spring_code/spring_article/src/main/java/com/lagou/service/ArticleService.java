package com.lagou.service;

import com.lagou.domain.Article;

/**
 * @author zs
 * @date 2022/6/20 20:20
 * @description
 */
/*
    业务层接口
 */
public interface ArticleService {

    // 添加文章信息方法
    public void save(Article article);
}
