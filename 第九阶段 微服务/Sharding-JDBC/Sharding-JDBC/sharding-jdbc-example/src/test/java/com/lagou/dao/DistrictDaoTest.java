package com.lagou.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 19:44
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictDaoTest {


    @Autowired
    private DistrictDao districtDao;


    @Test
    public void testInsert(){
        districtDao.insertDist("昌平区",2);
        districtDao.insertDist("朝阳区",2);
    }


    @Test
    public void testDelete(){
        districtDao.deleteDict(774721291797135361L);
        districtDao.deleteDict(774721292011044864L);
    }
}
