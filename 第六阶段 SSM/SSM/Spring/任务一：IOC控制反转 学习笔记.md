# 任务一：IOC控制反转	学习笔记

# 一、Spring概述		笔试面试

## 1.1 Spring是什么？（重点）

**`Spring`是一个 [全栈式]() [轻量级的]() 开源框架**			（重点）

**有两大核心：IOC和AOP**

---



​	Spring是分层的 Java SE/EE 应用  full-stack**(全栈式)  轻量级 开源框架**。

* **全栈式**：该框架对各种主流技术和框架进行了整合，同时对三层架构对提供了解决方案
  * 每一层都有解决方案，如在Web层使用  SpringMVC
  * service层：使用spring的核心，来完成事务控制，对象管理
  * dao层：spring jdbc Template
* **轻量级**：轻量级和重量级和划分主要依据就是看该框架使用了多少服务，启动时需要加载的资源多少 以及 耦合度等等情况。
  * Spring就是轻量级框架，所消耗的资源比较少



提供了 **表现层 的SpringMVC**，**持久层的 Spring JDBC Template**以及 业务层 的Spring核心 进行事务控制对象管理等等。



（笔试）

**两大核心**（重点）：

* `IOC`：**控制反转：把对象的创建权交给spring**
* `AOP`：**面向切面编程：在不修改源码的情况下，对方法进行增强**
  * AOP底层就是动态代理，就是对动态代理进行了封装





## 1.2 Spring发展历程	（了解）

EJB  是 Spring 的前身。		当前用Spring5



## 1.3 Spring优势		（面试）

从核心角度去记忆：

IOC和AOP 都可以进行程序间的解耦，同时AOP是面向切面编程，可以实现在不修改源码的情况下，对方法进行增强。



* 耦合：程序间的依赖关系
* 解耦：降低程序间的依赖关系
  * 体现：编译期不依赖，运行期才依赖
    * 在代码中就是去掉new 关键字。
    * 解耦思路：配置文件+反射

```markdown
1）方便程序解耦，简化开发
	Spring就是一个容器，可以将所有对象的创建和关系维护交给Spring管理
	什么是耦合度？对象之间的关系，通常说当一个模块（对象）更改时也需要更改其他模块（对象），也就是说这两个对象有依赖关系，当修改其中的一个对象时，那么还需要修改关联的另一个对象。这就是耦合。耦合度过高会使代码的维护成本增加。所以要尽量解耦
	
	耦合：程序间的依赖关系，存在编译期依赖，耦合重的体现
		只要看见new，那么就会存在编译期依赖，耦合重的体现。解耦思路：去掉new关键字， 配置文件+反射
	解耦：降低程序间的依赖关系	  体现：编译期不依赖，运行期才依赖
	
	
2）AOP编程的支持
	Spring提供面向切面编程，实现对程序进行权限拦截，运行监控等功能
	
	
3）声明式事务的支持
	通过配置完成事务的管理，无需在Service层每个方法中手动编写事务代码了。实现了事务代码和业务代码的分隔
	

4）方便测试，降低JavaEE  API的使用
	Spring对Junit4支持，可以使用注解测试
	

5）方便集成各种优秀框架
	不排除各种优秀的开源框架，内部提供了对各种优秀框架的直接支持
```



## 1.4 Spring体系结构

Spring就是一个模块化的，当想使用某部分模块的功能时，引入对应的模块。

核心容器：Core  Container		所有模块的基础



IOC：把对象的创建交给Spring		IOC容器：Spring创建出来的这些对象都存到容器中







# 二、初识IOC

## 2.1 概述

**控制反转**：是一种设计思想。指导我们设计出更加松耦合的程序。

* 控制：在Java中指的就是**对象的控制权限**（创建对象，销毁对象）
* 反转：指的是对象控制权由原来 的 开发者在类中手动控制  反转到  由Spring容器控制
  * **也就是对象的创建交给Spring来完成**

就是把对象的创建权交给Spring



例：

```markdown
* 传统方式
		之前我们需要一个userDao实例，需要开发者 自己手动创建  new UserDao();
			
			看见new，说明存在编译期依赖，耦合重的体现。所以要去掉new 来解耦
* IOC方式
		现在需要一个userDao实例的话，直接从Spring的IOC容器中获取，对象的创建交给Spring控制
		
		在程序启动期间，spring会创建一些类的对象，bean对象 存到IOC容器中
```



## 2.2 自定义IOC容器

### 2.2.1 介绍

需求：

​	实现service层与dao层代码解耦合

传统方式下的Service层和Dao层的调用 步骤：

```markdown
1. 创建java项目，导入自定义IOC相关坐标，依赖
2. 编写Dao接口和实现类
3. 编写Service接口和实现类
4. 编写测试
```



### 2.2.2 实现

1）创建Java项目，导入自定义IOC相关坐标

```xml
<!--        导入dom4j 技术进行xml解析-->
        <dependency>
            <groupId>dom4j</groupId>
            <artifactId>dom4j</artifactId>
            <version>1.6.1</version>
        </dependency>

<!--        用到XPath-->
        <dependency>
            <groupId>jaxen</groupId>
            <artifactId>jaxen</artifactId>
            <version>1.1.6</version>
        </dependency>


<!--        单元测试-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
```



2）编写Dao接口和实现类

```java
public interface IUserDao {

    public void save();
}
```

```java
public class UserDaoImpl implements IUserDao {

    @Override
    public void save() {
        System.out.println("dao被调用啦，保存成功...");
    }
}
```



3）编写Service接口和实现类

```java
public interface UserService {
  public void save();
}
```

```java
public class UserServiceImpl implements UserService {

private UserDao userDao;

    // service层调用dao层
	@Override
  public void save(){
      
      // 调用dao层方法     传统方式:存在编译期依赖，是耦合重的体现
    userDao = new UserDaoImpl();
    userDao.save();
 }
}
```



4）编写测试

```java
![4](C:\Users\zs\Desktop\学习\阶段六\模块二Spring\模块二Spring\spring画图\任务1\4.png)public class SpringTest {


    @Test
    public void test1(){

        // 获取到业务层对象

        IUserService userService = new UserServiceImpl();

        // 调用save方法
        userService.save();
    }

}
```



5）问题

当前Service对象和Dao对象耦合度高，而且每次调用都是new 的新对象，导致服务器压力大

解决：

​	去掉new，使得 **编译期不依赖，运行期依赖就行**

使用反射+配置文件：

* 准备一个配置文件，配置好对应的标签，以及实体类全路径
* 编写一个工厂类，在工厂类中借助dom4j解析配置文件，拿到类全路径。
* 使用反射生成对应类的实例对象，存到map中（IOC容器）



那么在Service层方法里，如果想获取userDao对象的话，不用new了，直接获取map容器，从map容器中把 创建好的userDao实例对象 取出来就ok。

这样就解决了编译期依赖，降低了程序间的耦合，并解决了硬编码



6）编写 beans.xml   配置文件

把所有需要创建对象的信息定义在配置文件中

```xml
<beans>

<!--    id:存放标识  class：存放要生成的实例的类的全路径，实现类的全路径-->
    <bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl"/>

</beans>
```



7）编写 BeanFactory工具类

编写工厂工具类，使用dom4j解析配置文件，得到 配置好的 类全路径。使用反射生成实例对象存到容器中

