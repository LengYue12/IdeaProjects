package com.lagou.dao.impl;

import com.lagou.dao.AccountDao;
import com.lagou.advice.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author zs
 * @date 2022/6/15 17:26
 * @description
 *
 *      加载核心配置文件，构建IOC容器之后。就会生成该类的实例对象存到IOC容器中，并且Spring会完成依赖注入。
 *          注入QueryRunner实例对象
 */
@Repository("accountDao")     // 采用注解方式生成该类实例存到IOC容器中，默认key值就是该类的类名小写
public class AccountDaoImpl implements AccountDao {

    // QueryRunner实例对象 由Spring创建，并注入到AccountDaoImpl
    @Autowired
    private QueryRunner qr;

    // 注入连接工具类对象       在update中用到方法
    @Autowired
    private ConnectionUtils connectionUtils;

    /*
        转出操作，根据传递过来的值，修改该用户的金额
            out方法和in方法执行时需要用到同一个Connection
                能够保证out方法和in方法处在同一个事务中的
     */
    @Override
    public void out(String outUser, Double money) {

        String sql = "update account set money = money - ? where name = ?";
        try {
            // 第一次获取connection，调用getThreadConnection，获取到connection，是从数据源中获取到的
            // 获取到的Connection就是在开启事务后绑定到当前线程上的Connection
            // 用的是同一个Connection，所以能够保证当前处在同一个事务中
            qr.update(connectionUtils.getThreadConnection(),sql,money,outUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        转入操作
     */
    @Override
    public void in(String inUser, Double money) {

        String sql = "update account set money = money + ? where name = ?";
        try {
            // 获取connection，第二次调用getThreadConnection方法
            // 在out方法时已经获取到了connection，此时ThreadLocal已经绑定好了连接
            // 拿到的connection就和out方法执行的connection是同一个了
            qr.update(connectionUtils.getThreadConnection(),sql,money,inUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
