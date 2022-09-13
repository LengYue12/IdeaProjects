package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestLogin01 {

    /**
     * 用户登录案列
     * @param args
     */
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = JDBCUtils.getConnection();

        // 2.获取Statement对象
        Statement statement = connection.createStatement();

        // 3.获取用户输入的用户名和密码
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = sc.nextLine();

        System.out.println("请输入密码：");
        String pass = sc.nextLine();

        // 4.拼接SQL语句
        String sql = "select * from jdbc_user where username = '" + name + "' and password = '" + pass + "'";
        System.out.println(sql);

        // 5.执行查询 获取结果集对象
        ResultSet resultSet = statement.executeQuery(sql);

        // 6.处理结果集
        if (resultSet.next()){
            System.out.println("登录成功！欢迎您：" + name);
        } else {
            System.out.println("登录失败！");
        }

        // 7.关闭流
        JDBCUtils.close(connection,statement,resultSet);
    }
}
