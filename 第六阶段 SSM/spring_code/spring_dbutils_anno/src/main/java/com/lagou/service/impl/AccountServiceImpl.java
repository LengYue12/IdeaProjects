package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/11 22:46
 * @description
 */

// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于当前实例对象 在IOC容器中所对应的key值
public class AccountServiceImpl implements AccountService {

    // 使用注解完成依赖注入
    @Autowired  // 根据类型去完成依赖注入，若根据类型查找，匹配到多个后，会再根据变量名进行二次匹配。换了变量名就不行了，报错
    private AccountDao aDao;


    @Override
    public List<Account> findAll() {
        // 借助accountDao对象调用方法
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
