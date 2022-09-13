package com.lagou;

import com.lagou.mapper.ArticleMapper;
import com.lagou.mapper.CommentMapper;
import com.lagou.pojo.Article;
import com.lagou.pojo.Comment;
import com.lagou.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class BootmybatisApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void findCommentById() {
        Comment byId = commentMapper.findById(1);
        System.out.println("byId = " + byId);
    }


    @Autowired
    private ArticleMapper articleMapper;
    @Test
    void findArticleById() {
        Article article = articleMapper.selectByPrimaryKey(1);
        System.out.println("article = " + article);
    }

    // 写入，key:1,value:mysql数据库中id为1的article记录
    @Autowired
    private RedisUtils redisUtils;

    @Test
    void writeRedis(){
        redisUtils.set("1",articleMapper.selectByPrimaryKey(1));
        System.out.println("success");
    }

    @Test
    void readRedis(){
        Article article = (Article) redisUtils.get("1");
        System.out.println(article);
    }


    @Test
    void deleteRedis(){
        boolean delete = redisUtils.delete("1");
        System.out.println("delete = " + delete);
    }
}
