package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

public class TestLogin02 {

    /*
    *  SQL 注入
    *   用户输入的用户名和密码 与我们编写的SQL进行了拼接，用户输入的内容成为了SQL语法的一部分，
    *   用户会利用这个漏洞   输入一些其他的字符串，改变SQL原有的含义
    *
    *如何解决
    *   要解决SQL注入    就不能让用户输入的数据和我们的SQL进行直接的拼接
    *
    *预处理对象  PrepareStatement 它是Statement接口的子接口
    *   使用预处理对象 他有预编译的功能，提高SQL的执行效率
    *   使用预处理对象 通过占位符的方式 设置参数 可以有效的防止SQL注入
    *
    *
    *
    * */
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = JDBCUtils.getConnection();

        // 2.获取PrepareStatement 预处理对象
        // 使用?占位符的方式来设置参数
        String sql = "select * from jdbc_user where username = ? and password = ?";
        PreparedStatement ps = connection.prepareStatement(sql);


        // 3.获取用户输入的用户名和密码
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = sc.nextLine();

        System.out.println("请输入密码：");
        String pass = sc.nextLine();

        // 4.设置参数   使用setXxx(占位符的位置(整数)，要设置的值)的方法设置占位符的参数
        ps.setString(1, name);  // 设置第一个问号值 为name
        ps.setString(2, pass);

        // 5.执行查询
        ResultSet resultSet = ps.executeQuery();

        // 6.处理结果集
        if (resultSet.next()){
            System.out.println("登录成功！欢迎您：" + name);
        } else {
            System.out.println("登录失败！");
        }

        // 7.关闭流
        JDBCUtils.close(connection,ps,resultSet);

    }
}
