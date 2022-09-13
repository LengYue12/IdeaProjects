package com.lagou.test;

import com.lagou.proxy.CglibProxyFactory;
import com.lagou.proxy.JDKProxyFactory;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zs
 * @date 2022/6/15 18:48
 * @description
 */
// 使用注解Spring整合Junit
// 指定运行环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})     // 指定加载的核心配置文件路径
public class AccountServiceTest {

    // 注入
    @Autowired
    private AccountService accountService;

    // 注入JDK动态代理工厂类实例对象
    @Autowired
    private JDKProxyFactory proxyFactory;

    // 注入cglib动态代理工厂类实例对象
    @Autowired
    private CglibProxyFactory cglibProxyFactory;

    @Test
    public void testTransfer(){

        accountService.transfer("tom","jerry",100d);
    }



    /*
        测试JDK动态代理优化转账案列
     */
    @Test
    public void testTransferProxyJDK(){

        // 当前返回的实际上是AccountService的代理对象         实际类型proxy
        AccountService accountServiceJDKProxy = proxyFactory.createAccountServiceJDKProxy();
        // 代理对象proxy调用接口中的任意方法时，都会执行底层的invoke方法
        // 就是代理对象中的匿名内部类中的invoke方法，通过反射去执行被代理类 AccountServiceImpl中的方法
        //accountServiceJDKProxy.transfer("tom","jerry",100d);
        accountServiceJDKProxy.save();
    }


    /*
        测试Cglib动态代理优化转账案列
     */
    @Test
    public void testTransferProxyCglib(){

        // accountServiceCglibProxy：类型就是proxy 代理对象
        AccountService accountServiceCglibProxy = cglibProxyFactory.createAccountServiceCglibProxy();

        // 代理对象调用方法时，会走到CglibProxyFactory 这个工厂类 里重写的intercept 这个方法中
        // intercept 这个方法在去调用目标对象 的原方法之前，就开启了事务，执行完原方法后，就提交事务，产生异常回滚事务以及释放资源
        accountServiceCglibProxy.transfer("tom","jerry",100d);

    }
}
