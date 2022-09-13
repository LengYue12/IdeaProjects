package com.lagou.app;

import com.lagou.dao.UserDao;
import com.lagou.entity.User;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import org.junit.Test;

import java.sql.SQLException;

public class TestUserDao {

    UserDao userDao = new UserDao();

    // 测试 注册用户
    @Test
    public void testRegister() throws SQLException {

        // 1.创建User
        User user = new User();
        user.setUid(UUIDUtils.getUUID());
        user.setUsername("武松");
        user.setPassword("123456");
        user.setTelephone("13512341234");
        user.setSex("男");
        user.setBirthday(DateUtils.getDateFormart());

        // 2.执行注册
        int register = userDao.register(user);

        if (register > 0){

            System.out.println("注册成功！欢迎您：" + user.getUsername());
        } else {

            System.out.println("注册失败！");
        }
    }

    // 2.测试 用户登录
    @Test
    public void testLogin() throws SQLException {

        // 1.调用UserDao的Login方法 传入用户名和密码
        User user = userDao.login("武松", "123456");

        // 2.判断user是否为空
        if (user != null){
            System.out.println("欢迎您！" + user.getUsername());
        } else {

            System.out.println("用户名或者密码错误！登录失败！");
        }
    }
}
