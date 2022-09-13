package com.lagou.module01.dao;

import com.lagou.module01.entity.PageBean;
import com.lagou.module01.entity.Student;
import com.lagou.module01.util.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    /**
     * 自定义成员方法描述显示所有学生的功能
     * @param pageBean
     * @return
     */
    @Override
    public List<Student> studentShow(PageBean pageBean) {
        List<Student> studentList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.使用StringBuilder类型的对象来描述SQL语句的目的在于后续可以发生改变
            StringBuilder stringBuilder = new StringBuilder("select * from t_student");
            // 3.当输入分页的需求时进行分页查询，于是拼接SQL语句
            if (pageBean != null) {
                stringBuilder.append(" limit " + pageBean.getStart() + "," + pageBean.getRow());
            }
            // 4.获取PreparedStatement并执行上述SQL语句
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            resultSet = preparedStatement.executeQuery();
            // 5.将结果集中的数据取出并放到List集合中返回
            studentList = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("sex"), new java.util.Date(resultSet.getDate("birthday").getTime()),
                        resultSet.getString("email"), resultSet.getString("comments"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(connection, preparedStatement, resultSet);
        }
        return studentList;
    }

    /**
     * 自定义成员方法实现增加学生的功能
     * @param student
     * @return
     */
    @Override
    public int studentAdd(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.准备插入数据的SQL语句
            String sql = "insert into t_student values(null,?,?,?,?,?)";
            // 3.获取PreparedStatement并执行SQL语句
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSex());
            preparedStatement.setDate(3, new java.sql.Date(student.getBirthday().getTime()));
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getcomments());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(connection, preparedStatement);
        }
        return 0; // 表示添加失败
    }

    /**
     * 自定义成员方法实现删除学生的功能，按照学号删除
     * @param ids
     * @return
     */
    @Override
    public int studentDelete(String[] ids) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.准备插入数据的SQL语句
            // 2.1 拼接一下所有的id
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length-1) {
                    sb.append(ids[i]);
                } else {
                    sb.append(ids[i]).append(",");
                }
            }
            // System.out.println("sb = " + sb);
            // 2.2 拼接sql语句
            String sql = "delete from t_student where id in(" + sb + ")";
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
     * 自定义成员方法实现修改学生的功能
     * @param student
     * @return
     */
    @Override
    public int studentModify(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.准备插入数据的SQL语句
            String sql = "update t_student set name=?, sex=?, birthday=?, email=?, comments=? where id=?";
            // 3.获取PreparedStatement并执行SQL语句
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSex());
            preparedStatement.setDate(3, new java.sql.Date(student.getBirthday().getTime()));
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getcomments());
            preparedStatement.setInt(6, student.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(connection, preparedStatement);
        }
        return 0; // 表示修改失败
    }

    /**
     * 自定义成员方法实现查找学生的功能，按照学号查找
     * @param id
     * @return
     */
    @Override
    public Student studentFind(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Student student = null;
        try {
            // 1.获取Connection连接对象
            connection = DruidUtils.getConnection();
            // 2.准备插入数据的SQL语句
            String sql = "select * from t_student where id=?";
            // 3.获取PreparedStatement并执行SQL语句
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("sex"), new java.util.Date(resultSet.getDate("birthday").getTime()),
                        resultSet.getString("email"), resultSet.getString("comments"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(connection, preparedStatement, resultSet);
        }
        return student;
    }
}
