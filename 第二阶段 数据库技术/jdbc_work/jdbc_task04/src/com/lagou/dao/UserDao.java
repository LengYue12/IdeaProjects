package com.lagou.dao;

import com.lagou.entity.User;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {

    // 需求1：编写一个注册用户的方法，接收的参数是一个User对象
    public int register(User user) throws SQLException {

        // 1.获取QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "insert into user values(?,?,?,?,?,?);";

        Object[] param = {user.getUid(), user.getUsername(), user.getPassword(),
        user.getTelephone(), user.getBirthday(), user.getSex()};

        // 3.执行插入操作
        int update = qr.update(sql, param);

        return update;
    }

    // 需求二：编写一个 用户登录的方法，接收的参数是用户名和密码，返回值是User对象
    public User login(String username, String password) throws SQLException {

        // 1.获取QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "select * from user where username = ? and password = ?";

        // 3.执行查询   使用BeanHandler来封装结果集，获取结果集中的第一条数据
        User user = qr.query(sql, new BeanHandler<User>(User.class), username, password);

        return user;
    }

}
