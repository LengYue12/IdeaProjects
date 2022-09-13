package com.lagou.module01.test;

import com.lagou.module01.entity.User;
import com.lagou.module01.service.UserService;
import com.lagou.module01.service.UserServiceImpl;

/**
 * 编程实现UserService接口的测试
 */
public class UserServiceTest {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        User admin = userService.userLoginService(new User("admin", "123456"));
        System.out.println("找到的数据为：" + admin); // User{id=1, userName='admin', password='123456'}
    }
}
