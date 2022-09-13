package com.lagou.dao;

import com.lagou.pojo.Novel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 17:14
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelDaoTest {

    @Autowired
    private NovelDao novelDao;


    /**
     * 读写分离的插入测试
     * 写主库
     */
    @Test
    public void testInsert(){

        Novel novel = new Novel();
        novel.setId(2L);
        novel.setTitle("斗罗大陆");
        novel.setAuthor("唐家三少");
        novel.setPic("http://images.com");
        novel.setContent("穿越到斗罗大陆的唐三如何一步步修炼武魂，由人修炼为神，最终铲除了斗罗大陆上的邪恶力量，报了杀母之仇，成为斗罗大陆最强者");

        novelDao.insertNovel(novel);
    }


    /**
     * 读写分离的查询测试
     * 读从库
     */
    @Test
    public void testSelect(){

        Novel byId = novelDao.findById(2L);
        System.out.println(byId);
    }
}
