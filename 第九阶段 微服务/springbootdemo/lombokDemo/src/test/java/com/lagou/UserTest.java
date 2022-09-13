package com.lagou;

import com.lagou.bean.User;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 20:43
 * @Description
 */
public class UserTest {
    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setName("蔡徐坤");
        System.out.println(user);
    }
}
