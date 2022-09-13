package com.lagou.test;

import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zs
 * @date 2022/6/16 18:07
 * @description
 */
// Spring整合Junit
// 替换运行器
@RunWith(SpringJUnit4ClassRunner.class)
// 指定核心配置文件所在的路径
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountServiceImplTest {

    // 注入实例对象
    @Autowired
    private AccountService accountService;

    @Test
    public void testTransfer(){
        accountService.transfer();
    }


}
