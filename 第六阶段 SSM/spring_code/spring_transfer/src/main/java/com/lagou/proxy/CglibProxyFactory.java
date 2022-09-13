package com.lagou.proxy;

/**
 * @author zs
 * @date 2022/6/15 22:16
 * @description
 */

import com.lagou.service.AccountService;
import com.lagou.advice.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 该类就是采用cglib动态代理来对目标类（AccountServiceImpl）进行方法（transfer）的动态增强（添加上事务控制）
 * cglib动态代理工厂类           基于父类的动态代理
 *      采用cglib动态代理方式对转账案列进行优化，实现事务代码和业务代码的解耦合
 */
// 生成该类的实例存到容器中
@Component
public class CglibProxyFactory {

    // 注入AccountService 实例对象
    @Autowired
    private AccountService accountService;

    // 注入事务管理器对象
    @Autowired
    private TransactionManager transactionManager;

    // AccountService来接收返回的代理对象，因为父类实现了这个接口
    public AccountService createAccountServiceCglibProxy(){

        // 编写cglib对应的API来生成对应的代理对象进行返回
        // Enhancer 就是cglib的字节码增强器

        // 参数1：目标类（accountService）的字节码对象
        // 参数2：动作类，当代理对象调用目标对象中原方法时，那么会执行 MethodInterceptor这个类中的intercept方法
        AccountService accountServiceProxy = (AccountService) Enhancer.create(accountService.getClass(), new MethodInterceptor() {

            // o：代表生成的代理对象      method：调用目标方法的引用        objects：方法入参    methodProxy：代理方法
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                // 就是对目标对象的 原方法进行拦截，拦截前和拦截后动态图进行方法的增强

                // 不修改源码的基础上，进行方法增强。实现事务控制代码和业务代码解耦合
                // 就要在method.invoke 之前和之后添加事务相关的代码

                try {
                    // 手动开启事务：调用事务管理器类中的开启事务的方法
                    transactionManager.beginTransaction();


                    // 进行目标对象的原方法调用         就是AccountServiceImpl里面的transfer方法
                    method.invoke(accountService, objects);

                    // 手动提交事务，能够保证转入操作和转出操作处在同一个事务中
                    transactionManager.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 如果出现了异常，要进行手动回滚事务
                    transactionManager.rollback();
                } finally {
                    // 手动释放资源
                    transactionManager.release();
                }

                return null;
            }
        });

        return accountServiceProxy;
    }
}
