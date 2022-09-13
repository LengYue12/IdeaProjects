package com.lagou.test;

import com.lagou.domain.Orders;
import com.lagou.domain.User;
import com.lagou.mapper.OrderMapper;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/4 15:06
 * @description
 */
public class mybatisTest {

    /*
        一对一 关联查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    @Test
    public void test1() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        // 调用方法
        List<Orders> orders = mapper.findAllWithUser();

        for (Orders order : orders) {
            // 所有的订单信息
            System.out.println(order);
        }

        // 关闭资源
        sqlSession.close();
    }



    /*
        一对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */

    @Test
    public void test2() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 使用代理对象调用方法
        List<User> allWithOrder = mapper.findAllWithOrder();

        for (User user : allWithOrder) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }


    /*
        多对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */

    @Test
    public void test3() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 使用代理对象调用方法
        List<User> allWithRole = mapper.findAllWithRole();

        for (User user : allWithRole) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }


    /*
        一对一嵌套查询:查询订单及关联的用户信息
     */

    @Test
    public void test4() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        // 使用代理对象调用方法
        List<Orders> allWithUser2 = mapper.findAllWithUser2();

        for (Orders orders : allWithUser2) {
            System.out.println(orders);
        }

        // 关闭资源
        sqlSession.close();
    }



    /*
        一对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */

    @Test
    public void test5() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 使用代理对象调用方法
        List<User> allWithOrder2 = mapper.findAllWithOrder2();

        for (User user : allWithOrder2) {
            System.out.println(user);

            // 要用到该用户的订单信息了
            //System.out.println(user.getOrdersList());

        }

        // 关闭资源
        sqlSession.close();
    }



    /*
        多对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */

    @Test
    public void test6() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 使用代理对象调用方法
        List<User> allWithRole2 = mapper.findAllWithRole2();

        for (User user : allWithRole2) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }



    /*
        验证Mybatis中的一级缓存：sqlSession级别的缓存
     */
    @Test
    public void testOneCache() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 根据id查询用户信息
        // 两次查询，但是SQL语句只执行了一次
        // 第一次查询，查询的是数据库
        User user1 = userMapper.findById(1);
        System.out.println(user1);

        // clearCache:手动清空缓存
        // 调用完clearCache之后，一级缓存被清空了。所以第二次查询时，会再次执行SQL，去查询数据库
        //sqlSession.clearCache();


        // 第二次查询，查询的是一级缓存
        User user2 = userMapper.findById(1);
        System.out.println(user2);

        sqlSession.close();
    }


    /*
        验证Mybatis中的二级缓存
            跨sqlSession的，两个sqlSession共享了二级缓存，因为都是对同一个namespace进行操作。都是对UserMapper 进行的操作

            二级缓存是 namespace级别，mapper映射级别的缓存。跨sqlSession的级别，当多个sqlSession去操作同一个Mapper映射的sql语句时，
            多个sqlSession可以共用二级缓存
     */
    @Test
    public void testTwoCache() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);

        // 第一次查询    从数据库进行查询，将结果放到一级缓存中
        User user = userMapper1.findById(1);

        System.out.println(user);

        // 只有执行sqlSession.commit 或者 sqlSession.close，才会清空一级缓存同时把一级缓存的内容刷新到二级缓存

        // 只有执行sqlSession.commit 或者 sqlSession.close， 那么一级缓存中内容才会刷新到二级缓存
        sqlSession1.close();


        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user2 = userMapper2.findById(1);

        // 从二级缓存中查询出来的user信息
        System.out.println(user2);

        sqlSession2.close();


    }
}
