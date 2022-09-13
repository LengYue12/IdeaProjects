package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TestPS {

    public static void main(String[] args) throws SQLException {

        Connection connection = JDBCUtils.getConnection();

        // 获取Statement
        Statement statement = connection.createStatement();

        // 向数据库插入两条数据
        statement.executeUpdate("insert into jdbc_user values (null, '张三', '123456', '2000/12/26');");
        statement.executeUpdate("insert into jdbc_user values (null, '李四', '654321', '1900/12/26');");

        // 获取预处理对象
        PreparedStatement ps = connection.prepareStatement("insert into jdbc_user values (?,?,?,?);");

        // 先插入第一条数据
        ps.setObject(1, null);
        ps.setString(2, "小斌");
        ps.setString(3, "qwer");
        ps.setString(4, "1999/11/11");

        // 执行插入
        ps.executeUpdate();

        // 先插入第二条数据
        ps.setObject(1, null);
        ps.setString(2, "长海");
        ps.setString(3, "asdf");
        ps.setString(4, "2000/11/11");

        // 执行插入
        ps.executeUpdate();

        // 释放资源
        statement.close();
        ps.close();
        connection.close();

    }
}
