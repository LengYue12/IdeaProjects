package com.lagou.controller;

import com.lagou.pojo.User;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 22:16
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * RestFul风格进行访问
     *  查询：GET
     *  新增：POST
     *  修改：PUT
     *  删除：DELETE
     */

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/query")
    public List<User> queryAll(){
        return userService.queryAll();
    }


    /**
     * 通过ID查询
     */
    @GetMapping("/query/{id}")
    public User queryById(@PathVariable Integer id){
        return userService.findById(id);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "删除成功";
    }


    /**
     * 新增
     * @param user
     * @return
     */
    @PostMapping("/insert")
    public String insert(User user){
        userService.insert(user);
        return "新增成功";
    }


    /**
     * 修改
     * @param user
     * @return
     */
    @PutMapping("/update")
    public String update(User user){
        userService.update(user);
        return "修改成功";
    }
}
