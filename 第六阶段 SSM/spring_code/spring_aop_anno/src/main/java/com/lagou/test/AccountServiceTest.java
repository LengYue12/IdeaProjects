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
 * @date 2022/6/16 20:37
 * @description
 */
// 替换运行器，把当前Junit的运行环境指定为Spring环境
@RunWith(SpringJUnit4ClassRunner.class)
// 加载核心配置类
@ContextConfiguration(classes = SpringConfig.class)   // 指定核心配置文件路径
public class AccountServiceTest {

    // 要调用AccountServiceImpl 中的transfer方法
    // 所以注入该对象
    // 要注入由Spring为我们生成的代理对象，所以要用共同的类型  也就是接口类型接收，不能使用实现类型接收。因为实际注入的是proxy
    @Autowired
    private AccountService accountService;

    @Test
    public void testTransfer(){

        accountService.transfer();
    }

}
