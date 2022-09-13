package com.lagou.jdbc02;

import com.lagou.utils.JDBCUtils;

import java.sql.*;

public class TestDQL {

    // 查询姓名为张百万的一条数据
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = JDBCUtils.getConnection();

        // 2.创建Statement对象
        Statement statement = connection.createStatement();

        // 3.编写SQL
        String sql = "select * from jdbc_user where username = '张百万'";
        ResultSet resultSet = statement.executeQuery(sql);

        // 4.处理结果集
        while(resultSet.next()){
            // 通过列名的方式获取
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Date birthday = resultSet.getDate("birthday");

            System.out.println(id + " : " + username + " : " + password + " : " + birthday);
        }

        // 5.释放资源
        JDBCUtils.close(connection, statement, resultSet);
    }
}
