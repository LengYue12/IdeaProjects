package com.lagou.test;

import com.lagou.config.SpringConfig;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zs
 * @date 2022/6/19 19:54
 * @description
 */
// 替换运行环境为Spring
@RunWith(SpringJUnit4ClassRunner.class)
// 指定加载核心配置文件路径
//@ContextConfiguration({"classpath:applicationContext.xml"})
// 加载核心配置类
@ContextConfiguration(classes = SpringConfig.class)
public class AccountServiceImplTest {

    // 注入AccountServiceImpl对象
    @Autowired
    private AccountService accountService;


    @Test
    public void testTransfer(){
        accountService.transfer("tom","jerry",100d);
    }
}
