package com.lagou.test;

import com.lagou.dao.EmpDAO;
import com.lagou.entity.Emp;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @BelongsProject mongo-com.lagou.test
 * @Author lengy
 * @CreateTime 2022/8/1 22:28
 * @Description 测试MongoDB
 */
public class TestMongo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        EmpDAO empDao = context.getBean("empDao", EmpDAO.class);

        /*Emp emp = new Emp("2","1",1,"1",1);


        empDao.save(emp);


        System.out.println("添加成功");*/


       /* Emp emp = new Emp("貂蝉","女",19,"美女",7586);
        emp.setId("62e7e7631f66bd0434422a39");
        empDao.update(emp);*/


        /*empDao.delete("62e7e7341f66bd233c7616b8");*/


        /*Emp emp = empDao.findById("62e7e7631f66bd0434422a39");
        System.out.println("emp = " + emp);*/

        List<Emp> emps = empDao.findListPage(1, 5, "张");
        System.out.println("emps = " + emps);
    }
}
