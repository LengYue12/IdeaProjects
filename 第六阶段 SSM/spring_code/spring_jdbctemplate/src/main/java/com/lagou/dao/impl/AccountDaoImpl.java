package com.lagou.dao.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/19 18:02
 * @description
 */

@Repository("accountDao")     // 采用注解形式生成该实例存到IOC容器中
public class AccountDaoImpl implements AccountDao {

    // 注入
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        查询所有账户
     */
    @Override
    public List<Account> findAll() {
        // 需要用到jdbcTemplate
        String sql = "select * from account";
        List<Account> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Account>(Account.class));

        return list;
    }

    /*
        根据id查询账户
     */
    @Override
    public Account findById(Integer id) {

        String sql = "select * from account where id = ?";
        // 占位符在解析时就会被替换成参数值
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class), id);

        return account;
    }

    /*
        添加账户
     */
    @Override
    public void save(Account account) {

        String sql = "insert into account values(null,?,?)";
        jdbcTemplate.update(sql,account.getName(),account.getMoney());
    }

    /*
        更新账户
     */
    @Override
    public void update(Account account) {
        String sql = "update account set money = ? where name = ?";
        jdbcTemplate.update(sql,account.getMoney(),account.getName());
    }

    /*
        根据ID删除账户
     */
    @Override
    public void delete(Integer id) {

        String sql = "delete from account where id = ?";
        jdbcTemplate.update(sql,id);

    }
}
