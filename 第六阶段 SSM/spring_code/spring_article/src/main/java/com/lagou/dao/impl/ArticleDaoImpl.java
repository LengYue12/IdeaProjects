package com.lagou.dao.impl;

import com.lagou.dao.ArticleDao;
import com.lagou.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author zs
 * @date 2022/6/20 20:14
 * @description
 */
/*
    Dao层实现类
 */
@Repository("articleDao")     // 使用注解完成对该类的实例创建并存到IOC容器中
public class ArticleDaoImpl implements ArticleDao {

    // 持久层采用JDBCTemplate完成对数据库的操作
    // 使用依赖注入，从IOC容器中获取到JdbcTemplate实例对象注入给参数
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 添加文章信息方法
    @Override
    public void save(Article article) {

        // 使用JdbcTemplate完成对数据库的操作
        String sql = "insert into t_article values(null,?,?)";
        jdbcTemplate.update(sql,article.getTitle(),article.getContent());
    }
}