```java
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
```



8）修改UserServiceImpl实现类

```java
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
```





### 2.2.3 知识小结

```markdown
* 其实升级后的BeanFactory就是一个简单的Spring的IOC容器所具备的功能

* 之前需要一个userDao实例，需要开发者手动 去new UserDao()

* 现在改造完后，需要userDao实例，直接去Spring的IOC容器中获取，不用自己new了，对象的创建权交给Spring控制

* 最终目标：代码解耦合
```

---



Spring的IOC其实 就是借助反射，把对象的创建交给Spring，让Spring借助反射生成类的实例对象，并存在由Spring创建出来的IOC容器中。当我们想用到某个类的 实例，那么直接去IOC容器中获取所需要的实例对象 就ok了





# 三、Spring 快速入门

## 3.1 介绍

需求：借助Spring的IOC实现Service层与dao层代码解耦合



IOC方式下的Service层和Dao层的调用 步骤：

```markdown
1. 创建java项目，导入Spring开发依赖
2. 编写Dao接口和实现类
3. 创建Spring核心配置文件
4. 在核心配置文件中配置 UserDaoImpl
5. 使用Spring相关API获取 Bean实例
```



## 3.2 实现

1）创建java项目，导入Spring依赖

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

    </dependencies>
```



2）编写Dao接口和实现类

```java
public interface IUserDao {	

    public void save();
}
```

```java
public class UserDaoImpl implements UserDao {
 
  public void save() {
    System.out.println("dao被调用了...");
 }
}
```



3）创建Spring核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```



4）在Spring核心配置文件中配置 UserDaoImpl

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
    在spring核心配置文件中 配置UserDaoImpl
        id：唯一标识，不能重复
        class：实现类的全路径

        spring底层就是通过反射根据类全路径生成的实例对象，存到IOC容器中

-->
    <bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
    
</beans>
```



5）使用Spring相关API获得Bean实例

```java
public class UserTest {
  @Test
    public void test1(){

        // 获取到了spring上下文对象，借助上下文对象可以获取到IOC容器中的bean对象，加载的同时就创建了bean对象存到了容器中
        // 给到spring核心配置文件的全路径，加载。
        // 并且使用dom4j 解析bean标签，会获取到class属性值 实现类的全路径，使用反射生成该类的实例对象，结合id属性值最终存到 IOC容器中

        // 解析配置文件，反射创建对象并放到IOC容器中  已完成
        // ClassPathXmlApplicationContext  从类的根路径下加载配置文件    就是相对类路径         常用
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");


        // 可以获取到实例对象了
        // 传递的是 bean标签里的 id属性值。 因为存到IOC容器里的key就是 在配置文件中bean标签 里的id属性值
        // 所以说，想从IOC容器中获取到实例对象，就要根据key 来拿到对应的value值，所以传递的key 要和bean标签里的id属性值保持一致

        // 拿到的就是 UserDaoImpl 的实例对象，所以可以用IUserDao接口来接，强转

        // 使用上下文对象从IOC容器中获取到了 bean对象。其实就是直接从容器中根据userDao 这个key 来取出对应的实例对象

        // 根据BeanId 在容器中找到对应的bean对象
       IUserDao userDao = (IUserDao) classPathXmlApplicationContext.getBean("userDao");

        // 调用方法
        userDao.save();
    }
}
```



## 3.3 知识小结

Spring的IOC实现 userService和userDao 程序的解耦的开发步骤

```markdown
1. 导入坐标
2. 创建Bean
3. 创建applicationContext.xml
4. 在配置文件中进行Bean配置
5. 创建ApplicationContext对象，执行getBean
```





# 四、Spring 相关API

## 4.1 API继承体系介绍

----



Spring的API体系异常庞大，我们现在只关注两个	BeanFactory接口和ApplicationContext接口（Spring上下文对象）



`*笔试*：BeanFactory和ApplicationContext的区别？`

**BeanFactory和ApplicationContext 的Bean对象创建时机不同，**

BeanFactory是**调用getBean方法时创建**，ApplicationContext**是容器启动时，就创建了**

---



## 4.2 BeanFactory

BeanFactory是IOC容器的核心接口，定义了IOC的基本功能

`特点`：在第一次**调用getBean()方法时，创建指定对象的实例**

```java
@Test
    public void test2(){

        // 核心接口，不会创建bean对象存到容器中
        BeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));

        // getBean的时候才真正创建bean对象，存到容器中
        IUserDao userDao = (IUserDao) xmlBeanFactory.getBean("userDao");

        // 借助实例对象调用方法
        userDao.save();
    }
```



## 4.3 ApplicationContext

代表应用上下文对象，可以获得Spring中IOC容器的Bean对象

`特点`：在**Spring容器启动时，加载并创建所有对象的实例**

**常用实现类**

```markdown
1. ClassPathXmlApplicationContext
   	它是从类的根路径下加载配置文件 推荐使用这种。
  
2. FileSystemXmlApplicationContext
   	它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。
  
3. AnnotationConfigApplicationContext
   	当使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。
   
```

```java
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
        // 有可能报错的情况：根据当前类型 匹配到多个实例        可以再根据beanId来获取
        // 也就是根据类型进行匹配，如果容器中 有多个相同类型的Bean时，就可以既根据beanId 又根据类型匹配  来确定具体是多个实例中的哪个实例   进行返回
        IUserDao userDao = classPathXmlApplicationContext.getBean("userDao",IUserDao.class);

        // 调用方法
        userDao.save();
    }
```



**常用方法**

```markdown
1. Object getBean(String name);
		根据Bean的id从容器中获取Bean实例，返回是Object，需要强转
		
2. <T> T getBean(Class<T> requiredType);
		根据类型从容器中匹配Bean实例，当容器中有相同类型的多个Bean时，会报错
		
3. <T> T getBean(String name,Class<T> requiredType);
		根据Bean的id和类型获取Bean实例，解决容器中相同类型Bean有多个情况
```



## 4.4 知识小结

```java
ApplicationContext app = new ClasspathXmlApplicationContext("xml文件");
  app.getBean("id");
app.getBean(Class);
```





# 五、Spring的核心配置文件

## 5.1 Bean标签基本配置

```markdown
<bean id="" class=""></bean>

* 用于配置对象交由Spring来创建。
* 基本属性：
	id：Bean实例在Spring容器中的唯一标识
	class：Bean的全限定名。因为Spring底层是通过反射来创建实例对象的，所以要放全路径名
	
* 默认走无参构造方法去生成实例对象并存到容器中，如果没有无参构造函数则不能创建成功。
```



## 5.2 Bean标签范围配置

```xml
<!--在spring核心配置文件中 配置UserDaoImpl
        id：唯一标识，不能重复
        class：实现类的全路径

        spring底层就是通过反射根据类全路径生成的实例对象，存到IOC容器中

        scope="singleton"：默认值：创建出来的bean是单例的
                prototype：创建出来的bean是多例的，每次从容器中获取都会创建一个新的对象
