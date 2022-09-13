package com.lagou.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3P0Utils {

    // 1.创建连接池对象 C3P0对DataSource接口的实现类
    // 使用的配置 是 配置文件中的默认配置
//    public static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    // 使用指定的配置
    public static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

    // 获取连接的方法
    public static Connection getConnection() throws SQLException {

        Connection connection = dataSource.getConnection();
        return connection;
    }

    // 释放资源
    public static void close(Connection connection, Statement statement) {

        if (connection != null && statement != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet){

        if (resultSet != null){
            try {
                resultSet.close();
                close(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
