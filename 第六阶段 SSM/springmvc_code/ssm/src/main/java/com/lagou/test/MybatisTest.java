package com.lagou.test;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
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
 * @date 2022/6/25 14:53
 * @description
 */
public class MybatisTest {

    @Test
    public void testMybatis() throws IOException {

        // 获取输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 借助Mybatis的API通过sqlSession.getMapper 获取mapper接口代理对象
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> all = mapper.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
        sqlSession.close();
    }

}
