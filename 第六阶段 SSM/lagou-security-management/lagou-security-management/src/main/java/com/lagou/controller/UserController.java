package com.lagou.controller;

import com.lagou.domain.User;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户处理类
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 查询所有用户
     *
     * @return
     */
    @RequestMapping("/findAll")
    // 访问findAll方法时 需要ADMIN权限
    // 基于Method 的权限表达式
    //@PreAuthorize("hasRole('ADMIN')") // 代表在调用这个方法之前进行权限的验证
    public String findAll(Model model) {
        List<User> userList = userService.list();
        model.addAttribute("userList", userList);
        return "user_list";
    }

    /**
     * 查询所有用户-返回json数据
     *
     * @return
     */
    @RequestMapping("/findAllTOJson")
    @ResponseBody

    // PreFilter和PostFilter 当我们设置条件表达式时并不会跳出权限不足的信息，而是针对参数为集合或返回的为集合这些数据进行 过滤
    //@PostFilter("filterObject.id%2==0") // 剔除所有基数的用户信息
    public List<User> findAllTOJson() {
        List<User> userList = userService.list();
        return userList;
    }

    /**
     * 用户修改页面跳转
     *
     * @return
     */
    @RequestMapping("/update/{id}")
    //@PreAuthorize("#id<3")  // 针对方法参数的权限控制     只有id<3的才能访问
    public String update(@PathVariable Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user_update";
    }

    /**
     * 用户添加或修改
     *
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(User user) {
        userService.saveOrUpdate(user);
        return "redirect:/user/findAll";
    }

    /**
     * 用户添加页面跳转
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        return "user_add";
    }

    /**
     * 用户删除
     *
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.removeById(id);
        return "redirect:/user/findAll";
    }

    /**
     * 用户删除-多选删除
     *
     * @return
     */
    @GetMapping("/delByIds")
    // 过滤掉集合参数中的值   filterObject 代表集合中每个元素
    //@PreFilter(filterTarget = "ids",value = "filterObject%2==0")    // 剔除参数为基数的值
    public String delByIds(@RequestParam(value = "id") List<Integer> ids) {
        for (Integer id : ids) {
            System.out.println(id);
        }
        return "redirect:/user/findAll";
    }


    /**
     * 根据用户ID查询用户
     *
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    // 只能自己查询自己的信息
    //@PostAuthorize("returnObject.username==authentication.principal.username")  // returnObject返回参数
    public User getById(@PathVariable Integer id) {
        // 获取认证的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 如果返回true，代表这个登录认证的信息来源于自动登录
        if(RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            throw new RememberMeAuthenticationException("认证来源于RememberMe");
        }
        User user = userService.getById(id);
        return user;
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/loginUser")
    @ResponseBody
    public UserDetails getCurrentUser(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    /**
     * 获取当前登录用户
     */
    @GetMapping("/loginUser1")
    @ResponseBody
    public UserDetails getCurrentUser1(Authentication authentication){
       return (UserDetails)authentication.getPrincipal();
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/loginUser2")
    @ResponseBody
    public UserDetails getCurrentUser2(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
