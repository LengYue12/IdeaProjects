package com.lagou.dao.impl;

import com.lagou.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author zs
 * @date 2022/6/19 19:34
 * @description
 */
// dao层要被service层调用，service层要注入该实例对象
// 所以添加注解，生成该类实例存到IOC容器中
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    // 要借助jdbcTemplate模板对象调用API方法，完成对数据库的操作
    // 所以要注入
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void out(String outUser, Double money) {

        String sql = "update account set money = money - ? where name = ?";
        jdbcTemplate.update(sql,money,outUser);
    }

    @Override
    public void in(String inUser, Double money) {

        String sql = "update account set money = money + ? where name = ?";
        jdbcTemplate.update(sql,money,inUser);
    }
}
