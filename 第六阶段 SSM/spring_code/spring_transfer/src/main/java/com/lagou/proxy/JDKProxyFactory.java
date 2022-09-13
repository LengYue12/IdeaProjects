package com.lagou.proxy;

/**
 * @author zs
 * @date 2022/6/15 21:21
 * @description
 */

import com.lagou.service.AccountService;
import com.lagou.advice.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 该类就是采用JDK动态代理来对目标类（AccountServiceImpl）进行方法（transfer）的动态增强（添加上事务控制）
 * JDK动态代理工厂类           基于接口的动态代理
 *      采用JDK动态代理方式对转账案列进行优化，实现事务代码和业务代码的解耦合
 */
// 生成该类的实例存到容器中
@Component
public class JDKProxyFactory {

    @Autowired      // 从IOC容器中把AccountServiceImpl实例对象注入进来
    private AccountService accountService;

    // 注入事务管理器对象
    @Autowired
    private TransactionManager transactionManager;



    /*
        采用JDK动态代理技术生成目标类的代理对象
            因为当前方法是产生代理对象的，代理对象和目标对象是同级关系，目标对象代表不了代理对象的。
                所以应该找共同的接口来进行接收，所以返回值是接口类型

                ClassLoader loader：类加载器：借助被代理对象来获取到类加载器
                Class<?>[] interfaces：表示被代理类所需要实现的全部接口
                InvocationHandler h：当生成代理对象调用接口中的任意方法时，那么都会去执行InvocationHandler 中的invoke方法
     */
    public AccountService createAccountServiceJDKProxy(){

        // 生成AccountService接口所对应的代理对象
        AccountService accountServiceProxy = (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override   // proxy：当前的代理对象引用      method：被调用的目标方法的引用      args：被调用的目标方法所用到的参数
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                // 优化思路：对目标方法进行拦截，在目标方法执行前和执行后去编写对于事务控制的一些相关代码
                // 实现事务控制代码和业务代码解耦合
                try {

                    // 借助if标签，只对AccountService中的transfer 方法进行增强
                    // 获取到当前被调用的方法的方法名
                    if (method.getName().equals("transfer")) {
                        System.out.println("进行了前置增强");
                        // 手动开启事务：调用事务管理器类中的开启事务的方法
                        transactionManager.beginTransaction();

                        // 让被代理对象的原方法执行，就是AccountServiceImpl 这个被目标对象里面的transfer方法执行
                        method.invoke(accountService, args);
                        System.out.println("进行了后置增强");

                        // 手动提交事务，能够保证转入操作和转出操作处在同一个事务中
                        transactionManager.commit();
                    } else {

                        method.invoke(accountService,args);
                    }
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
