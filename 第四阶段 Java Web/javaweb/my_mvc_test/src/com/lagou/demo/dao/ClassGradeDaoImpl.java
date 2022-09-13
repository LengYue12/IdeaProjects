package com.lagou.demo.dao;

import com.lagou.demo.entity.ClassGrade;
import com.lagou.demo.entity.PageBean;
import com.lagou.demo.utils.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 使用DBUtils实现班级信息管理系统数据的CRUD
 */
public class ClassGradeDaoImpl implements ClassGradeDao {
    /**
     * 自定义成员方法实现显示班级信息的功能
     * @param pageBean
     * @return
     */
    @Override
    public List<ClassGrade> classGradePageQuery(PageBean pageBean) {
        // 1.创建QueryRunner对象 自动模式，传入数据库连接池
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2.使用StringBuilder类型的对象来描述SQL语句的目的在于后续可以发生改变
        StringBuilder stringBuilder = new StringBuilder("select * from classgrade");
        // 3.当输入分页的需求时进行分页查询，于是拼接SQL语句
        if (pageBean != null) {
            stringBuilder.append(" limit " + pageBean.getStart() + "," + pageBean.getRow());
        }
        // 4.执行query方法并返回
        List<ClassGrade> query = null;
        try {
            query = qr.query(stringBuilder.toString(), new BeanListHandler<ClassGrade>(ClassGrade.class));
            return query;   // 返回
        } catch (SQLException e) {
            e.printStackTrace();
            return null;    // 若显示失败，返回null
        }
    }

    /**
     * 自定义成员方法实现创建班级功能
     * @param classGrade
     * @return
     */
    @Override
    public int classGradeAdd(ClassGrade classGrade) {
        // 1.创建 QueryRunner对象 自动模式，传入数据库连接池
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2.编写占位符方式SQL
        String sql = "insert into classgrade values(null,?,?,?,?,?)";
        // 3.设置占位符的参数
        Object[] param = {classGrade.getClassName(),classGrade.getGradeName(),classGrade.getHeadTeacherName(),classGrade.getClassSlogan(),classGrade.getClassNumber()};
        // 4.执行 update 方法并返回
        try {
            return qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;   // 插入失败，返回0
        }
    }

    /**
     * 自定义成员方法实现修改班级信息功能
     * @param classGrade
     * @return
     */
    @Override
    public int classGradeUpdate(ClassGrade classGrade) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "update classgrade set className = ?, gradeName = ?, headTeacherName = ?, classSlogan = ?, classNumber = ? where classId = ?";
        Object[] param = {classGrade.getClassName(),classGrade.getGradeName(),classGrade.getHeadTeacherName(),classGrade.getClassSlogan(),classGrade.getClassNumber(),classGrade.getClassId()};
        try {
            return qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;    // 修改失败返回0
        }
    }

    /**
     * 根据参数id，删除班级信息
     * @param classIds
     * @return
     */
    @Override
    public int classGradeDelete(String[] classIds) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.准备插入数据的SQL语句
            // 2.1 拼接一下所有的id
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < classIds.length; i++) {
                if (i == classIds.length - 1) {
                    sb.append(classIds[i]);
                } else {
                    sb.append(classIds[i]).append(",");
                }
            }
            // System.out.println("sb = " + sb);
            // 2.2 拼接sql语句
            String sql = "delete from classgrade where ClassId in(" + sb + ")";
            // 3.获取PreparedStatement并执行SQL语句
            preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(connection, preparedStatement);
        }
        return 0; // 表示删除失败
    }

    /**
     * 根据班级编号进行查询
     * @param classId
     * @return
     */
    @Override
    public ClassGrade classGradeQuery(int classId) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from classgrade where classId = ?";
        try {
            return qr.query(sql, new BeanHandler<ClassGrade>(ClassGrade.class), classId);   // 表示查询成功，返回classgrade对象
        } catch (SQLException e) {
            e.printStackTrace();
            return null;    // 查询失败，返回null
        }
    }
}
