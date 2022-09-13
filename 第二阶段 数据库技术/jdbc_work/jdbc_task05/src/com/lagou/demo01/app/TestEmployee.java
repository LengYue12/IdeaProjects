package com.lagou.demo01.app;

import com.lagou.demo01.utils.DBUtils;


import java.sql.*;

/*
*   测试类 根据name和gender字段进行查询
* */
public class TestEmployee {

    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = DBUtils.getConnection();

        // 2.获取PrepareStatement对象
        PreparedStatement ps = connection.prepareStatement("select * from employee where name = ? and gender = ?");
        PreparedStatement ps1 = connection.prepareStatement("select * from employee where name = ? and gender = ?");

        // 3.设置参数
        ps.setString(1, "张飞");
        ps.setString(2, "男");

        ps1.setString(1, "孙尚香");
        ps1.setString(2, "女");

        // 4.获取结果集
        ResultSet resultSet = ps.executeQuery();
        ResultSet resultSet1 = ps1.executeQuery();


        // 5.处理结果集
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String gender = resultSet.getString("gender");
            double salary = resultSet.getDouble("salary");
            double bonus = resultSet.getDouble("bonus");
            Date join_date = resultSet.getDate("join_date");
            System.out.println(id + " " + name + " " + gender + " " + salary + " " + bonus + " " + join_date + " ");
        }

        while (resultSet1.next()){
            int id = resultSet1.getInt("id");
            String name = resultSet1.getString("name");
            String gender = resultSet1.getString("gender");
            double salary = resultSet1.getDouble("salary");
            double bonus = resultSet1.getDouble("bonus");
            Date join_date = resultSet1.getDate("join_date");
            System.out.println(id + " " + name + " " + gender + " " + salary + " " + bonus + " " + join_date + " ");
        }

        // 6.释放资源
        DBUtils.close(connection, ps, resultSet);
        DBUtils.close(connection, ps, resultSet1);
    }
}
