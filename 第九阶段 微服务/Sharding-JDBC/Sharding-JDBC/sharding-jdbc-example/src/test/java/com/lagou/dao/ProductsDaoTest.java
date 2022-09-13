package com.lagou.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 20:08
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsDaoTest {

    @Autowired
    private ProductsDao productsDao;


    /**
     * 读写分离插入测试
     */
    @Test
    public void testInsert(){

        for (int i = 0; i < 5; i++) {
            productsDao.insertProduct(100L+i,"小米手机",1888,"1");
        }
        
    }


    /**
     * 读写分离查询测试
     */
    @Test
    public void testSelect(){
        List<Map> all = productsDao.findAll();
        System.out.println(all);
    }
}
