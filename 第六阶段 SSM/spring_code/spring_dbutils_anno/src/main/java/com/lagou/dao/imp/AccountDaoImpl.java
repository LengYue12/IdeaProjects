package com.lagou.dao.imp;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 22:28
 * @description
 */

/**
 *  注解方式
 *      持久层的类要使用 @Repository
 *          @Repository 这个注解是使用在dao层类上 用于实例化Bean的注解
 */
@Repository     // 相当于配置了bean标签
public class AccountDaoImpl implements AccountDao {

    @Autowired      // 使用注解方式进行注入
    private QueryRunner qr;

    @Override
    public List<Account> findAll() {

        List<Account> list = null;
        // 编写Sql
        String sql = "select * from account";
        try {
            // 执行SQL
            list = qr.query(sql, new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Account findById(Integer id) {

        Account query = null;

        // 编写SQL
        String sql = "select * from account where id = ?";

        try {
            query = qr.query(sql, new BeanHandler<Account>(Account.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }

    @Override
    public void save(Account account) {

        String sql = "insert into account values(null,?,?)";
        try {
            qr.update(sql, account.getName(), account.getMoney());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {

        String sql = "update account set name = ?,money = ? where id = ?";
        try {
            qr.update(sql,account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {

        String sql = "delete from account where id = ?";
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
