package com.lagou.test;

import com.lagou.config.SpringConfig;
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
 * @date 2022/6/11 23:49
 * @description
 */

/**
 *  基于注解的方式去实现Spring 整合DBUtils
 *
 *
 *          使用Spring整合Junit 需要的注解：
 *              @RunWith：指定Junit的运行环境
 *              @ContextConfiguration：指定要去加载的核心配置文件或核心配置类的所在的路径
 *
 */

// @RunWith 这个注解 是  指定Junit 的运行环境
// SpringJUnit4ClassRunner是Spring提供的作为junit运行环境的一个类
// 当配置了这个注解后，原来Junit的运行器替换成了Spring提供的运行器，表示当前Junit的运行环境是在 Spring的环境下运行的


@RunWith(SpringJUnit4ClassRunner.class)


// 使用 @ContextConfiguration 这个注解 来指定核心配置文件或核心配置类的路径

// 如果是进行xml配置文件 开发，当前工程中 存在 applicationContext.xml  时，就写 classpath:applicationContext.xml
//@ContextConfiguration({"classpath:applicationContext.xml"})

// 若采用的是纯注解开发，就要去指定当前核心配置类所在的路径
// classes =    ：核心配置类所在的路径       {}：可以配置多个核心配置类

// 指定要去加载的核心配置文件或核心配置类
@ContextConfiguration(classes={SpringConfig.class})
public class AccountServiceTest {



    // 在测试类中。首先编写了 Spring API，去加载并解析核心配置文件，构建IOC容器，根据Bean标签的配置 生成类的实例对象存到IOC容器中
    //ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    // 根据上下文对象调用 getBean方法，从容器中根据key 来获取到对应的 value值， 也就是实例对象. 以供下面的测试方法进行调用
    //AccountService accountService = (AccountService) classPathXmlApplicationContext.getBean("accountService");



    // 当前改成了纯注解形式
    // 采用注解形式   就要 new  ApplicationContext 这个接口 的    AnnotationConfigApplicationContext  这个实现类
    // 把 加载核心配置文件改成 加载核心配置类
//    AnnotationConfigApplicationContext annotationConfigApplicationContext =  new AnnotationConfigApplicationContext(SpringConfig.class);
//    AccountService accountService = (AccountService) annotationConfigApplicationContext.getBean("accountService");


    // 会报错，因为没有加载核心配置文件。根本没有创建出IOC容器。注入不了
    // 当前在测试类中，不去编写Spring 的相关API的话。就没办法从容器中获取 实例对象。
    // 可以使用SpringJunit 负责创建Spring容器简化操作，就不用去编写Spring的API了。
    // 需要将配置文件路径告诉SpringJunit，SpringJunit 负责加载核心配置文件，并构建IOC容器，生成实例存到IOC容器中
    // 这样的话 在测试类中  如果想要用到Bean实例，就可以借助  @Autowired 这个注解，进行注入


    // 使用 @Autowired  这个注解   注入需要测试的对象
    @Autowired
    private AccountService accountService;


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
