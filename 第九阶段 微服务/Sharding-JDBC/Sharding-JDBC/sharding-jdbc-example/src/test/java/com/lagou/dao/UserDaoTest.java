package com.lagou.dao;

import com.mysql.jdbc.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 19:33
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {


    @Autowired
    private UsersDao usersDao;

    @Test
    public void testInsert(){

        for (int i = 0; i < 10; i++) {
            Long id = i + 100L;
            usersDao.insertUser(id,"药水"+i,"1239880879","1");
        }
    }



    @Test
    public void testFindUser(){

        List<Long> list = new ArrayList<>();
        list.add(101L);
        list.add(102L);

        List<Map> userByIds = usersDao.findUserByIds(list);
        System.out.println(userByIds );
    }
}
