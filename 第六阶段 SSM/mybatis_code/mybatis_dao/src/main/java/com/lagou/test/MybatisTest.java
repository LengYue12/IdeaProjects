package com.lagou.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.domain.User;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/3 15:00
 * @description
 */
public class MybatisTest {

    /*
        Mybatis的Dao层mapper代理方式 测试方法
     */
    @Test
    public void test1() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findUserById(1);
        System.out.println(user);

        // 释放资源
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> allResultMap = mapper.findAllResultMap();
        for (User user : allResultMap) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }

    /*
        多条件查询：方式一
     */
    @Test
    public void test3() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findByIdAndUsername1(1, "孙悟空");
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }

    /*
        多条件查询：方式二
     */
    @Test
    public void test4() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findByIdAndUsername2(1, "孙悟空");
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }

    /*
        多条件查询：方式三
     */
    @Test
    public void test5() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = new User();
        user1.setUsername("孙悟空");
        user1.setId(1);

        User user = mapper.findByIdAndUsername3(user1);
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }


    /*
        模糊查询：方式一
     */
    @Test
    public void test6() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.findByUsername("%孙悟%");
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }

    /*
        模糊查询：方式二
     */
    @Test
    public void test7() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.findByUsername2("%孙悟%");
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }


    /*
        添加用户：返回主键方式一
     */
    @Test
    public void test8() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("潘金莲");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("水浒");

        System.out.println(user);
        mapper.saveUser(user);

        System.out.println(user);

        // 手动提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }


    /*
        添加用户：返回主键方式二
     */
    @Test
    public void test9() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("北京");

        System.out.println(user);
        mapper.saveUser2(user);

        System.out.println(user);

        // 手动提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }


    /*
        动态sql之if：多条件查询
     */
    @Test
    public void test10() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣");
        user.setId(8);

        List<User> users = mapper.findByIdAndUsernameIf(user);
        for (User user1 : users) {
            System.out.println(user1);
        }
        // 释放资源
        sqlSession.close();
    }


    /*
        动态sql之set：动态更新
     */
    @Test
    public void test11() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("超级赛亚人孙悟空");
        user.setAddress("地球");
        user.setId(1);

        mapper.updateIf(user);

        // 提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }


    /*
        动态sql之foreach：多值查询：集合
     */
    @Test
    public void test12() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(6);
        ids.add(2);

        List<User> users = mapper.findByList(ids);
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }


    /*
        动态sql之foreach：多值查询：数组形式
     */
    @Test
    public void test13() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Integer[] ids = {2,5,8};

        List<User> byArray = mapper.findByArray(ids);
        for (User user : byArray) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }


    /*
        核心配置文件深入：plugins标签：整合pageHelper插件
     */
    @Test
    public void test14() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 设置分页参数
        // 这个方法要写在调用数据库方法的上面，中间不要加代码
        // 参数1：当前页      就是当前要显示第几页的数据
        // 参数2：每页显示的条数
        PageHelper.startPage(1,2);

        List<User> users = mapper.findAllResultMap();
        for (User user : users) {
            System.out.println(user);
        }

        // 获取分页相关的其他参数
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        System.out.println("总条数：" + pageInfo.getTotal());   // 总条数：7
        System.out.println("总页数：" + pageInfo.getPages());   // 总页数：4
        System.out.println("判断当前是否是第一页：" + pageInfo.isIsFirstPage());   // 判断当前是否是第一页：true

        // 释放资源
        sqlSession.close();
    }
}
