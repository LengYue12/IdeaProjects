package com.lagou.testpool;

import com.lagou.utils.DruidUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDruid {

    // 需求 查询 薪资在3000到 5000 之间的员工的姓名
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = DruidUtils.getConnection();

        // 2.获取Statement对象
        Statement statement = connection.createStatement();

        // 3.执行查询
        ResultSet resultSet = statement.executeQuery("select ename from employee where salary between 3000 and 5000");

        // 4.处理结果集
        while (resultSet.next()){
            String ename = resultSet.getString("ename");
            System.out.println(ename);
        }

        // 5.释放资源
        DruidUtils.close(connection, statement, resultSet);

    }
}
