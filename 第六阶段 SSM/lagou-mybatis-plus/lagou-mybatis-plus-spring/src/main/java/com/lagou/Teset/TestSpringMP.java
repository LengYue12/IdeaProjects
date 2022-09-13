package com.lagou.Teset;

import com.lagou.mapper.UserMapper;
import com.lagou.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/19 15:54
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSpringMP {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void method(){
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
}