-->
<!--    默认走无参构造方法去生成实例对象并存到容器中-->
<!--    方式一：无参构造方法进行实例化-->
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl" scope="singleton"></bean>
```

scope属性指对象的作用范围，取值如下：

| 取值范围           | 说明                                                         |
| ------------------ | ------------------------------------------------------------ |
| **singleton**      | 默认值，创建出来的对象是单列的                               |
| **prototype**      | 创建出来的对象是多列的                                       |
| request            | 在创建Bean实例时，实例对象不仅在容器中，还会在request域中    |
| session            | Web项目中，Spring创建一个Bean对象，将对象存到session域中     |
| global<br/>session | WEB项目中，应用在Portlet环境，如果没有Portlet环境那么globalSession 相当<br/>于 session |

```markdown
1. 当scope取值为singleton时，Bean的生命周期就与容器相同
	Bean的实例化个数：1个
	Bean的实例化时机：当Spring核心配置文件被加载时，就会实例化Bean并存到容器中
	Bean的生命周期：
		对象创建：当应用加载，创建容器时，对象就被创建了
		对象运行：只要容器在，对象一直活着
		对象销毁：当应用卸载，销毁容器时，对象就被销毁了
		


2. 当scope取值为prototype时，
	Bean的实例化个数：多个
	Bean的实例化时机：调用getBean()方法时实例化Bean
	Bean的生命周期：
		对象创建：当使用对象时，创建新的对象实例
		对象运行：只要对象在使用中，就一直活着
		对象销毁：当对象长时间不用时，就 会被 Java的垃圾回收器给回收
```



## 5.3 Bean生命周期配置	(了解)

```markdown
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl" init-method="init" destroy-method="destroy"></bean>

* init-method：指定类中的初始化方法名称
	如果想让该Bean对象在实例化完成后要执行方法，就可以把方法名配置到init-method
	
* destroy-method：指定类中销毁方法名称
	当Bean对象在销毁时执行的方法
```



## 5.4 Bean实例化三种方式

* 无参**构造**方法实例化                 **（常用）**
* 工厂**静态**方法实例化                   （有特定的场景时才用）
* 工厂**普通**方法实例化                    （有特定的场景时才用）



### 5.4.1 无参构造方法实例化		（重点）

​	它会根据默认的无参构造方法来创建该类对象，如果bean中没有默认无参构造，将会创建失败

```xml
<!--    方式一：无参构造方法进行实例化-->
    <bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
```



### 5.4.2 工厂静态方法实例化

**应用场景**

​	当依赖的jar包里有个A类，有个静态方法，返回值是一个B对象。如果我们经常用到B对象，此时可以把B对象的创建权交给Spring IOC容器，以后使用B对象时，无需调用A类的方法，直接从IOC容器中获得。因为已经把B对象交给IOC容器管理了

```java
public class StaticFactoryBean {
 
  public static UserDao createUserDao(){  
 return new UserDaoImpl();
 }
}
```

```xml
<!--方式二：工厂静态方法实例化-->
<!--    factory-method属性：就会把 StaticFactoryBean 里面的createUserDao 这个方法进行调用，获取到返回结果。结合id 放到IOC

        factory-method属性：用来指定把该方法的返回值存到容器中
    -->
<bean id="userDao" class="com.lagou.factory.StaticFactoryBean" factory-method="createUserDao"/>
```



### 5.4.3 工厂普通方法实例化

**应用场景**

​	依赖的jar包中有个A类，A类中有个普通方法，返回值是一个B对象。B对象经常使用，所以可以把B对象的创建权交给Spring IOC容器进行管理。以后用到B对象，无需调用A类的方法获取，直接去IOC容器中获得

```java
public class DynamicFactoryBean { 
 
public UserDao createUserDao(){    
return new UserDaoImpl();
}
}
```

```xml
<!--    方式三：工厂普通方法实例化
        先生成工厂类实例存到容器中

            factory-bean：指定当前工厂bean对象是哪个
            factory-method：表示要去调用这个工厂类里面的哪个方法  把对应方法的返回值存到容器中
-->

 <bean id="dynamicFactoryBean" class="com.lagou.factory.DynamicFactoryBean"/>
<bean id="userDao" factory-bean="dynamicFactoryBean" factory-method="createUserDao"/>
```





## 5.5 Bean依赖注入概述		（重点）笔试

**依赖注入   DI**：它就是 Spring框架核心 IOC 的具体实现

​	在编写程序时，通过IOC控制反转，把对象的创建权交给Spring管理，降低程序间的依赖关系，实现解耦。但是不可能出现没有依赖的情况，IOC 解耦只是降低依赖关系，但不会消除。 如：业务层调用持久层方法

​	那这种业务层和持久层的依赖关系，在使用Spring之后，就让Spring来维护了。简单说，**就是通过框架把持久层对象传入业务层**，不用我们自己去获取了

---

**把对象之间的依赖关系交给Spring来维护**





## 5.6 Bean依赖注入方式		让框架把持久层对象自动注入给业务层 

**`Bean依赖注入方式有几种`**：其实就只有两种，通过构造方法进行注入，通过set方法进行注入

---



### 5.6.1 有参构造方法 完成依赖注入

​	在UserServiceImpl中创建有参构造

```java
public class UserServiceImpl implements IUserService {


    // 声明出一个变量，用于接收注入进来的userDao实例对象
    // 就是用来接收注入进来的userDao实例
    private IUserDao userDao;

    // 借助有参构造来给userDao 成员变量属性进行赋值，让spring通过有参构造完成userDao 实例对象的注入
   public UserServiceImpl(IUserDao userDao) {
        
       // 当userDao实例注入进来，成员变量userDao就有值了
      // 把接收到的userDao赋值给 成员变量userDao
       this.userDao = userDao;
    }

    @Override
    public void save() {

        // 在UserServiceImpl 中 已经完成userDao 实例对象的注入。
        // 就可以通过注入进来的userDao 去调用方法
        userDao.save();

    }
}
```

​	配置Spring容器调用有参构造时进行注入

```xml
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl"/>


<!--    配置UserService-->

<!--    这段bean标签的配置，所采用的对象的创建   其实就是采用有参构造的方法进行对象的创建，
            同时借助constructor-arg 这个标签，设置了要去注入的参数的值

            在new  UserServiceImpl对象时，是去注入了一个userDao对象
            IUserService userService = new UserServiceImpl(userDao);

-->

<!--    使用有参完成依赖注入-->

<!--    采用有参构造来创建userServiceImpl 对象-->
    <bean id="userService" class="com.lagou.service.impl.UserServiceImpl">
<!--        constructor-arg 子标签：spring在进行解析配置文件时，看到了这个constructor-arg 子标签。
                就会生成userService 这个实例对象时，要采用有参构造方法来进行创建实例对象
-->

<!--        index="0"：表示要给userServiceImpl 这个类中的有参构造中的第一个参数进行赋值
                type="com.lagou.dao.IUserDao"：来指定当前第一个参数的实际类型
                ref="userDao"：表示引用。要去引用IOC容器中的哪个对象，并把它注入到第一个参数中。要和配置的注入对象 的id值相同

                这样就可以根据标识在容器中找到对应的userDao实例对象，并把它注入给当前userServiceImpl 里面的有参构造中的第一个参数
-->
<!--        <constructor-arg index="0" type="com.lagou.dao.IUserDao" ref="userDao"/>-->

        
        <!--   name="userDao"：name的值就是 userServiceImpl 类中的 有参构造的 参数名
                    表示就要给有参构造中 参数名为userDao 的参数进行值的注入，注入的值就是 userDao这个实例对象

                    ref="userDao"：就和userDao这个实例对象 的id值保持一致
          -->
       <constructor-arg name="userDao" ref="userDao"/>
