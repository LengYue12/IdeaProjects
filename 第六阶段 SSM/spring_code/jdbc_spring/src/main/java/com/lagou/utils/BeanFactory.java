package com.lagou.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zs
 * @date 2022/6/11 12:13
 * @description
 */
public class BeanFactory {


    // 创建iocMap，用来存放通过反射生成的对应类的实例对象
    // 当静态代码块执行完毕后，iocMap中就有记录了
    // key就是userDao，value就是 UserDaoImpl的实例对象

    private static Map<String,Object> iocMap = new HashMap<>();


    // 编写静态代码块，因为静态代码块是随着类的加载而加载的。
    // 只要去调用BeanFactory 这个工具类，静态代码块就会执行，并且只执行一次

    // 程序启动时，初始化对象实例

    // 当静态代码块执行完毕后，iocMap里就存放了
    // key为配置文件里的id属性值，value为 根据配置文件里的class属性值 通过反射创建出来的实例对象

    static {

        // 1.读取配置文件     借助类加载器的getResourceAsStream 方法去加载beans.xml 配置文件，加载成字节输入流并存到内存中
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");

        // 2.解析当前加载的 xml（dom4j）
        SAXReader reader = new SAXReader();
        try {

            // 获取到读取的 beans.xml 的文档对象，  将字节输入流读取成一个document文档对象
            Document document = reader.read(resourceAsStream);

            // 3.编写xpath表达式
                // 获取beans.xml 中的bean标签
                String xpath = "//bean";

                // 4.根据xpath路径，获取到所有的bean标签
                // list集合里的 Element就是一个个的  bean标签
            List<Element> list = document.selectNodes(xpath);

            // 5.遍历list集合，并使用反射创建对象实例，存到map集合（ioc容器）中

            // 在遍历所有的bean标签过程中，读取到了bean标签里面的id属性值和class属性值
            // 借助反射生成对应的实例对象并存到 map集合里，key就是对应的id属性值，value就是 对应生成的某个类的实例对象

            for (Element element : list) {
                // 获取bean标签中的id属性值
                String id = element.attributeValue("id");

                // 获取bean标签中的class属性值
                // className：com.lagou.dao.impl.UserDaoImpl         类全路径
                String className = element.attributeValue("class");

                // 使用反射生成实例对象
                // className就是要加载的类全路径
                // 生成对应类的实例对象
                Object object = Class.forName(className).newInstance();

                // 需要存到map中 key：id  value：object格式的，
                // key存 bean标签中的id值，value存 通过反射生成的实例对象

                iocMap.put(id,object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // 定义一个方法。
    // 根据beanId，去iocMap 集合中 获取对应的 类实例对象 并返回
    public static Object getBean(String beanId){

        // 根据传递进来的key（beanId） 来获取 对应的value值（类实例对象）

        // 在iocMap 容器中 根据key 来获取 到一个实例对象并返回

        // 从map中 根据beanId，也就是userDao 来获取到 实例对象，其实就是 存在map中的 UserDaoImpl的实例对象，进行返回
        return iocMap.get(beanId);

    }
}
