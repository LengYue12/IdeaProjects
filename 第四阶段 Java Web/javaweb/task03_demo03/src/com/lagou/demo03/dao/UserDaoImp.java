package com.lagou.demo03.dao;

import com.lagou.demo03.bean.User;
import com.lagou.demo03.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {
    @Override
    public User userLogin(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 1.获取数据库连接
            connection = DbUtil.getConnection();
            // 2.准备SQL语句
            String sql = "select * from t_user where userName = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);
            // 3.执行SQL语句后获取结果并返回
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User tu = new User(resultSet.getString("userName"),resultSet.getString("password"));
                return tu;  // 表示查找成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4.释放相关的资源
            DbUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return null;    // 表示查找失败
    }
}
