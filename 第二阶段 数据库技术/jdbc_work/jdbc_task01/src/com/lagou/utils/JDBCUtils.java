package com.lagou.utils;

import java.sql.*;

/*
*   JDBC 工具类
* */
public class JDBCUtils {

    // 1.将连接信息定义为字符串常量
    public static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db4?characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    // 2.编写静态代码块
    static {
        try {
            // 1.注册驱动
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 3.获取连接的静态方法
    public static Connection getConnection(){
        // 获取连接对象并返回
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 4.关闭资源的方法
    public static void close(Connection connection, Statement statement){
        if (connection != null && statement != null){
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet){
        if (connection != null && statement != null){
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
