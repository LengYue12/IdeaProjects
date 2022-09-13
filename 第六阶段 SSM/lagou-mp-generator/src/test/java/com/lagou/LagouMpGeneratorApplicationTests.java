package com.lagou;

import com.lagou.entity.User;
import com.lagou.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class LagouMpGeneratorApplicationTests {


    @Autowired
    private UserMapper userMapper;



    @Test
    void test(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