```





### 5.6.2 Set方法依赖注入				（常用）

​	在UserServiceImpl类中 创建set方法

```java
public class UserServiceImpl implements IUserService {


    // 声明出一个变量，用于接收注入进来的userDao实例对象
    // 就是用来接收注入进来的userDao实例
    private IUserDao userDao;

    // 通过set方法进行注入，userDao实例对象。才能调用方法
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
 
        // 在UserServiceImpl 中 已经完成userDao 实例对象的注入。
        // 就可以通过注入进来的userDao 去调用方法
        userDao.save();

    }
}
```

​	配置Spring容器调用set方法进行注入

```xml
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl"/>
<bean id="userService" class="com.lagou.service.impl.UserServiceImpl">
  
    
    <!--    采用set方法完成依赖注入           常用
            name="userDao"：name的值要写 userServiceImpl这个类 中所生成的set方法后面的首字母小写
            ref="userDao"：配置引用对象。 根据这个标识，在容器中找到对应的实例对象，然后注入给 userServiceImpl这个类 中的userDao属性

            等同于
                userService.setUserDao(userDao);
-->
 <property name="userDao" ref="userDao"/>
</bean>
```





### 5.6.3 P命名空间注入			（不常用）

​	P命名空间注入本质也是set方法注入，但比set方法注入更加方便，主要体现在配置文件中

**首先，需要引入P命名空间：**

```
xmlns:p="http://www.springframework.org/schema/p"
```



**其次，需要修改注入方式**：

```xml
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl"/>

<!--在bean标签里添加 p:userDao-ref="userDao"   P命名空间注入
            ref ：是实例对象的id-->
<bean id="userService" class="com.lagou.service.impl.UserServiceImpl"
   p:userDao-ref="userDao"/>
```





## 5.7 Bean依赖注入的数据类型

​	上面操作，都是注入的Bean对象，除了对象的引用可以注入，普通数据类型和集合都可以在容器中进行注入的

注入数据的三种类型：

1. **普通数据类型**：基本数据类型和String
2. **引用数据类型**：Bean对象
3. **集合数据类型**：List、Map、Set

下面重点是以Set方式注入为例，演示普通数据类型和集合数据类型的注入。



### 5.7.1 注入普通数据类型

```java
public class UserDaoImpl implements UserDao {
    // 两个普通属性，可以通过set方法进行注入普通数据类型
    private String username;
    private Integer age;



