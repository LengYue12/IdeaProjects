package com.lagou.dao.imp;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 22:28
 * @description
 */
public class AccountDaoImpl implements AccountDao {

    // 需要QueryRunner 对象，就可以把QueryRunner对象的创建交给Spring的IOC容器去创建
    // 而在AccountDaoImpl中 需要这个QueryRunner对象，那直接去IOC容器中去获取。进行注入
    private QueryRunner qr;

    // 表示要让spring借助set方式来把QueryRunner  注入进来，并赋值给qr成员变量
    public void setQr(QueryRunner qr) {
        this.qr = qr;
    }

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
