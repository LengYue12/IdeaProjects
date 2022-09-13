package com.lagou.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCPUtils {

    // 1.定义常量   保存数据库连接的相关信息
    public static final String DRIVENAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db5?characterEncoding=UTF-8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    // 2.创建连接池对象(由DBCP提供的实现类)
    public static BasicDataSource dataSource = new BasicDataSource();

    // 3.使用静态代码块进行配置
    static {

        dataSource.setDriverClassName(DRIVENAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
    }

    // 4.获取连接的方法
    public static Connection getConnection() throws SQLException {

        // 从连接池中获取连接
        Connection connection = dataSource.getConnection();
        return connection;
    }

    // 5.释放资源方法
    public static void close(Connection connection, Statement statement){

        if (connection != null && statement != null){
            try {
                statement.close();
                // 归还连接
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
