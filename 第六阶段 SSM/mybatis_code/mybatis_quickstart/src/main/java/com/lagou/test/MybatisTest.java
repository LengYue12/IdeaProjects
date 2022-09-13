package com.lagou.test;

import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/2 18:26
 * @description
 */
public class MybatisTest {

    /*
        快速入门测试方法
     */
    @Test
    public void mybatisQuickStart() throws IOException {

        // 1.加载核心配置文件,加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象，就是生产sqlSession会话对象的。再借助工厂对象获取到会话对象
        // SqlSessionFactoryBuilder SQLSession工厂类的构建者
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象，借助sqlSession会话对象去操作数据库
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，操作数据库    参数：statementid：namespace.id     返回结果就是List集合，集合中就是user对象
        List<User> users = sqlSession.selectList("userMapper.findAll");

        // 5.遍历打印结果
        for (User user : users) {
            System.out.println(user);
        }

        // 6.释放资源
        sqlSession.close();
    }


    /*
        测试新增用户
     */
    @Test
    public void testSave() throws IOException {
        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        // 4.执行sql，调用api方法
        User user = new User();
        user.setUsername("自动提交事务");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("流沙河");
        sqlSession.insert("userMapper.saveUser",user);

        // 5.关闭资源
        // 当前做的是新增操作，增删改这样的操作都会改变数据库的状态，所以对于增删改操作需要进行事务提交
        // 手动提交事务
        // 因为数据库表id是自动增长的，就算执行sql后，没有提交事务，也会自增id，没有提交事务，进行回滚，id自增。
        //sqlSession.commit();
        sqlSession.close();
    }


    /*
        测试更新用户
     */
    @Test
    public void testUpdate() throws IOException {

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，调用sqlSession的api方法
        User user = new User();
        user.setId(4);
        user.setUsername("蜘蛛精");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("盘丝洞");

        sqlSession.update("userMapper.updateUser",user);
        // 5.手动提交事务并关闭资源
        sqlSession.commit();
        sqlSession.close();
    }

    /*
        测试删除用户
     */
    @Test
    public void testDelete() throws IOException {

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，调用sqlSession的API方法
        sqlSession.delete("userMapper.deleteUser",4);

        // 手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }
}
