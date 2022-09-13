package com.lagou.controller;

import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/25 16:00
 * @description
 */
@Controller     // 生成该类实例对象存到IOC容器中
@RequestMapping("/account")   // 一级目录
public class AccountController {


    // controller调用service，service调用dao
    @Autowired      // 注入
    private AccountService accountService;

    /*
        查询所有用户
     */
    @RequestMapping("/findAll")
    public String findAll(Model model){

        // 实现查询所有账户信息
        List<Account> list = accountService.findAll();

        // 把封装好的list存到Model中
        model.addAttribute("list",list);

        // 跳转页面
        return "list";
    }


    @RequestMapping("/save")
    // 接收前台的请求参数，直接封装成Account对象
    public String save(Account account){

        // 调用service层的save方法，完成对账户的保存
        accountService.save(account);
        // 跳转到findAll方法重新查询一次数据库进行数据的遍历展示
        // 跳转account这个controller类里面findAll方法完成重新查询所有账户，完成重定向
        return "redirect:/account/findAll";
    }


    /*
        根据ID查询账户信息，完成账户回显
     */
    @RequestMapping("/findById")
    public String findById(Integer id,Model model){

       Account account = accountService.findById(id);

       // 存到model中，进行数据回显
        model.addAttribute("account",account);

        // 视图跳转
        return "update";
    }




    /*
        更新账户
     */
    @RequestMapping("/update")
    public String update(Account account){

        accountService.update(account);

        // 跳转到findAll方法进行账户信息的更新查询
        return "redirect:/account/findAll";
    }


    /*
        批量删除
     */

    @RequestMapping("/deleteBatch")
    public String deleteBatch(Integer[] ids){

        accountService.deleteBatch(ids);

        // 重新发起请求再去查询findAll方法，重新查询数据库的记录，进行展示
        return "redirect:/account/findAll";
    }
}
