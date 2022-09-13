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
 * @date 2022/6/19 18:37
 * @description
 */
// Spring整合Junit
// 替换运行环境为Spring环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定加载核心配置文件的路径
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountServiceImplTest {

    // 注入AccountService
    @Autowired
    private AccountService accountService;


    // 测试保存
    @Test
    public void testSave(){

        Account account = new Account();
        account.setName("lucy");
        account.setMoney(1000d);
        accountService.save(account);
    }


    // 测试查询所有
    @Test
    public void testFindAll(){

        List<Account> all = accountService.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }


    // 测试根据ID进行查询
    @Test
    public void testFindById(){

        Account account = accountService.findById(3);
        System.out.println(account);
    }


    // 测试账户修改
    @Test
    public void testUpdate(){

        Account account = new Account();
        account.setName("tom");
        account.setMoney(1000d);
        accountService.update(account);
    }


    // 测试根据ID删除账户
    @Test
    public void testDelete(){

        accountService.delete(3);
    }
}
