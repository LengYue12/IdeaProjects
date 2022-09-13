package com.lagou.demo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 使用德鲁伊数据库连接池
 */
public class DruidUtils {

    // 1.定义数据库连接池成员变量
    private static  DataSource dataSource;

    // 2.静态代码块
    static {
        try {
            // 3.创建属性集对象
            Properties p = new Properties();

            // 4.加载配置文件 Druid 连接池不能够主动加载配置文件，需要指定文件
            InputStream resourceAsStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");

            // 5.使用Properties对象的 load方法 从字节流中读取配置信息
            p.load(resourceAsStream);

            // 6.通过工厂类获取连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接池
    public static DataSource getDataSource(){
        return dataSource;
    }

    // 获取数据库连接
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 释放资源
    public static void  close(Connection connection, Statement statement){
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
        if (resultSet != null) {
            try {
                resultSet.close();
                close(connection,statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
