package com.lagou.jdbc04;

import com.lagou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBCTransaction {

    // 使用JDBC操作事务
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            // 1.获取连接
            connection = JDBCUtils.getConnection();
            // 2.开启事务
            connection.setAutoCommit(false); // 手动提交事务
            // 3.获取预处理对象 执行SQL(两次修改操作)
            // 3.1tom账户-500
            ps = connection.prepareStatement("update account set money = money - ? where name = ?");
            ps.setDouble(1, 500);
            ps.setString(2, "tom");
            ps.executeUpdate();

            // 模拟 tom转账之后出现异常
            System.out.println(1 / 0);

            // 3.2 jack账户 + 500
            ps = connection.prepareStatement("update account set money = money + ? where name = ?");
            ps.setDouble(1, 500);
            ps.setString(2, "jack");
            ps.executeUpdate();

            // 4.提交事务(正常情况)
            connection.commit();
            System.out.println("转账成功！");

        } catch (SQLException e) {
            e.printStackTrace();
            // 5.出现异常就回滚事务
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // 6.释放资源
            JDBCUtils.close(connection, ps);
        }
    }
}
