package com.lagou.demo01.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {
    private static String jdbcName; // 用于描述驱动信息
    private static String dbUrl;    // 用于描述URL信息
    private static String dbUserName;   // 用于描述用户名信息
    private static String dbPassword;   // 用于描述密码信息

    // 进行静态成员的初始化操作
    static {
        jdbcName = "com.mysql.jdbc.Driver";
        dbUrl = "jdbc:mysql://localhost:3306/db_web";
        dbUserName = "root";
        dbPassword = "123456";
        try {
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
    }

    /**
     * 关闭资源
     * @param con
     * @throws SQLException
     */
    public static void close(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }
    public static void close(Connection con, PreparedStatement ps){
        if (con != null && ps != null) {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
