package com.lagou.testDBUtils;

import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/*
*   使用QueryRunner对象 完成增删改操作
*           update(Connection con, String sql, Object... param) 方法
* */
public class DBUtilsDemo02 {

    @Test
    public void testInsert() throws SQLException {

        // 1.创建 QueryRunner 手动模式创建
        QueryRunner qr = new QueryRunner();

        // 2.编写占位符方式的SQL
        String SQL = "insert into employee values(?,?,?,?,?,?);";

        // 3.设置占位符的参数
        Object [] param = {null, "张百万", 20, "女", 10000, "1990-12-26"};

        // 4.执行update 方法
        Connection connection = DruidUtils.getConnection();
        int update = qr.update(connection, SQL, param);

        // 5.释放资源
        DbUtils.closeQuietly(connection);
    }

    // 修改操作 修改姓名为张百万的员工的工资为15000
    @Test
    public void testUpdate() throws SQLException {

        // 1.创建核心类  自动模式 需要传递数据库连接池对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "update employee set salary = ? where ename = ?";

        // 3.设置占位符的参数
        Object [] param = {15000, "张百万"};

        // 4.执行修改操作 自动模式不需要传入Connection对象
        qr.update(sql, param);

    }

    // 删除操作 删除id为1的记录
    @Test
    public void testDelete() throws SQLException {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "delete from employee where eid = ?";

        // 如果只有一个参数的话   不需要创建数组

        qr.update(sql, 1);
    }
}
