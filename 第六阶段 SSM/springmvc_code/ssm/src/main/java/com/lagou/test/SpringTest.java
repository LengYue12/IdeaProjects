package com.lagou.test;

import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/25 15:09
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class) // 替换运行环境为Spring环境
@ContextConfiguration("classpath:applicationContext.xml")         // 加载Spring核心配置文件路径，并整合了mybatis
public class SpringTest {

    @Autowired      // 根据类型完成自动注入
    private AccountService accountService;

    @Test
    public void testSpring(){

        // Spring调用Dao，从而执行对account表的查询所有操作，实现了Spring整合mybatis
        List<Account> all = accountService.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }
}