    // 采用set方法完成注入普通数据类型
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

```xml
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
    
  <!--        完成依赖注入，向UserDaoImpl 该对象中注入username 和 age 这两个普通属性的值-->

<!--
            采用set方式完成普通属性值的注入

 name：要和当前类中的 set方法后面的首字母小写 保持一致
                ref：用于引用数据类型的注入
                value：用户普通数据类型的注入

            所以要根据当前注入的类型来选择使用value还是ref

              要注入的username是String类型，所以是普通数据类型，可以用value

              -->
        <property name="username" value="张飞"></property>
        <property name="age" value="30"></property>
</bean>
```



### 5.7.2 注入集合数据类型

#### 1）List集合注入

```java
public class UserDaoImpl implements UserDao {
    
    // 注入List集合数据类型
   private List<Object> list;
    

    // 采用set方法完成注入List集合数据类型
    public void setList(List<Object> list) {
        this.list = list;
    }
    
    @Override
    public void save() {

        System.out.println("list集合：" + list);
    }
}
```

```xml
<bean id="user" class="com.lagou.domain.User">
  <property name="username" value="jack"/>
  <property name="age" value="18"/>
</bean>


<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
    
  <!--    进行list集合数据类型的注入

            name="list"：配置UserDaoImpl这个类 里面的list属性
   -->
        <property name="list">
<!--            在property标签中 用到list子标签，表示要注入list集合-->
            <list>
                <value>jjj</value>
<!--                把list集合中第二个元素的值设置为user对象
                        使用ref标签，用于引用数据类型的注入，user就是引用数据类型
                            bean="user"：bean属性指定当前要去注入的对象的标识
-->
                <ref bean="user"></ref>
            </list>
        </property>
</bean>
```



#### 2）Set集合注入

```java
public class UserDaoImpl implements UserDao {
  // 注入Set集合数据类型
   private Set<Object> set;
    

    // 采用set方法完成注入Set集合数据类型
    public void setSet(Set<Object> set) {
        this.set = set;
    }
    
    @Override
    public void save() {

        System.out.println("Set集合：" + set);
    }
}
```

```xml
<bean id="user" class="com.lagou.domain.User">
  <property name="username" value="jack"/>
  <property name="age" value="18"/>
</bean>
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
  <!--    进行set集合数据类型的注入    -->

        <property name="set">
            <!--            在property标签中 用到set子标签，表示要注入set集合-->
            <set>
<!--                注入普通数据类型-->
                <value>lkkk</value>
                <!--                把list集合中第二个元素的值设置为user对象
                                        使用ref标签，用于引用数据类型的注入，user就是引用数据类型
                                            bean="user"：bean属性指定当前要去注入的对象的标识
                -->
                <ref bean="user"></ref>
            </set>
        </property>
</bean>
```



#### 3）Array数组注入

```java
public class UserDaoImpl implements UserDao {
  // 注入Array数组数据类型
   private Object[] array;
    

    // 采用set方法完成注入Array数组数据类型
    public void setArray(Object[] array) {
        this.array = array;
    }
    
    @Override
    public void save() {

        System.out.println("array数组：" + Arrays.toString(array));
    }
}
```

```xml
<bean id="user" class="com.lagou.domain.User">
  <property name="username" value="jack"/>
  <property name="age" value="18"/>
</bean>
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
  <!--    进行array数组数据类型的注入    -->
        <property name="array">
            <array>
                <value>3gggg</value>
                <ref bean="user"></ref>
            </array>
        </property>	
</bean>
```



#### 4）Map集合注入

```java
public class UserDaoImpl implements UserDao {
  // 注入Map集合数据类型
   private Map<String,Object> map;
    

    // 采用set方法完成注入Map集合数据类型
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    
    @Override
    public void save() {

        System.out.println("map集合：" + map);
    }
}
```

```xml
<bean id="user" class="com.lagou.domain.User">
  <property name="username" value="jack"/>
  <property name="age" value="18"/>
</bean>
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
  <!--     进行map集合数据类型的注入   -->
        <property name="map">
            <map>
<!--              map集合中  第一个元素 key为k1，value为lll-->
                <entry key="k1" value="lll"></entry>
<!--                map集合中 第二个元素 key为k2， value为user引用-->
                <entry key="k2" value-ref="user"></entry>
            </map>
        </property>
</bean>
```



#### 5）Properties配置注入

```java
public class UserDaoImpl implements UserDao {
  // 注入properties数据类型
   private Properties properties;
    

    // 采用set方法完成注入properties数据类型
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    @Override
    public void save() {

        System.out.println("properties:" + properties);
    }
}
```

```xml
<bean id="user" class="com.lagou.domain.User">
  <property name="username" value="jack"/>
  <property name="age" value="18"/>
</bean>
<bean id="userDao" class="com.lagou.dao.impl.UserDaoImpl">
 <!--    进行properties数据类型的注入    -->
    <property name="properties">
        <props>
            <prop key="k1">v1</prop>
            <prop key="k2">v2</prop>
            <prop key="k3">v3</prop>
        </props>
    </property>
</bean>
```





## 5.8 配置文件模块化

​	实际开发中，Spring的配置内容非常多，就导致Spring配置很复杂，体积大。所以，可以将部分配置进行拆解到其他配置文件中，也就是所谓的	**配置文件模块化**。

---



**可以按层进行拆分，或者按业务模块进行拆分**

---



**1）并列的多个配置文件同时加载**

```java
ApplicationContext act =
 new
ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-dao.xml","...");
```



**2）主从配置文件**		（常用）			在ApplicationContext.xml 中借助import标签引入

```xml
<!--    借助import标签      来配置文件模块化
        主从配置文件加载
        resource="classpath:"：子配置文件的路径
-->
    <import resource="classpath:applicationContext-user.xml"></import>
```

注意：

* 同一个xml中不能出现相同名称的bean，如果出现会报错。	
  * 就是在一个ApplicationContext.xml 中，bean标签的id值不能重复
* 多个xml中如果出现了相同名称，也就是id值相同 的bean，不会报错，但是后加载的bean 会覆盖前面加载的bean





## 5.9 知识小结

Spring的重点配置

```markdown
IOC的重点就是bean标签配置，bean标签就是创建对象并放到spring的IOC容器中
        id属性是唯一标识，不允许重复
        class属性：要实例化的Bean的全路径
        scope属性：Bean的作用范围   常用：singleton（默认）单例对象   和prototype：当前创建的对象是多例的




  
  	

DI 依赖注入用到的标签
        constructor-arg标签：采用有参构造进行依赖注入的标签
            name属性：对应的属性名，有参构造中的参数名称
            value属性：注入普通属性值时用到的
            ref属性：注入的对象引用值时用到的



        property标签：使用set方式进行属性注入时会用到的标签
            name属性：属性名称
            value：注入普通属性值
            ref：注入对象引用值

            注入集合类型时的标签
            <list>
            <set>
            <array>
            <map>
            <props>
            子标签



  配置文件模块化的标签：
  	import标签：导入其他的Spring的分文件的
```







# 六、DBUtils（IOC实战）

## 6.1 DBUtils是什么？

​	DBUtils就是Apache的一款用于简化Dao代码的工具类，底层封装了JDBC

**核心对象**

```java
QueryRunner  qr  = new QueryRunner(DataSource  dataSource)
```



**核心方法**

```java
int	update();	// 执行增删改操作时的方法

T query();		// 执行查询操作时的方法
	ResultSetHandler<T>		// 接口，把数据库返回的记录封装到实体对象中
```



例：

​	查询数据库所有账户信息到Account实体中

```java
public class DbUtilsTest {
  @Test
  public void findAllTest() throws Exception {
    // 创建DBUtils工具类，传入连接池
    QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
    // 编写sql
    String sql = "select * from account";
    // 执行sql
    List<Account> list = queryRunner.query(sql, new BeanListHandler<Account>
(Account.class));
    // 打印结果
    for (Account account : list) {
      System.out.println(account);
   }
 }
}
```





## 6.2 使用Spring的XML配置整合DBUtils

### 6.2.1 介绍

**需求**：

​	基于Spring的xml配置实现账户的CRUD

**步骤分析**

```markdown
1. 准备数据库环境
2. 创建java项目，导入依赖
3. 编写Account实体类，和账户表有对应关系
4. 编写AccountDao接口和实现类
5. 编写AccountService接口和实现类
6. 编写Spring核心配置文件。就是把AccountDao和AccountService实例对象的创建交给Spring，在核心配置文件中配置Bean标签

7. 编写测试代码
```



### 6.2.2 实现

#### 1）准备数据库环境

```sql
CREATE DATABASE `spring_db`;
USE `spring_db`;
CREATE TABLE `account` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(32) DEFAULT NULL,
`money` double DEFAULT NULL,
PRIMARY KEY (`id`)
) ;
insert  into `account`(`id`,`name`,`money`) values (1,'tom',1000),
(2,'jerry',1000);
```



2）创建java项目，导入依赖

```xml
<dependencies>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>1.6</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>javax.annotation-api</artifactId>
            <version>1.3.1</version>
        </dependency>


    </dependencies>
```



#### 3）编写Account实体类

```java
public class Account {

    private Integer id;
    private String name;
    private Double money;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
```



#### 4）编写AccountDao接口和实现类

```java
public interface AccountDao {


    /*
        查询所有账户信息方法
     */
    public List<Account> findAll();

    /*
        根据id查询账户的方法
     */
    public Account findById(Integer id);


    /*
        添加方法
     */
    public void save(Account account);

    /*
        更新方法
     */
    public void update(Account account);

    /*
        删除方法
     */
    public void delete(Integer id);
}
```

```java
public class AccountDaoImpl implements AccountDao {

    // 需要QueryRunner 对象，就可以把QueryRunner对象的创建交给Spring的IOC容器去创建
    // 而在AccountDaoImpl中 需要这个QueryRunner对象，那直接去IOC容器中去获取。进行注入
    private QueryRunner qr;

    // 表示要让spring借助set方式来把QueryRunner  注入进来，并赋值给qr成员变量
    public void setQr(QueryRunner qr) {
        this.qr = qr;
    }

    @Override
    public List<Account> findAll() {

        List<Account> list = null;
        // 编写Sql
        String sql = "select * from account";
        try {
            // 执行SQL
            list = qr.query(sql, new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Account findById(Integer id) {

        Account query = null;

        // 编写SQL
        String sql = "select * from account where id = ?";

        try {
            query = qr.query(sql, new BeanHandler<Account>(Account.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }

    @Override
    public void save(Account account) {

        String sql = "insert into account values(null,?,?)";
        try {
            qr.update(sql, account.getName(), account.getMoney());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {

        String sql = "update account set name = ?,money = ? where id = ?";
        try {
            qr.update(sql,account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {

        String sql = "delete from account where id = ?";
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```



#### 5）编写AccountService接口和实现类

```java
public interface AccountService {

    /*
        查询所有账户信息方法
     */
    public List<Account> findAll();

    /*
        根据id查询账户的方法
     */
    public Account findById(Integer id);


    /*
        添加方法
     */
    public void save(Account account);

    /*
        更新方法
     */
    public void update(Account account);

    /*
        删除方法
     */
    public void delete(Integer id);
}
```

```java
public class AccountServiceImpl implements AccountService {
    
    // 业务层里的方法要调用Dao层的方法
    // 所以在AccountServiceImpl 要用到 AccountDaoImpl对象
    // 通过配置就可以让spring把容器中的accountDao实例对象注入进来
    // 所以使用Spring DI依赖注入
    
  private AccountDao accountDao;
    
    // 通过set方式来注入accountDao 实例对象
  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
 }
  @Override
  public List<Account> findAll() {
      
      // 借助accountDao对象调用方法
    return accountDao.findAll();
 }
  @Override
  public Account findById(Integer id) {
    return accountDao.findById(id);
 }
  @Override
  public void save(Account account) {
    accountDao.save(account);
 }
  @Override
  public void update(Account account) {
    accountDao.update(account);
 }
  @Override
  public void delete(Integer id) {
    accountDao.delete(id);
 }
}
```



#### 6）编写Spring核心配置文件

在 applicationContext.xml   配置Bean标签

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
">

    
    
