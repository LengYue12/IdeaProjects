package test;

import entity.Users;
import mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:26
 * @Description 测试dao层
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class TestDao {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void register(){

        Users users = new Users(null,"a1", "1", "11", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        userMapper.register(users);

        System.out.println("注册成功！");

    }



    @Test
    public void method(){

        Users users = new Users(1,"aa1","123","13338",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        userMapper.update(users);


    }

    @Test
    public void method1(){
        List<Users> users = userMapper.findByUsername("a");
        System.out.println(users);
    }
}
