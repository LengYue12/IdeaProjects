package com.lagou.jdbc01;

import java.sql.*;

public class JDBCDemo02 {

    public static void main(String[] args) throws Exception {

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db4", "root", "123456");

        // 3.获取语句执行平台对象
        Statement statement = con.createStatement();

        // 4.执行查询操作 使用executeQuery()方法
        String sql = "select * from jdbc_user";

        // resultSet 是结果集对象
        ResultSet resultSet = statement.executeQuery(sql);

        // 处理结果集对象 resultSet
//        boolean next = resultSet.next();        // 判断是否有下一条数据
//        System.out.println(next);


        // 获取id
//        int id = resultSet.getInt("id");    // 通过列名的方式获取
//        System.out.println(id);

//        int anInt = resultSet.getInt(1);
//        System.out.println("通过列号获取id" + anInt);

        // 通过while循环    遍历获取 resultSet中的数据
        while(resultSet.next()){
            // 获取id
            int id = resultSet.getInt("id");

            // 获取姓名
            String username = resultSet.getString("username");

            // 获取密码
            String password = resultSet.getString("password");

            // 获取生日
            Date birthday = resultSet.getDate("birthday");

            System.out.println(id + " : " + username + " : " + password + " : " + birthday);
        }

        // 5.关闭流操作
        resultSet.close();
        statement.close();
        con.close();
    }
}
