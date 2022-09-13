package com.lagou.demo01.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
*   工具类
*
* */
public class DBUtils {

    // 1.定义成员变量
    private static DataSource dataSource;

    // 2.静态代码块
    static {

        try {
            // 3.创建属性集对象
            Properties p = new Properties();

            // 4.加载配置文件
            InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");

            // 5.使用Properties对象的load方法 从字节流中读取配置信息
            p.load(inputStream);

            // 6.通过工厂类获取连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3.获取连接
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 4.释放资源
    public static void close(Connection connection, Statement statement){
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
                close(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
