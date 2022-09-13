package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zs
 * @date 2022/6/19 19:41
 * @description
 */
// 在测试类中要使用AccountServiceImpl实例对象，从而调用transfer方法
// 所以添加注解生成实例存到IOC容器中
@Service("accountService")
// 添加事务注解 @Transactional      添加在类上表示对于该类里面的所有方法都进行事务控制
@Transactional      // 就会对该类所有的方法都进行事务控制，属性都走默认值
public class AccountServiceImpl implements AccountService {

    // 注入accountDao
    @Autowired
    private AccountDao accountDao;

    // 添加事务注解 @Transactional      添加在方法上就是针对与当前这个方法进行事务控制
    // propagation事务传播行为，isolation事务隔离级别，timeout超时时间，readOnly是否为只读
    //@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,timeout = -1,readOnly = false)
    @Override
    public void transfer(String outUser, String inUser, Double money) {

        // 调用dao的out及in方法
        accountDao.out(outUser,money);

        int i = 1/0;

        accountDao.in(inUser,money);
    }
}
