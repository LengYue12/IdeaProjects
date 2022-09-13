package com.lagou.demo.dao;

import com.lagou.demo.entity.User;
import com.lagou.demo.utils.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * 对UserDao的实现类实现登录功能
 */
public class UserDaoImp implements UserDao {
    @Override
    public User userLogin(User user) {

        // 1.创建QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "select * from user where userName = ? and password = ?";

        // 3.设置占位符参数
        Object[] param = {user.getUserName(),user.getPassword()};

        // 4.执行query方法
        try {
            User query = qr.query(sql, new BeanHandler<User>(User.class), param);
            return query;   // 表示查找成功
        } catch (SQLException e) {
            e.printStackTrace();
            return null;    // 表示查找失败
        }

    }
}
