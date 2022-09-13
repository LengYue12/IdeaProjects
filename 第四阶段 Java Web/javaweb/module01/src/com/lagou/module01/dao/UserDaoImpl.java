package com.lagou.module01.dao;

import com.lagou.module01.entity.User;
import com.lagou.module01.util.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 编程实现用户数据访问层接口的实现类
 */
public class UserDaoImpl implements UserDao {

    /**
     * 自定义成员方法描述用户登录功能的实现
     * @param user
     * @return
     */
    @Override
    public User userLogin(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 1.获取数据库连接
            connection = DruidUtils.getConnection();
            // 2.准备sql语句
            String sql = "select * from t_user where userName = ? and password = ?";
            // 3.执行sql语句后获取结果并返回
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User tu = new User(resultSet.getInt("id"),
                        resultSet.getString("userName"), resultSet.getString("password"));
                return tu; // 表示查找成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4.释放相关的资源
            DruidUtils.close(connection, preparedStatement, resultSet);
        }
        return  null; // 表示查找失败
    }
}
