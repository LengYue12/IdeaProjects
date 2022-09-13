package com.lagou.service.impl;

import com.lagou.dao.IUserDao;
import com.lagou.service.IUserService;
import com.lagou.utils.BeanFactory;

/**
 * @author zs
 * @date 2022/6/11 11:45
 * @description
 */
public class UserServiceImpl implements IUserService {

    // service层调用dao层
    @Override
    public void save() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        // 调用dao层方法     传统方式:存在编译期依赖，是耦合重的体现
        //IUserDao userDao = new UserDaoImpl();

        // 使用反射方式去掉new ，就不存在编译期依赖了，变成运行期依赖
        // 反射       借助反射来获取userDao的实例

        // 使用反射解决编译期依赖问题，但是还存在硬编码问题
        // 可以使用配置文件解决硬编码问题，把类全路径抽取出来，放在一个配置文件中。
        // 当想进行反射需要用到类全路径时，就去解析配置文件拿到存放的类全路径，然后再进行反射获取实例对象



        // 解耦的思路：反射+配置文件
        // IUserDao userDao = (IUserDao) Class.forName("com.lagou.dao.impl.UserDaoImpl").newInstance();

        // 不能用new，不能直接用反射 获取实例对象。因为用new 存在耦合，用反射存在硬编码
        // 所以想获取实例对象，就要从iocMap中 来获取对应的实例对象
        // 因为在工具类中已经生成了实例对象存到了iocMap中
        // 所以想获取实例对象，直接拿到iocMap，从map中根据key 获取到对应的 value值 就ok了

        // 使用工厂类调用getBean 方法，根据beanId 来获取 到对应的value值  其实就是  需要的实例对象

        // 传递beanId，获取userDao实例对象。id值要和beans.xml 中配置的id属性值保持一致，因为是解析的配置文件产生的id值

        // 获取到的实例对象的类型 就是userDaoImpl，用接口接收并强转。



        // 当在UserServiceImpl里的 save方法想获取到一个userDao对象时
        // 执行了BeanFactory.getBean方法，并且传递了beanId
        // 而在调用 BeanFactory.getBean 方法时，先执行BeanFactory 里的静态代码块 进行解析配置文件
        // 并把配置文件里的id 属性值和根据class属性值 获取的类全路径通过反射创建的类实例对象 放到iocMap 容器里
        // 调用getBean 方法， 就会根据传递过来的beanId，也就是iocMap 里的key 去获取到value值， 也就是需要用到的 类实例对象

        IUserDao userDao = (IUserDao) BeanFactory.getBean("userDao");

        userDao.save();
    }
}
