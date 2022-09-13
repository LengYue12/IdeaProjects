package com.lagou.test;

import com.lagou.domain.Orders;
import com.lagou.domain.User;
import com.lagou.mapper.OrderMapper;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/8 14:03
 * @description
 */
public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    //在 @Test方法标注的方法执行之前来执行
    // 被before所标注的方法最先执行
    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        sqlSession = sqlSessionFactory.openSession();
    }

    // after所标注的注解最后执行
    @After
    public void after(){

        sqlSession.commit();
        sqlSession.close();
    }

    /*
        测试查询方法
     */
    /*
        其次是被test标注的方法
     */
    @Test
    public void testSelect() throws IOException {


        // 业务调用
        // 获取返回的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> all = mapper.findAll();

        for (User user : all) {
            System.out.println(user);
        }
    }

    /*
        测试添加方法
     */
    @Test
    public void testInsert(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("周淑怡");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("上海");

        mapper.save(user);
    }

    /*
        测试更新方法
     */
    @Test
    public void testUpdate(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣真美");
        user.setBirthday(new Date());
        user.setId(8);

        mapper.update(user);
    }


    /*
        测试删除方法
     */
    @Test
    public void testDelete(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.delete(8);
    }

    /*
        一对一查询（注解方式）

     */
    @Test
    public void testOneToOne(){

        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        List<Orders> allWithUser = mapper.findAllWithUser();

        for (Orders orders : allWithUser) {
            System.out.println(allWithUser);
        }
    }


    /*
        一对多查询（注解方式）

     */
    @Test
    public void testOneToMany(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> allWithOrder = mapper.findAllWithOrder();

        // 因为实现了延迟加载，所以查询结果只有用户信息，并不会立刻把该用户所具有的的订单信息也查询出来
        for (User user : allWithOrder) {
            System.out.println(user);

            // 获取当前用户所具有的订单信息
            // user.getOrdersList()：表示要用到该用户 所具有的订单信息了
            // 此时就会再次发起查询，根据该用户的id 查询出该用户所具有的订单信息

            // 发起第二次查询，表名 namespace.id 对应的方法要被调用了
            System.out.println(user.getOrdersList());
        }
    }





    /*
        多对多查询（注解方式）

     */
    @Test
    public void testManyToMany(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> allWithRole = mapper.findAllWithRole();

        // 因为实现了延迟加载，所以查询结果只有用户信息，并不会立刻把该用户所具有的的订单信息也查询出来
        for (User user : allWithRole) {
            System.out.println(user);

            // 获取当前用户所具有的订单信息
            // user.getOrdersList()：表示要用到该用户 所具有的订单信息了
            // 此时就会再次发起查询，根据该用户的id 查询出该用户所具有的订单信息

            // 发起第二次查询，表名 namespace.id 对应的方法要被调用了
            System.out.println(user.getRoleList());
        }
    }






    /*
        测试注解实现二级缓存

        基于注解实现二级缓存
     */
    @Test
    public void cacheTest(){

        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user1 = userMapper1.findById(1);

        System.out.println(user1);

        // user1从数据库查询出来后放到一级缓存中，当执行 sqlSession1.close()  就会从一级缓存中把user1刷新到二级缓存中
        // 才能将内容从一级缓存刷新到二级缓存
        sqlSession1.close();

        // 这次查询时查询二级缓存中的内容
        User user2 = userMapper2.findById(1);

        // 从二级缓存中拿出user
        System.out.println(user2);

        sqlSession2.close();

    }
}
