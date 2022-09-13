package com.lagou;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/20 18:07
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAR {

    /**
     * 在AR模式下，完成根据主键进行查询
     */
    @Test
    public void testARSelectById(){

        User user = new User();
        user.setId(12L);

        User user1 = user.selectById();
        System.out.println("user1 = " + user1);
    }


    /**
     * 在AR模式下，完成添加操作
     */
    @Test
    public void testARInsert(){

        User user = new User();
        user.setName("吴亦凡");
        user.setAge(30);
        user.setEmail("kjkjk.cc");

        boolean insert = user.insert();
        System.out.println("insert = " + insert);
    }


    /**
     * 在AR模式下，完成更新操作
     */
    @Test
    public void testARUpdate(){

        User user = new User();
        User user1 = user.selectById(12L);

        user.setId(12l);
        user.setName("练习生蔡徐坤");
        user.setVersion(user1.getVersion());

        boolean b = user.updateById();
        System.out.println("b = " + b);
    }


    /**
     * 在AR模式下，完成删除操作
     */
    @Test
    public void testARDelete(){

        User user = new User();
//        user.setId(14l);
        boolean b = user.deleteById(14l);
        System.out.println("b = " + b);
    }


    /**
     * 在AR模式下，根据条件进行查询
     */
    @Test
    public void testARFindByWrapper(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age","20");

        User user = new User();
        List<User> users = user.selectList(queryWrapper);
        users.forEach(System.out::println);
    }



}
