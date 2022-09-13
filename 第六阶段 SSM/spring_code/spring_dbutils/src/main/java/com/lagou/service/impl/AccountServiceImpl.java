package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 22:46
 * @description
 */

// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于当前实例对象 在IOC容器中所对应的key值
@Scope("singleton")         // prototype 表示该实例对象 创建多个
public class AccountServiceImpl implements AccountService {

    // 业务层里的方法要调用Dao层的方法
    // 所以在AccountServiceImpl 要用到 AccountDaoImpl对象
    // 通过配置就可以让spring把容器中的accountDao实例对象注入进来
    // 所以使用Spring DI依赖注入


    // 使用注解完成依赖注入
    @Autowired  // 根据类型去完成依赖注入，若根据类型查找，匹配到多个后，会再根据变量名进行二次匹配。换了变量名就不行了，报错

    @Qualifier("accountDao")   // 表示先根据类型查找，如果匹配到多个再根据名称查找     要结合@Autowired使用，不能单独使用

    //@Resource(name = "accountDao")       根据bean  id进行注入
    private AccountDao aDao;


    @Value("注入普通属性")
    private String username;

    // @Value("${jdbc.driverClassName}"):写jdbc.properties中 所配置的key值，根据key 获取value值进行注入
    @Value("${jdbc.driverClassName}")
    private String driver;

    // 通过set方式来注入accountDao 实例对象
//    public void setAccountDao(AccountDao accountDao) {
//        this.accountDao = accountDao;
//    }

    @PostConstruct
    public void init(){
        System.out.println("初始化方法...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁方...");
    }

    @Override
    public List<Account> findAll() {
        // 借助accountDao对象调用方法

        System.out.println(username);
        System.out.println(driver);
        return aDao.findAll();

    }

    @Override
    public Account findById(Integer id) {

        return aDao.findById(id);
    }

    @Override
    public void save(Account account) {
        aDao.save(account);
    }

    @Override
    public void update(Account account) {
        aDao.update(account);
    }

    @Override
    public void delete(Integer id) {
        aDao.delete(id);
    }
}
