package com.lagou.demo.dao;

import com.lagou.demo.entity.Student;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.lagou.demo.utils.DruidUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 使用DBUtils实现学生信息管理系统数据的CRUD
 * M层（DAO层，对数据库的操作）
 */
public class StudentDaoImp implements StudentDao {

    /**
     * 添加学员信息
     * @param student
     * @return
     */
    @Override
    public int insert(Student student) {

        // 1.创建 QueryRunner对象 自动模式，传入数据库连接池
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写占位符方式SQL
        String sql = "insert into student values(null,?,?,?,?,?,?)";

        // 3.设置占位符的参数
        Object[] param = {student.getName(),student.getSex(),student.getDate(),student.getEmail(),student.getRemarks(),student.getClassGradeId()};

        // 4.执行 update 方法并返回
        try {
            int i = qr.update(sql, param);
            return i;   // 添加学员成功，返回1
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;   // 添加学员失败，返回0
        }
    }

    /**
     * 根据学员id，修改学员信息
     * @param student
     * @return
     */
    @Override
    public int update(Student student) {

        // 1.创建QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "update student set name = ?, sex = ?, date = ?, email = ?, remarks = ?,classGradeId = ? where id = ?";

        // 3.设置占位符参数
        Object[] param = {student.getName(),student.getSex(),student.getDate(),student.getEmail(),student.getRemarks(),student.getClassGradeId(),student.getId()};

        // 4.执行update方法并返回
        try {
            int i = qr.update(sql, param);
            return i;   // 修改成功，返回1
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;   // 修改失败，返回0
        }
    }

    /**
     * 根据参数id，删除学员信息
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "delete from student where id = ?";

        try {
            int i = qr.update(sql, id);
            return i;   // 删除成功，返回1
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;   // 删除失败，返回0
        }
    }

    /**
     * 根据参数学号id，查询学员信息并放入JavaBean中
     * @param id
     * @return
     */
    @Override
    public Student findById(int id) {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "select * from student where id = ?";

        try {
            Student st = qr.query(sql, new BeanHandler<Student>(Student.class), id);
            return st;  // 查询成功，返回student对象
        } catch (SQLException e) {
            e.printStackTrace();
            return null;    // 查询失败，返回null
        }
    }

    /**
     * 学员信息显示，查询到的数据封装到JavaBean中再封装到List集合中
     * @return
     */
    @Override
    public List<Student> selectAll() {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "select * from student";

        try {
            List<Student> list = qr.query(sql, new BeanListHandler<Student>(Student.class));

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 自定义成员方法实现根据指定的班级编号来查找该班级的学生信息
     * @param classId
     * @return
     */
    @Override
    public Student studentFindByClassId(int classId) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from student where classGradeId = ? ";
        try {
            Student query = qr.query(sql, new BeanHandler<Student>(Student.class), classId);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
