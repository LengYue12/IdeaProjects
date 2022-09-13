package com.lagou.testbatch;

import com.lagou.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchInsert {

    // 使用批处理 向表中添加 10000条数据
    public static void main(String[] args) throws SQLException {

        // 1.获取连接
        Connection connection = DruidUtils.getConnection();

        // 2.获取预处理对象
        PreparedStatement ps = connection.prepareStatement("insert into testBatch(uname) values(?)");

        // 3.执行批量插入操作
        for (int i = 0; i < 10000; i++) {
            ps.setString(1, "小强" + i);

            // 将SQL添加到批处理列表
            ps.addBatch();
        }

        // 添加时间戳    测试执行效率
        long start = System.currentTimeMillis();


        // 4.统一执行 批量插入操作
        ps.executeBatch();

        long end = System.currentTimeMillis();

        System.out.println("插入10000条数据需要使用：" + (end-start) + "毫秒！");

        // 5.关闭连接
        DruidUtils.close(connection, ps);
    }
}
