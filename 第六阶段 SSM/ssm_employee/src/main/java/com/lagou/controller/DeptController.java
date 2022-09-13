package com.lagou.controller;

import com.lagou.domain.Dept;
import com.lagou.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/27 11:18
 * @description
 */
@Controller
@RequestMapping("/dept")    // 一级目录
public class DeptController {


    @Autowired
    private DeptService deptService;


    /*
        查询部门
     */
    @RequestMapping("/find")
    @ResponseBody
    public List<Dept> find(){

        return deptService.find();
    }
}
