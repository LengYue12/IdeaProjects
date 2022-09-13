package com.lagou.test;

import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 23:49
 * @description
 */
public class AccountServiceTest {


    // 传入applicationContext.xml  核心配置文件全路径
    // 这样就是在 new classPathXmlApplicationContext 对象时，会去加载 applicationContext.xml
    // 并且根据里面的bean标签，来生成各自的实例对象 并注入到IOC容器内
    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    // 调用getBean方法。获取accountService 实例对象，因为accountService 实例对象去调用了accountDao 实例对象
    // 拿到AccountServiceImpl 实现类对象，用接口接
    AccountService accountService = (AccountService) classPathXmlApplicationContext.getBean("accountService");

    // 测试添加
    @Test
    public void testSave(){



        Account account = new Account();
        account.setName("lucy");
        account.setMoney(8888d);

        accountService.save(account);
    }


    // 测试查询方法
    @Test
    public void testFindById(){

        Account account = accountService.findById(3);

        System.out.println(account);
    }



    // 测试查询所有
    @Test
    public void testFindAll(){

        List<Account> all = accountService.findAll();

        for (Account account : all) {
            System.out.println(account);
        }
        classPathXmlApplicationContext.close();
    }


    // 测试更新
    @Test
    public void testUpdate(){

        Account account = new Account();
        account.setId(3);
        account.setName("jack");
        account.setMoney(2000d);

        accountService.update(account);
    }


    // 测试删除
    @Test
    public void testDelete(){

        accountService.delete(3);
    }
}
