package com.lagou.test;

import com.lagou.domain.Article;
import com.lagou.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zs
 * @date 2022/6/20 21:04
 * @description
 */
// 使用spring整合junit进行单元测试，显示最终效果
// 替换运行环境，为Spring运行环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定核心配置文件路径
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ArticleServiceImplTest {

    // 注入ArticleServiceImpl对象
    @Autowired
    private ArticleService articleService;


    // 测试
    @Test
    public void testSave(){

        Article article = new Article();
        article.setTitle("穿越者的自我修养");
        article.setContent("作为一名穿越者，首先要父母双亡，无牵无挂...");
        articleService.save(article);
    }
}
