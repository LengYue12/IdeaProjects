package com.lagou.test;

import com.lagou.service.IUserService;
import com.lagou.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author zs
 * @date 2022/6/11 11:48
 * @description
 */
public class SpringTest {


    @Test
    public void test1() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        // 获取到业务层对象

        IUserService userService = new UserServiceImpl();

        // 调用save方法
        userService.save();
    }

}
