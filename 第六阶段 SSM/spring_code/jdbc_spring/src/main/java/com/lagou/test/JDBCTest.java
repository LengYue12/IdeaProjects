package com.lagou.test;

import org.junit.Test;

import java.sql.*;

/**
 * @author zs
 * @date 2022/6/11 10:40
 * @description
 */


public class JDBCTest {

    @Test
    public void test1() throws SQLException, ClassNotFoundException {

        // 1.注册驱动
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///mybatis_db", "root", "123456");

        // 3.定义SQL
        String sql = "select * from user";

        // 4.获取预编译对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 5.执行SQL，得到结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6.遍历结果集
        while (resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }

        // 7.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
