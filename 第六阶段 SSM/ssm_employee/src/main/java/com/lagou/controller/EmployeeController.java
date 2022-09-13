package com.lagou.controller;

import com.lagou.domain.Employee;
import com.lagou.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 22:47
 * @description
 */
/*

 */
@Controller         // 生成实例对象存到IOC
@RequestMapping("/employee")     // 一级目录
public class EmployeeController {


    // controller调用service，service调用dao
    @Autowired  // 注入
    private EmployeeService employeeService;


    /*
        查询所有员工
     */
    @RequestMapping("/findAllWithDept")
    public String findAllWithDept(Model model){

        // 实现查询所有员工信息
        List<Employee> list = employeeService.findAllWithDept();

        // list存到Model中
        model.addAttribute("list",list);

        // 跳转页面
        return "list";

    }


    /*
        新增操作
     */
    @RequestMapping("/save")
    public String save(Employee employee){

        employeeService.save(employee);

        // 跳转到findAllWithDept
        return "redirect:/employee/findAllWithDept";

    }
}
