package com.lagou.demo01.dao;

import com.lagou.demo01.model.User;
import com.lagou.demo01.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 注册功能
     * @param user
     * @return
     */
    public int createUser(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取连接
            connection = DbUtil.getConnection();
            // 2.准备SQL语句
            String sql = "insert into t_user values(null,?,?)";
            // 3.获取PrepareStatement对象
            preparedStatement = connection.prepareStatement(sql);
            // 4.向问号所占的位置设置数据
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            // 5.执行sql语句
            return preparedStatement.executeUpdate();   // 执行成功
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;   // 执行失败
        } finally {
            // 6.关闭资源
            try {
                DbUtil.close(connection,preparedStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean loginUser(User user){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            // 1.获取连接
            connection = DbUtil.getConnection();
            // 2.准备sql语句
            String sql = "select * from t_user where userName = ? and password = ?";
            // 3.获取PrepareStatement对象
            ps = connection.prepareStatement(sql);
            // 4.设置数据
            ps.setString(1,user.getUserName());
            ps.setString(2,user.getPassword());
            // 5.执行SQL语句
            ResultSet resultSet = ps.executeQuery();
            // 6.处理结果集对象
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 7.关闭资源
            DbUtil.close(connection,ps);
        }

    }
}