    <!--    Spring帮助我们创建对象就写    bean标签

            若有依赖关系需要注入
                有参构造注入：constructor-arg 子标签
                set方式注入：property 子标签
-->


    

<!--    dataSource 数据库连接池对象，dataSource 的创建权要交由Spring容器去完成，所以对于dataSource创建 bean标签

          当创建出dataSource 实例对象后，在创建QueryRunner 实例对象时，再把dataSource 通过通过有参构造方式注入到QueryRunner 实例对象中

                先让spring把dataSource数据源对象创建出来，存到容器中
                     再创建QueryRunner 的时候，把dataSource通过有参构造方式进行注入
-->

<!--    采用阿里德鲁伊的数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--       当前使用property对 driverClassName进行配置，说明在DruidDataSource对象中 有对应的关于driverClassName 这个属性的set方法
                所以现在使用set方式 进行依赖注入，注入的值就是字符串类型的   驱动类
 -->
        <property name="driverClassName" value="com.mysql.jdbc.Driver">
</property>
    <property name="url" value="jdbc:mysql:///spring_db">
</property>
    <property name="username" value="root"></property>
    <property name="password" value="123456"></property>
  </bean>




<!--    QueryRunner的创建权交由 Spring容器去完成，因为在AccountDao中是需要注入 QueryRunner 对象的。所以要配置 QueryRunner 的bean标签
              在创建QueryRunner的同时，要去使用构造方法注入 已经在IOC容器中的 dataSource 实例对象



    当创建出QueryRunner 实例对象后，在创建AccountDaoImpl 实例对象时，再把QueryRunner 通过set方式注入到AccountDaoImpl 实例对象中

        QueryRunner对象在创建时，需要注入dataSource

            配置QueryRunner的bean标签，先把QueryRunner 实例对象创建出来存到容器中
                这样在创建AccountDaoImpl实例对象 时，才能完成对QueryRunner 实例对象的注入
-->
    <bean id="qr" class="org.apache.commons.dbutils.QueryRunner">
<!--        在创建QueryRunner 实例对象时，要通过它的有参构造把dataSource注入进来
                所以使用constructor-arg 标签，name属性值要和QueryRunner 里的有参构造里的参数名保持一致
                    ref属性要配置引用数据类型，引用dataSource 数据源对象
-->
        <constructor-arg name="ds" ref="dataSource"/>
    </bean>




<!--    AccountDao
    当创建出AccountDaoImpl 实例对象后，在创建AccountServiceImpl 实例对象时，再把AccountDaoImpl 通过set方式注入到AccountServiceImpl 实例对象中

        在AccountDao对象中，需要注入QueryRunner

            在配置AccountDao bean标签时，还需要在其内部配置子标签 property
                借助property标签，来完成依赖注入。因为在AccountDaoImpl里还有QueryRunner 实例对象
-->

<!--    class：AccountDaoImpl的全路径-->
    <bean id="accountDao" class="com.lagou.dao.imp.AccountDaoImpl">
<!--        通过set方式去注入QueryRunner 实例对象
                name：就是AccountDaoImpl 类中  set方法后面的首字母小写
                ref:注入QueryRunner 对象
    -->
        <property name="qr" ref="qr"/>
    </bean>






<!--    AccountService

            在AccountService对象中，需要注入accountDao
-->

 <bean id="accountService" class="com.lagou.service.impl.AccountServiceImpl">
<!--       在AccountServiceImpl 实例对象中 要注入accountDao 实例对象-->
<!--                所以要通过set方式注入accountDao 实例对象-->
    <property name="accountDao" ref="accountDao"/>
  </bean>
</beans>
```



#### 7）编写测试代码

```java
public class AccountServiceTest {


    // 传入applicationContext.xml  核心配置文件全路径
    // 这样就是在 new classPathXmlApplicationContext 对象时，会去加载 applicationContext.xml
    // 并且根据里面的bean标签，来生成各自的实例对象 并注入到IOC容器内
    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    // 调用getBean方法。获取accountService 实例对象，因为accountService 实例对象去调用了accountDao 实例对象
    // 拿到AccountServiceImpl 实现类对象，用接口接
    AccountService accountService = (AccountService) classPathXmlApplicationContext.getBean("accountService");

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
        classPathXmlApplicationContext.close();
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
```



#### 8）抽取jdbc配置文件

applicationContext.xml 加载 jdbc.properties 配置文件获得数据源信息

首先，需要引入context命名空间和约束路径：

```markdown
* 命名空间
	xmlns:context="http://www.springframework.org/schema/context"

* 约束路径
	http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
```

```xml
<!--    新知识点：   Spring容器加载properties文件，要用到  context:property-placeholder  标签
            location：就是 properties 文件所对应的路径
                当加载好了properties文件后， 如果想引用到properties 文件里的内容，用到 Spring的el表达式: ${key}   获得到对应的value



            在 Spring的核心配置文件 applicationContext.xml 中如何去加载一个 jdbc.properties？

                主要就是借助 context:property-placeholder  这个标签
-->




<!--    小技巧：
            只要在Spring的核心配置文件 applicationContext.xml中 想要去引入外部其他的配置文件时
                不管是import标签还是 context:property-placeholder 标签。只要是想要引入外部的一些配置文件时
                    在写位置时，前面都要加 classpath:
                        就是当前在找文件时，会去定位到当前项目编译过后的classes目录下去进行查找
                            所以要添加  classpath:
        -->


<!--  表示在 加载 applicationContext.xml 配置文件的同时
        借助 context:property-placeholder  标签来把jdbc.properties文件 也引入加载了

                这样就可以在编写 数据源 配置信息 时 编写value值时 直接去引用在jdbc.properties文件  配好的一些数据库的相关信息了
  -->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>



<!--    若想在加载applicationContext.xml 的同时把 jdbc.properties文件也加载的话
            首先就需要引入 Context命名空间和约束路径-->


<!--    采用阿里德鲁伊的数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--       使用property对 driverClassName进行配置，说明在DruidDataSource对象中 有对应的关于driverClassName 这个属性的set方法
                所以现在使用set方式 进行依赖注入，注入的值就是字符串类型的   驱动类
 -->
<!--        使用抽取的properties文件来配置数据源 信息
                写value值时  要引用到 在jdbc.properties配置好的值
                    ${}：里面写jdbc.properties  里面配置的 key值，这样就可以根据key值 获取到 对应的value值
-->
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
```





## 6.3 知识小结

Spring的xml方式整合DBUtils时的关键点：

```markdown
* DataSource的创建权交给Spring容器去完成

* QueryRunner的创建权交给Spring容器去完成，因为在AccountDao中需要注入QueryRunner。 在创建QueryRunner的同时，要使用有参构造方法注入已经创建好的DataSource

* Spring容器加载properties文件
		当Spring容器需要加载properties文件时，要用到  context:property-placeholder  标签
		
            location：就是 properties 文件所对应的路径
                当加载好了properties文件后， 如果想引用到properties 文件里的内容，用到 Spring的el表达式: ${key}   获得到对应的value


