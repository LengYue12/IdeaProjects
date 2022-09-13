package com.lagou.service;

import com.lagou.domain.Employee;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 22:09
 * @description
 */
/*
    service层接口
 */
public interface EmployeeService {


    // 查询员工信息   一对多嵌套查询，查询所有员工，并查询出每个员工所属的部门信息
    List<Employee> findAllWithDept();

    // 新增员工
    void save(Employee employee);
}
