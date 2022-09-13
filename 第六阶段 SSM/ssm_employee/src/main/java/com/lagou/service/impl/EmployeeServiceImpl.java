package com.lagou.service.impl;

import com.lagou.dao.EmployeeDao;
import com.lagou.domain.Employee;
import com.lagou.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 22:15
 * @description
 */
@Service("employeeService") // 生成该类的实例对象存到IOC
@Transactional  // 开启事务控制
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired   // 注入生成的Dao 代理对象
    private EmployeeDao employeeDao;

    /*
       	Spring整合mybatis，调用dao层方法
     */
    @Override
    public List<Employee> findAllWithDept() {

        return employeeDao.findAllWithDept();
    }

    @Override
    public void save(Employee employee) {

        employeeDao.save(employee);
    }
}