		<context:property-placeholder location="xx.properties"/>
		<property name="" value="${key}"/>
```





# 七、Spring注解开发

​	Spring是轻代码重配置的框架，配置比较多，影响开发效率。所以采用注解开发，代替xml配置文件进行配置简化，提高开发效率



## 7.1 Spring常用注解

### 7.1.1 介绍

Spring常用注解主要是代替<bean/> 标签

| 注解                                    | 说明                                                         |
| --------------------------------------- | ------------------------------------------------------------ |
| **@Component**                          | 使用在类上用于实例化Bean                                     |
| **@Controller**                         | 使用在web层类上用于实例化Bean                                |
| **@Service**                            | 使用在service层类上用于实例化Bean                            |
| **@Repository**                         | 使用在dao层类上用于实例化Bean                                |
| **@Autowired**                          | 使用在字段上用于根据类型依赖注入                     （常用） |
| **@Qualifier**                          | 结合@Autowired一起使用，根据名称进行依赖注入                 |
| **@Resource**                           | 相当于@Autowired+@Qualifier，按照名称进行注入                |
| **@Value**                              | 注入普通属性值                                               |
| **@Scope**                              | 标注Bean的作用范围                                           |
| @PostConstruct   (init-method 属性)     | 使用在方法上标注该方法是Bean的初始化方法                     |
| @PostDestroy      (destroy-method 属性) | 使用在方法上标注该方法是Bean的销毁方法                       |

----

@Component、@Controller、@Service和@Repository			都是相当于配置了<bean>标签，**生成类的实例对象存到IOC容器中**

@Autowired、@Qualifier、@Resource  注入引用类型的 和@Value						相当于配置<property>标签，**就是要进行依赖注入**。（使用注解进行依赖注入时，是采用反射）

@Autowired 是根据类型去完成依赖注入。@Qualifier 结合@Autowired 一起使用，不能单独使用



@Scope：相当于配置scope属性，指定bean的作用范围

----



说明：

JDK11以后完全移除了javax扩展导致不能使用@Resource注解

```xml
需要maven引入依赖

 <dependency>
   <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
   <version>1.3.2</version>
</dependency>
```



**注意**

​	使用注解开发时，需要在applicationContext.xml 中配置组件扫描，作用是指定哪个包及其子包下的Bean需要进行扫描以便识别使用注解配置的类、字段和方法。

就是让配置的主键生效

```xml 
<!--    *** 只有配置注解扫描，注解才能生效
                base-package="com.lagou"：就会对com.lagou 这个包及其子包里面的所有类都进行注解扫描
                    让这个包及其子包中所有类中所配置的注解都能够生效
            -->
    <context:component-scan base-package="com.lagou"></context:component-scan>
```



### 7.1.2 实现

#### 1）Bean实例化（IOC）	   

基于xml方式，配置bean标签实现Bean实例化

```xml
<bean id = "userDao" class = "com.lagou.dao.impl.UserDaoImpl"/>
```

使用注解的方式实现Bean实例化，达到和配置bean标签一样的效果



使用注解方式去生成AccountServiceImpl的实例对象存到容器中，并基于注解实现accountDao的依赖注入

```java
// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于指定当前实例对象 在IOC容器中所对应的key值
@Scope("singleton")         // prototype 表示该实例对象 创建多个
public class AccountServiceImpl implements AccountService {

}
```



#### 2）属性依赖注入（DI）

```xml
<bean id="accountService"class="com.lagou.service.impl.AccountServiceImpl">
  <property name="accountDao" ref="accountDao"/>
</bean>
```

使用注解对于accountDao这个对象的注入

```java
// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于当前实例对象 在IOC容器中所对应的key值
@Scope("singleton")         // prototype 表示该实例对象 创建多个
public class AccountServiceImpl implements AccountService {

    // 使用注解完成依赖注入
    @Autowired  // 根据类型去完成依赖注入，若根据类型查找，匹配到多个后，会再根据变量名进行二次匹配。换了变量名就不行了，报错

    @Qualifier("accountDao")   // 表示先根据类型查找，如果匹配到多个再根据名称查找     要结合@Autowired使用，不能单独使用

    //@Resource(name = "accountDao")       根据bean  id进行注入
    private AccountDao aDao;
}
```



#### 3）@Value 注入普通属性值

使用@Value 进行字符串的注入，结合SPEL表达式获得配置参数

```java
// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Value("注入普通属性")
    private String str;

    // @Value("${jdbc.driverClassName}"):写jdbc.properties中 所配置的key值，根据key 获取value值进行注入
    @Value("${jdbc.driverClassName}")
    private String driver;
}
```



#### 4）@Scope

相当于在bean标签中配置的 scope属性

```xml
<bean scope=""/>
```

使用@Scope标注Bean的范围	

```java
// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于当前实例对象 在IOC容器中所对应的key值
@Scope("singleton")         // prototype 表示该实例对象 创建多个
public class AccountServiceImpl implements AccountService {
}
```



#### 5）Bean生命周期

在使用xml方式配置Bean标签时，里面的init-method属性 指定初始化方法 和destroy-method属性  指定销毁方法

```xml
<bean init-method="init" destroy-method="destory" />
```

使用@PostConstruct标注初始化方法，使用@PreDestroy标注销毁方法

```java
@PostConstruct
    public void init(){
        System.out.println("初始化方法...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁方法...");
    }
```



## 7.2 Spring常用注解整合DBUtils

**步骤分析**

```markdown
1. 拷贝xml配置项目，改为注解配置项目
2. 修改AccountDaoImpl实现类		（在AccountDaoImpl中添加注解）
3. 修改AccountServiceImpl实现类
4. 修改Spring核心配置文件           不用写Bean标签了
5. 编写测试代码
```



### 1）拷贝xml配置项目，改为常用注解配置项目

略。。

### 2）修改AccountDaoImpl实现类

```java
/**
 *  注解方式
 *      持久层的类要使用 @Repository
 *          @Repository 这个注解是使用在dao层类上 用于实例化Bean的注解 
 */
@Repository     // 相当于配置了bean标签
public class AccountDaoImpl implements AccountDao {

    @Autowired      // 使用注解方式进行注入
    private QueryRunner qr;

    ...
}
```



### 3）修改AccountServiceImpl实现类

```java
// 使用@Service 来生成该类的实例对象并存到IOC容器中，
@Service("accountService")  // 相当于配置了bean标签        id属性用于当前实例对象 在IOC容器中所对应的key值
public class AccountServiceImpl implements AccountService {

    // 使用注解完成依赖注入
    @Autowired  // 根据类型去完成依赖注入，若根据类型查找，匹配到多个后，会再根据变量名进行二次匹配。换了变量名就不行了，报错
    private AccountDao aDao;


    ...
}
```



### 4）修改Spring核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w1.org/2001/XMLSchema-instance"
   xmlns:context="http://www.springframework.org/schema/context"
   xsi:schemaLocation="
   http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd">
  <!--注解的组件扫描-->
    <context:component-scan base-package="com.lagou"></context:component-scan>
  <!--加载jdbc配置文件-->
  <context:property-placeholder location="classpath:jdbc.properties">
</context:property-placeholder>
  <!--把数据库连接池交给IOC容器-->
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="${jdbc.driver}"></property>
    <property name="url" value="${jdbc.url}"></property>
    <property name="username" value="${jdbc.username}"></property>
    <property name="password" value="${jdbc.password}"></property>
  </bean>
  <!--把QueryRunner交给IOC容器-->
  <bean id="queryRunner" class="org.apache.commons.dbutils.QueryRunner">
    <constructor-arg name="ds" ref="dataSource"></constructor-arg>
  </bean>
</beans>
```



### 5）编写测试代码

```java
public class AccountServiceTest {
  ApplicationContext applicationContext =
      new ClassPathXmlApplicationContext("applicationContext.xml");
  AccountService accountService =
applicationContext.getBean(AccountService.class);
  //测试查询
  @Test
  public void findByIdTest() {
    Account account = accountService.findById(3);
    System.out.println(account);
 }
}
```





## 7.3 Spring新注解

