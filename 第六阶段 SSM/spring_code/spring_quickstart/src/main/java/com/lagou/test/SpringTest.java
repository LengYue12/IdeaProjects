package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author zs
 * @date 2022/6/11 15:40
 * @description
 */
public class SpringTest {

    @Test
    public void test1(){

        // 获取到了spring上下文对象，借助上下文对象可以获取到IOC容器中的bean对象，加载的同时就创建了bean对象存到了容器中
        // 给到spring核心配置文件的全路径，加载。
        // 并且使用dom4j 解析bean标签，会获取到class属性值 实现类的全路径，使用反射生成该类的实例对象，结合id属性值最终存到 IOC容器中

        // 解析配置文件，反射创建对象并放到IOC容器中  已完成
        // ClassPathXmlApplicationContext  从类的根路径下加载配置文件    就是相对类路径         常用
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 从磁盘路径上加载配置文件         绝对路径
        //ApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("F:\\workspace\\idea\\spring_code\\spring_quickstart\\src\\main\\resources\\applicationContext.xml");

        // 可以获取到实例对象了
        // 传递的是 bean标签里的 id属性值。 因为存到IOC容器里的key就是 在配置文件中bean标签 里的id属性值
        // 所以说，想从IOC容器中获取到实例对象，就要根据key 来拿到对应的value值，所以传递的key 要和bean标签里的id属性值保持一致

        // 拿到的就是 UserDaoImpl 的实例对象，所以可以用IUserDao接口来接，强转

        // 使用上下文对象从IOC容器中获取到了 bean对象。其实就是直接从容器中根据userDao 这个key 来取出对应的实例对象

        // 1.根据BeanId 在容器中找到对应的bean对象
        //IUserDao userDao = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        // 2.根据类型在容器中进行查找       传递要获取哪个类型的实例对象
        // 有可能报错的情况：根据当前类型 当前匹配到多个实例        可以再根据beanId来获取
        // 也就是根据类型进行匹配，如果容器中 有多个相同类型的Bean时，就可以既根据beanId 又根据类型匹配  来确定具体是多个实例中的哪个实例   进行返回
        IUserDao userDao = classPathXmlApplicationContext.getBean("userDao",IUserDao.class);

        // 调用方法
        userDao.save();
    }



    @Test
    public void test2(){

        // 核心接口，不会创建bean对象存到容器中
        BeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));

        // getBean的时候才真正创建bean对象，存到容器中
        IUserDao userDao = (IUserDao) xmlBeanFactory.getBean("userDao");

        // 借助实例对象调用方法
        userDao.save();
    }


    /*
        测试scope属性：默认值是 singleton效果
            每次getBean时，都是获取创建一个userDao实例对象，所以这两个实例对象的地址值相同
     */
    @Test
    public void test3(){

        // 也就是当前scope的值是 singleton。那么在加载 核心配置文件时，该类的实例对象被创建，并存到容器中
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        IUserDao userDao = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        IUserDao userDao2 = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        System.out.println(userDao);
        System.out.println(userDao2);

    }



    /*
        测试scope属性：默认值是 prototype效果
            每次getBean时，都是重新创建userDao实例对象，所以这两个实例对象的地址值不同
     */
    @Test
    public void test4(){

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 也就是当前scope的值是 prototype。那么其实 实例化bean的时机 就是在调用getBean方法时。
        // 因为每一次调用getBean时，都是要重新创建该类的实例对象并返回
        // 当getBean时，就表示要使用到bean对象了，所以此时就会创建一个新的该类实例对象
        IUserDao userDao = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        IUserDao userDao2 = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        System.out.println(userDao);
        System.out.println(userDao2);

        classPathXmlApplicationContext.close();

    }





    @Test
    public void test5(){


        // 可以并列加载多个配置文件
        // 还可以采用 主从配置文件加载
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserService userService = (IUserService) classPathXmlApplicationContext.getBean("userService");

        userService.save();
    }
}
