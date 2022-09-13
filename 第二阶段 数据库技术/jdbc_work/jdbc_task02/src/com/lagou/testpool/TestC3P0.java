package com.lagou.testpool;

import com.lagou.utils.C3P0Utils;

import java.sql.*;

public class TestC3P0 {

    // 需求 查询姓名为李白的 记录
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = C3P0Utils.getConnection();

        // 2.获取预处理对象
        String sql = "select * from employee where ename = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 3.设置占位符
        preparedStatement.setString(1, "李白");
        ResultSet resultSet = preparedStatement.executeQuery();

        // 4.处理结果集
        while (resultSet.next()){

            int eid = resultSet.getInt("eid");
            String ename = resultSet.getString("ename");
            int age = resultSet.getInt("age");
            String sex = resultSet.getString("sex");
            double salary = resultSet.getDouble("salary");
            Date empdate = resultSet.getDate("empdate");

            System.out.println(eid + " " + ename + " " + age + " " + sex + " " + salary + " " + empdate);
        }

        // 5.释放资源
        C3P0Utils.close(connection, preparedStatement, resultSet);
    }
}