 	使用上面的注解还不能全部替代xml配置文件，还需要使用注解替代的配置：

```xml
* 非自定义的Bean的配置：<bean>
    
* 加载properties文件的配置：<context:property-placeholder>
    
* 组件扫描的配置：<context:component-scan>
    
* 引入其他文件：<import>
```



| 注解            | 说明                                               |
| --------------- | -------------------------------------------------- |
| @Configuration  | 被当前这个注解所标注的类，为核心配置类             |
| @Bean           | 使用在方法上，标注将该方法的返回值存到Spring容器中 |
| @PropertySource | 用于加载外部的 properties文件                      |
| @ComponentScan  | 用于指定 Spring在 初始化容器时要扫描的包           |
| @Import         | 用于导入其他配置类                                 |



## 7.4 Spring纯注解方式整合DBUtils

**步骤分析**

```markdown
1. 编写Spring核心配置类		用到新注解，替换标签	
2. 编写数据库配置信息类
3. 编写测试代码
```



### 1）编写Spring核心配置类

```java
/**
 *  核心配置类           替换掉applicationContext.xml 核心配置文件
 *     被 @Configuration 标注的类就为核心配置类
 */
@Configuration
// @ComponentScan   这个注解用于指定 Spring 在初始化容器时要扫描的包
// 指定属性，要对哪个包进行扫描。 表示会扫描该包及其子包下的所有类，让注解生效
@ComponentScan("com.lagou")
// 加载SpringConfig 这个核心配置类的同时，也把DataSourceConfig 这个类也引入加载   就要使用@Import注解，导入其他配置类
@Import(DataSourceConfig.class)
public class SpringConfig {

    // 配置QueryRunner  这个Bean
    @Bean("qr")         // 默认，方法的名字作为key，返回值作为value存到IOC容器          给值自定义key


    // 因为在QueryRunner 实例对象中 需要用dataSource 实例对象。
    // 所以需要注入，声明一个参数。这个方法由Spring调用
    // Spring 在调用时，就会把在 IOC容器中 已经生成的dataSource 实例对象当做参数来进行传递

    // 所以要 添加 @Autowired 注解    告诉Spring，根据DataSource 这个类型，从IOC容器中找到对应的实例对象 并注入给参数


    // @Autowired DataSource dataSource  这个 就相当于 在核心配置文件中 配置的constructor-arg 标签  有参方式依赖注入
    public QueryRunner getQueryRunner(@Autowired DataSource dataSource){

        // 根据传递进来dataSource 来创建QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }
}
```



### 2）编写数据库配置信息类

把关于数据库配置信息进行拆分

```java
/**
 *  数据源信息的配置类
 */
// @PropertySource()    这个注解用于 加载properties文件中的配置       需要去指定要加载的properties文件路径  其实就是标签中的location 的值
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {

    // 借助@Value 注解，把jdbc.properties 里面的内容引入到成员变量中
    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;


    // 通过使用 @Bean注解  注解方法 获取返回值的方式   把DataSource 这个实例对象存到IOC容器中

    // @Bean   对于jar包中的类，采用这个注解  去配置非自定义的类的配置
    // 使用在方法上，会把该方法的返回值村到 IOC容器中

    @Bean("dataSource")     // 起key值，不起的话  对象在IOC容器中对应的key就是方法名
    public DataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        // 返回druidDataSource    @Bean注解 就会把返回值放进IOC容器中
        return druidDataSource;
    }
}
```



### 3）编写测试代码

```java
public class AccountServiceTest {
// 当前改成了纯注解形式
    // 采用注解形式   就要 new  ApplicationContext 这个接口 的    AnnotationConfigApplicationContext  这个实现类
    
    // 把 加载核心配置文件改成 加载核心配置类
    AnnotationConfigApplicationContext annotationConfigApplicationContext =  new AnnotationConfigApplicationContext(SpringConfig.class);
    
    AccountService accountService = (AccountService) annotationConfigApplicationContext.getBean("accountService");
  //测试查询
  @Test
  public void testFindById() {
    Account account = accountService.findById(3);
    System.out.println(account);
 }
}
```



# 八、Spring整合Junit

## 8.1 普通Junit测试问题

在普通的测试类中，需要开发者手动加载配置文件并创建Spring容器，然后通过Spring相关API获得Bean实例；如果不这么做，那么无法从容器中获得对象。

```java
ApplicationContext applicationContext =
 new ClassPathXmlApplicationContext("applicationContext.xml");
 
 
AccountService accountService =
applicationContext.getBean(AccountService.class);
```



可以让SpringJunit负责创建Spring容器简化操作，也就是不用手动编写API了。但是需要将配置文件名称告诉它，**让SpringJunit负责去加载配置文件，构建IOC容器，并且生成实例存到IOC容器中**。我们直接使用 @Autowired  这个注解  注入测试的对象



## 8.2 Spring整合Junit

**步骤分析：**

```markdown
1. 导入Spring集成Junit的坐标
2. 使用@Runwith注解替换原来的运行器        就是指定当前Junit的运行环境
3. 使用@ContextConfiguration指定配置文件或配置类
4. 使用@Autowired注入需要测试的对象
5. 创建测试方法进行测试
```

​	

### 1）导入Spring集成Junit的坐标

```xml
<!--        此处需要注意：spring 5 及以上版本 在整合Junit时 要求junit 版本必须是  4.12 及以上版本  -->

<!--        若当前用到的Spring版本是 5以上的
                那么在导入junit对应的坐标时，version版本  最低是 4.12。  若低于4.12  会报错
    -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

<!--        Spring整合Junit  导入依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
</dependency>
```



### 2）使用@Runwith注解替换原来的运行器	  

```java
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
public class SpringJunitTest {
 
}
```



### 3）使用@ContextConfiguration指定配置文件或配置类

```java
@RunWith(SpringJUnit4ClassRunner.class)
// 使用 @ContextConfiguration 这个注解 来指定核心配置文件或核心配置类的路径

// 如果是进行xml配置文件 开发，当前工程中 存在 applicationContext.xml  时，就写 classpath:applicationContext.xml
//@ContextConfiguration({"classpath:applicationContext.xml"})

// 若采用的是纯注解开发，就要去指定当前核心配置类所在的路径
// classes =    ：核心配置类所在的路径       {}：可以配置多个核心配置类

// 指定要去加载的核心配置文件或核心配置类
@ContextConfiguration(classes={SpringConfig.class})	
public class SpringJunitTest {
 
}
```



### 4）使用@Autowired注入需要测试的对象

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringJunitTest {
 
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
}
```



### 5）测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringJunitTest {
 
 @Autowired
  private AccountService accountService;
 
  // 测试查询方法
    @Test
    public void testFindById(){

        Account account = accountService.findById(3);

        System.out.println(account);
    }
}
```

