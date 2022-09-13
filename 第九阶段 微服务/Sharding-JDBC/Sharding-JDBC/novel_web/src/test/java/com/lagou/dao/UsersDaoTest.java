package com.lagou.dao;

import com.lagou.pojo.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 17:38
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersDaoTest {


    @Autowired
    private UsersDao usersDao;
    
    
    /**
     * 测试插入数据
     * 观察数据是否根据分片规则落入对应的数据库
     */
    @Test
    public void testInsert(){

        // 当id为偶数时，根据分片规则，插入到users_1数据表,反之时users_2数据表
        for (int i = 0; i <= 100; i++) {
            // 插入数据
            usersDao.insertUsers(new Users((long) i,"张飞","000","蜀国"));
        }
    }
}
