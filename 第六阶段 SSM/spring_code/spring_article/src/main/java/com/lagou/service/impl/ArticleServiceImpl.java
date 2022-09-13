package com.lagou.service.impl;

import com.lagou.dao.ArticleDao;
import com.lagou.domain.Article;
import com.lagou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zs
 * @date 2022/6/20 20:20
 * @description
 */
/*
    业务层实现类
        要在Service层调用Dao层方法
 */
// 使用IOC注解完成对该类的实例创建并放到IOC容器中，在测试中进行注入该实例
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    // Service层需注入Dao对象，进行解耦
    // 依赖注入
    @Autowired
    private ArticleDao articleDao;

    @Override
    public void save(Article article) {

        // 调用Dao层方法
        // 切入点
        articleDao.save(article);
        //System.out.println(1/0);
        System.out.println("添加方法执行了");
    }
}
