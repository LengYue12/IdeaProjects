# 任务二：AOP学习笔记

# 一 转账案列

**需求：**

​	使用Spring框架整合DBUtils技术，来实现用户转账功能



## 1.1 基础功能

**步骤分析**

```markdown
1. 创建Java项目，导入依赖
2. 编写Account实体类
3. 编写AccountDao接口和实现类			使用DBUtils API 操作数据库
4. 编写AccountService接口和实现类		调用Dao的方法
5. 编写Spring核心配置文件
6. 编写测试代码
```



### 1）创建java项目，导入坐标

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
            <version>1.1.15</version>
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
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

    </dependencies>
```



### 2）编写Account实体类

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



### 3）编写AccountDao接口和实现类

```java
public interface AccountDao {

    // 转账本质就是：同时调用这两个方法，实现一个账户加钱，一个账户减钱

    // 转出操作，减钱          表示哪个账户要转出多少钱
    public void out(String outUser,Double money);

    // 转入操作，加钱          表示哪个账户要增加多少钱
    public void in(String inUser,Double money);
}
```

```java
/*
	加载核心配置文件，构建IOC容器之后。就会生成该类的实例对象存到IOC容器中，并且	Spring会完成依赖注入。
 	注入QueryRunner实例对象
*/

@Repository("accountDao")     // 采用注解方式生成该类实例存到IOC容器中，默认key值就是该类的类名小写
public class AccountDaoImpl implements AccountDao {

    // QueryRunner实例对象 由Spring创建，并注入到AccountDaoImpl
    @Autowired
    private QueryRunner qr;


    /*
        转出操作，根据传递过来的值，修改该用户的金额
     */
    @Override
    public void out(String outUser, Double money) {

        String sql = "update account set money = money - ? where name = ?";
        try {
            
            qr.update(sql,money,outUser);
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
            qr.update(sql,money,inUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```



### 4） 编写AccountService接口和实现类

```java
public interface AccountService {

    // 转账方法
    // 指定转出账户，转入账户和操作的金额
    public void transfer(String outUser,String inUser,Double money);
}
```

```java
// 采用注解方式，生成当前实例存到IOC容器中
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    // 在当前业务层中要调用持久层方法，所以就要注入持久层对象

    // 完成依赖注入，根据类型在当前实例对象中注入AccountDao实例对象
    @Autowired
    private AccountDao accountDao;



    /*
        转账方法      切入点       当前要增强的业务逻辑：就是为该方法上添加上事务控制的效果
            要对 transfer 这个方法进行拦截增强
     */
    @Override
    public void transfer(String outUser, String inUser, Double money) {

            // 调用了减钱方法，转出操作
            accountDao.out(outUser,money);

            // int i = 1/0;

            // 调用了加钱方法，转入操作
            accountDao.in(inUser,money);

    }
}
```



### 5）编写Spring核心配置文件

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

<!--    开启注解扫描，使注解生效-->
    <context:component-scan base-package="com.lagou"></context:component-scan>

<!--引入properties文件
        location：指定文件路径，要加上classpath:       在核心配置文件中引入其他配置文件前面都要加classpath:
-->
<context:property-placeholder location="classpath:jdbc.properties"/>

<!--配置DataSource 数据源对象-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--        配置数据库信息-->
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

<!--    配置QueryRunner-->

<bean id="qr" class="org.apache.commons.dbutils.QueryRunner">
<!--    在构建QueryRunner时，要使用有参方式注入DataSource数据源对象-->
    <constructor-arg name="ds" ref="dataSource"></constructor-arg>
</bean>
</beans>
```



### 6）编写测试代码

```java
// 使用注解Spring整合Junit
// 指定运行环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})     // 指定加载的核心配置文件路径
public class AccountServiceTest {

    // 注入
    @Autowired
    private AccountService accountService;
    @Test
    public void testTransfer(){

        accountService.transfer("tom","jerry",100d);
    }
}
```



### 7）问题分析

​		转出操作和转入操作都是一个独立的事务，而且互不干扰，就有可能导致转出操作成功，转入操作失败。所以应该把业务逻辑控制在同一个事务中，让转出操作和转入操作要么都执行成功，要么都执行失败。 事务控制通常在service层

​	所以要在service层进行业务控制，让转出操作和转入操作处在同一个事务中，这两个操作要么全部成功，要么全部失败





## 2.2 传统事务

**步骤分析：**

```markdown
1. 编写线程绑定工具类		要把用到的Connection对象与当前线程进行绑定，从而保证在dao层调用方法时用到的Connection是同一个，才能够保证它们处在同一个事务中

2. 编写事务管理器类			编写事务的相关代码
3. 修改service层代码		  在service层方法中添加事务操作
4. 修改Dao层代码			  
```



### 1）编写线程绑定工具类

就是为了保证在dao层多个方法执行时，所用到的Connection是同一个。借助ThreadLocal实现

```java
/*
    连接工具类：从数据源中获取一个连接，并且将获取到的连接与线程进行绑定
       工具类作用：能够保证在dao层多个方法执行时所用到的Connection是同一个，使得事务也是同一个

        ThreadLocal：线程内部的存储类，可以在指定的线程内存储数据      key：threadLocal（当前线程）     value：任意类型的值 Connection
 */
@Component      // 生成该类的实例对象存到IOC容器中
public class ConnectionUtils {

    // 注入数据源对象
    @Autowired
    private DataSource dataSource;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();


    /*
        获取当前线程上绑定的连接：如果获取到的连接为空，那么就要从数据源中获取连接，并且放到ThreadLocal中（绑定到当前线程）
     */
    public Connection getThreadConnection(){

        // 1.先从ThreadLocal上获取连接。 获取容器中当前所存的内容
        Connection connection = threadLocal.get();

        // 2.判断当前线程中是否是有Connection
        if (connection == null) {
            // 3.从数据源中获取一个连接，并且存入到ThreadLocal中
            try {
                // 不为null
                connection = dataSource.getConnection();

                threadLocal.set(connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

         return connection;

    }


    /*
        解除当前线程的连接绑定
     */
    public void removeThreadConnection(){
        // 删除当前ThreadLocal中所存的value值
        threadLocal.remove();
    }
}
```



### 2）编写事务管理器

定义了事务相关的方法，在service层中需要去使用事务相关的操作时，就使用这个事务管理器调用方法就ok

```java
/*
    事务管理器工具类：包含：开启事务、提交事务、回滚事务、释放资源方法
 */

@Component("transactionManager")      // 生成该类的实例存到IOC容器中
public class TransactionManager {

    @Autowired  // 注入
    private ConnectionUtils connectionUtils;


    /*
        开启事务
     */
    public void beginTransaction(){

        // 获取Connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            // 开启了一个手动事务，关闭自动提交
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        提交事务
     */
    public void commit(){

        // 获取的是同一个connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        回滚事务
     */
    public void rollback(){

        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        释放资源
     */
    public void release(){

        // 将手动事务改回成自动提交事务
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.setAutoCommit(true);

            // 将连接归还到连接池
            connectionUtils.getThreadConnection().close();
            // 解除线程绑定
            connectionUtils.removeThreadConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```



### 3）修改Service层代码

在业务操作的前后添加事务相关的方法调用

```java
// 采用注解方式，生成当前实例存到IOC容器中
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    // 在当前业务层中要调用持久层方法，所以就要注入持久层对象

    // 完成依赖注入，根据类型在当前实例对象中注入AccountDao实例对象
    @Autowired
    private AccountDao accountDao;

    @Autowired
  private TransactionManager transactionManager;


    /*
        转账方法      切入点       当前要增强的业务逻辑：就是为该方法上添加上事务控制的效果
            要对 transfer 这个方法进行拦截增强
     */
    @Override
    public void transfer(String outUser, String inUser, Double money) {
try {
      // 1.开启事务
      transactionManager.beginTransaction();
      // 2.业务操作
      // 调用了减钱方法，转出操作
       accountDao.out(outUser,money);
        // int i = 1/0;
       // 调用了加钱方法，转入操作
       accountDao.in(inUser,money);
      // 3.提交事务
      transactionManager.commit();
   } catch (Exception e) {
      e.printStackTrace();
      // 4.回滚事务
      transactionManager.rollback();
   } finally {
     // 5.释放资源
      transactionManager.release();
   }
 }
}
```



### 4）修改dao层代码

```java
/**
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
```



### 5）问题分析

​	通过对业务层改造，可以实现事务控制。但是业务层方法太多，臃肿，里面有很多重复代码。并且业务层方法和事务控制方法耦合了。		

​	就是业务方法和事务控制方法耦合了，并且代码冗余了。





# 二、Proxy优化转账案列

​	我们可以将业务代码和事务代码进行拆分，通过动态代理的方式，对业务方法进行事务的增强。这样就不会对业务层产生影响，业务层代码也不冗余了，就可以解决耦合性问题了



**常用的动态代理技术**

* JDK代理：**基于接口的动态代理技术**：利用拦截器（必须实现invocationHandler）加上反射机制生成一个代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理，从而实现方法增强
* CGLIB代理：**基于父类的动态代理技术**：动态生成一个要代理的子类，子类重写要代理的类的所有不是final的方法。在子类中采用方法拦截技术拦截所有的父类方法的调用，顺势织入横切逻辑，对方法进行增强



## 笔试，动态代理区别

**JDK动态代理是基于接口的动态代理，而cglib动态代理是基于父类的动态代理**



## 2.1 JDK动态代理方式优化转账案列

JDK工厂类，用来创建出代理对象。在目标方法前后增加上事务控制的代码

```java
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
```

测试

```java
// 使用注解Spring整合Junit
// 指定运行环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})     // 指定加载的核心配置文件路径
public class AccountServiceTest {


    // 注入JDK动态代理工厂类实例对象
    @Autowired
    private JDKProxyFactory proxyFactory;

    

    /*
        测试JDK动态代理优化转账案列
     */
    @Test
    public void testTransferProxyJDK(){

        // 当前返回的实际上是AccountService的代理对象         实际类型proxy
        AccountService accountServiceJDKProxy = proxyFactory.createAccountServiceJDKProxy();
        // 代理对象proxy调用接口中的任意方法时，都会执行底层的invoke方法
        // 就是代理对象中的匿名内部类中的invoke方法，通过反射去执行被代理类 AccountServiceImpl中的方法
        accountServiceJDKProxy.transfer("tom","jerry",100d);
        
    }
}
```



## 2.2 CGLIB动态代理方式优化转账案列

cglib工厂类，实现目标类没有被final修饰的方法，创建代理对象

```java
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
```

测试

```java
// 使用注解Spring整合Junit
// 指定运行环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})     // 指定加载的核心配置文件路径
public class AccountServiceTest {


    // 注入cglib动态代理工厂类实例对象
    @Autowired
    private CglibProxyFactory cglibProxyFactory;


    /*
        测试Cglib动态代理优化转账案列
     */
    @Test
    public void testTransferProxyCglib(){

        // accountServiceCglibProxy：类型就是proxy 代理对象
        AccountService accountServiceCglibProxy = cglibProxyFactory.createAccountServiceCglibProxy();

        // 代理对象调用方法时，会走到CglibProxyFactory 这个工厂类 里重写的intercept 这个方法中
        // intercept 这个方法在去调用目标对象 的原方法之前，就开启了事务，执行完原方法后，就提交事务，产生异常回滚事务以及释放资源
        accountServiceCglibProxy.transfer("tom","jerry",100d);

    }
}
```





# 三、初识AOP

## 3.1 什么是AOP

​	AOP：**面向切面编程**

​	利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高开发效率。

**好处：**

1. 在程序运行期间，不修改源码的情况下对方法进行功能增强
2. 逻辑清晰，开发核心业务的时候，不必关注增强业务的代码
3. 减少重复代码，将重复代码使用AOP提取出来，提高开发效率，便于后期维护





## 3.2 AOP底层实现

​	AOP 的底层是通过 Spring 提供的**动态代理技术**实现的。在运行期间，Spring通过动态代理技术动态的生成对象，代理对象方法执行时进行增强功能的介入，在去调用目标对象的方法，从而完成功能的增强。

​	AOP底层就是**动态代理**，封装了动态代理技术





## 3.3 AOP相关术语

​	Spring的AOP 底层实现就是对**动态代理**的代码进行了封装，借助AOP去完成方法增强，就通过**配置**的方式去完成指定目标的方法增强。

理解AOP的相关术语：

```markdown
* Target（目标对象）：被代理类
* Proxy（代理）：生成的代理对象
* Joinpoint（连接点）：可以被拦截增强的方法
* Pointcut（切入点）：真正被拦截增强的方法
* Advice（通知/增强）：增强的业务逻辑
* Aspect（切面）：是切入点和通知的结合过程
* Weaving（织入）：把增强应用到目标对象来创建新的代理对象的过程
```



## 3.4 AOP开发明确事项

### 3.4.1 开发阶段

1. 编写核心业务代码（目标类的目标方法）           **切入点**
2. 把公用代码抽取出来，制作成通知（包含了增强业务功能方法）        **通知**
3. 在配置文件中，声明切入点和通知间的关系，即切面。          



### 3.4.2 运行阶段（Spring框架完成的）

​	Spring框架会监控切入点方法的执行。一旦切入点方法运行，采用代理机制，动态地创建目标对象的代理对象，根据通知类型，在代理对象的对应位置，将通知对应的功能织入，

完成完整代码的运行。



### 3.4.3 底层代理实现

在 Spring中，框架会根据**目标类是否实现了接口**来决定采用哪种动态代理的方式生成代理对象

* 当Bea**n实现接口时**，采用**JDK代理**
* 当Bean**没有实现接口**，**采用cglib代理**（可以在核心配置文件中加入<aop:aspectj-
  autoproxy proxyt-target-class=”true”/>标签，强制使用cglib动态代理）



## 3.5 知识小结

```markdown
* aop：面向切面编程

* aop底层实现：基于JDK动态代理和基于CGLIB动态代理
	根据目标类是否实现接口，来决定使用哪种代理
	
* aop的重点：
	Pointcut（切入点）：真正被增强的方法
	Advice（通知/增强）：增强的业务逻辑
	Aspect（切面）：切点+通知
	Weaving（织入）：将切点和通知结合，并产生代理对象的这个过程
```





# 四、基于XML的AOP开发

## 4.1 快速入门

**步骤分析：**

```markdown
1. 创建java项目，导入AOP依赖
2. 创建目标接口和目标实现类（定义切入点）
3. 创建通知类及方法（定义通知）
4. 将目标类和通知类对象创建权交个Spring
5. 在核心配置文件中配置织入关系，及切面，让通知和切点产生关系
6. 编写测试代码
```

### 4.1.1 创建java项目，导入依赖

```xml
<dependencies>
        <!--导入spring的context坐标，context依赖aop-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <!-- aspectj的织入（切点表达式需要用到该jar包） -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <!--spring整合junit-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
```



### 4.1.2 创建目标接口和目标实现类	定义切入点

```java
public interface AccountService {

    /*
        目标方法：（切入点：要进行拦截增强的方法）
     */
    public void transfer();
}
```

```java
public class AccountServiceImpl implements AccountService {

    /*
        目标方法：（切入点：要进行拦截增强的方法）
     */
    @Override
    public void transfer() {
        System.out.println("转账方法执行了...");
        //System.out.println(1/0);
    }
}
```



### 4.1.3 创建通知类

```java
/*
    通知类
 */
public class MyAdvice {


    /*
        前置通知所对应的方法
     */
    public void before(){

        System.out.println("前置通知执行了...");
    }
    }
```



### 4.1.4 将目标类和通知类对象创建权交给Spring

```xml
<!--目标类交给IOC容器-->
    <bean id="accountServcie" class="com.lagou.service.impl.AccountServiceImpl"/>

    <!--通知类交给IOC容器-->
    <bean id="myAdvice" class="com.lagou.advice.MyAdvice"/>
```



### 4.1.5 在核心配置文件中配置织入关系，及切面

导入aop命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd">

<!--    基于XML的AOP开发-->



    <!--目标类交给IOC容器-->
    <bean id="accountServcie" class="com.lagou.service.impl.AccountServiceImpl"/>

    <!--通知类交给IOC容器-->
    <bean id="myAdvice" class="com.lagou.advice.MyAdvice"/>




<!--    AOP织入配置

            表示将用到myAdvice里的before方法来对 pointcut="" 这个表达式所匹配到的切点方法，目标方法进行 aop:before 前置增强

-->
<aop:config>
<!--    配置切面：切入点+通知
            ref="myAdvice"：引用通知类为myAdvice这个类
-->


<!--切面配置    ref：就是引用通知类，包含增强业务逻辑的类-->

    <aop:aspect ref="myAdvice">
<!--        method=""：表示要用到该通知类里面的哪个方法作为增强方法
            pointcut=""：配置切入点
                配置目标类的transfer方法执行时，会使用myAdvice类中的before方法做一个前置增强
                    就是在transfer方法执行之前 myAdvice里面的before方法要优先于transfer方法之前执行
-->
      <aop:before method="before" pointcut="execution(public void
com.lagou.service.impl.AccountServiceImpl.transfer())"/>
    </aop:aspect>
</aop:config>



</beans>
```



### 4.1.6 编写测试代码

```java
// Spring整合Junit
// 替换运行器
@RunWith(SpringJUnit4ClassRunner.class)
// 指定核心配置文件所在的路径
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountServiceImplTest {

    // 注入实例对象
    @Autowired
    private AccountService accountService;

    @Test
    public void testTransfer(){
        accountService.transfer();
    }
}
```





## 4.2 XML配置AOP详解

### 4.2.1 切点表达式

表达式语法：

```
execution([修饰符] 返回值类型 包名.类名.方法名(参数类型))

execution(public void com.lagou.service.impl.AccountServiceImpl.transfer())
```

* 访问修饰符可以省略
* 返回值类型、包名、类名、方法名可以使用星号*代替，代表任意
* 包名与类名之间一个点 . 代表当前包下的类，两个点 .. 表示当前包及其子包下的类
* 参数列表可以使用两个点 .. 表示任意个数，任意类型的参数列表



例如：

```
- 表达式中的访问修饰符可省略
      execution(void com.lagou.service.impl.AccountServiceImpl.transfer(java.lang.String))

      - 返回值类型、包名、类名、方法名可以使用星号 * 代替，代表任意      任意包下的任意类的 无参方法
      execution(* *.*.*.*.*.*())

      - 包名和类名之间一个点 . 代表当前包下的类， 两个点 .. 表示当前包及其子包下的类
       任意包下及其子包下的任意类  .. 表示该包下的所有子包
      execution(* *..*.*())

      - 参数列表可以使用两个点 ..       表示任意个数，任意类型的参数列表
      表示匹配任意返回值类型，任意包下的任意类的任意方法
      execution(* *..*.*(..))       表示任意个数，任意类型的参数
```



**切点表达式抽取**

​	当多个增强的切点表达式相同时，可以将切点表达式进行抽取，在增强中使用 pointcut-ref 属性来引用抽取后的切点表达式

```xml
<aop:config>
  <!--  抽取切点表达式
            把重复的代码进行抽取，方便维护
        -->
    <aop:pointcut id="myPointcut" expression="execution(* com.lagou.service.impl.AccountServiceImpl.*(..))"/>
    
  <aop:aspect ref="myAdvice">
    <aop:before method="before" pointcut-ref="myPointcut"/>
   <aop:after-returning method="afterReturning" pointcut-ref="myPointcut"/>
  </aop:aspect>
</aop:config>
```



### 4.2.2 通知类型

通知的配置语法：

```xml
<aop:通知类型 method=“通知类中方法名” pointcut=“切点表达式"></aop:通知类型>
```

可以配置的通知类型：（笔试）

| 名称     | 标签                 | 说明                                       |
| -------- | -------------------- | ------------------------------------------ |
| 前置通知 | <aop:before>         | 指定增强方法在切入点方法之前执行           |
| 后置通知 | <aop:afterReturning> | 指定增强方法在切入点方法之后执行           |
| 异常通知 | <aop:afterThrowing>  | 指定切入点方法出现异常时执行               |
| 最终通知 | <aop:after>          | 无论切入点方法执行时是否有异常，都会执行   |
| 环绕通知 | <aop:around>         | 在代码中可以手动控制增强代码在什么时候执行 |

**注意：通常情况下，环绕通知都是独立使用的**

```java
/*
        环绕通知所对应的方法
            环绕通知是一种可以通过手动控制增强代码在什么时候执行的通知

                环绕通知是Spring提供的一种手动可以通过代码控制增强业务逻辑执行顺序的方式
     */
    // Proceeding JoinPoint（切点对象）  ：正在执行的连接点：切点
    public Object around(ProceedingJoinPoint pjp){

        // 切点方法执行
        Object proceed = null;
        // 手动通过代码的方式去控制执行顺序
        try {
            System.out.println("前置通知执行了");
            proceed = pjp.proceed();
            System.out.println("后置通知执行了");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知执行了");
        } finally {
            System.out.println("最终通知执行了");
        }

        return proceed;
    }
```





## 4.3 知识小结

```markdown
* aop织入配置
	<aop:config>		aop配置
    	<aop:aspect ref=“通知类”>   	切面配置
          <aop:before method=“通知方法名称” pointcut=“切点表达式"/>
    	</aop:aspect>
 	 </aop:config>
 	 
 	 
* 通知的类型
	前置通知、后置通知、异常通知、最终通知
	环绕通知
	
* 切点表达式语法
	execution([修饰符] 返回值类型 包名.类名.方法名(参数))
	
	简化写：修饰符可以省略，返回值类型、包名、类名、方法名 都可以使用*代替。
		参数写成.. 表示匹配任意类型任意个数的参数 的方法
```





# 五、基于注解的AOP开发

## 5.1 快速入门

**步骤分析：**

```markdown
1. 创建java项目，导入依赖
2. 创建目标接口和目标实现类（定义切入点）
3. 创建通知类（定义通知）
4. 将目标类和通知类对象创建权交给Spring
5. 在通知类中使用注解配置织入关系，升级为切面类
6. 在配置文件中开启组件扫描和 AOP的自动代理
7. 编写测试代码
```



### 5.1.1 创建java项目，导入依赖

```xml
<dependencies>
        <!--导入spring的context坐标，context依赖aop-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <!-- aspectj的织入（切点表达式需要用到该jar包） -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <!--spring整合junit-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
```



### 5.1.2 创建目标接口和目标实现类

```java
public interface AccountService {

    // 转账方法
    public void transfer();
}
```

```java
@Service        // 生成该类实例存到IOC容器中
public class AccountServiceImpl implements AccountService {

    // 切入点方法
    @Override
    public void transfer() {
        System.out.println("转账方法执行了...");
    }
}
```



### 5.1.3 创建通知类

```java
/*
    通知类
 */
@Component      // 将该类创建权交给Spring
public class MyAdvice {
  public void before() {
    System.out.println("前置通知...");
 }
}
```



### 在通知类中使用注解配置织入关系，升级为切面类

```java
/*
    通知类
 */
@Component      // 将该类创建权交给Spring
// 使用@Aspect注解：标注切面类。Spring会 根据 @Aspect 这个注解 帮助我们完成织入增强，并且生成代理

// 使用@Before等注解  来标注通知方法
    // execution(切入点表达式)    对AccountServiceImpl 这个类里面的任意类型，任意个数的参数 的方法进行增强

    // 借助注解进行织入。表示要使用before这个方法 来对这个切点方法进行前置增强

    @Before("execution(* com.lagou.service.impl.AccountServiceImpl.*(..))")
    public void before(){
       System.out.println("前置通知执行了...");
   }
}
```



### 在配置文件中开启组件扫描和 AOP的 自动代理

```xml
<!--    想要让IOC注解生效的话 就要     开启IOC注解扫描
            扫描com.lagou 该包及其子包下所有的类上面所标注的注解。使注解生效
-->
   <context:component-scan base-package="com.lagou"/>


<!--    开启  AOP的自动代理            配置aop自动代理：   aop:aspectj-autoproxy
            Spring会采用动态代理完成织入增强，并且生成代理      proxy-target-class="true"：表示强制使用cglib动态代理
                会对标注了@Aspect  这个注解的类进行 织入，并生成对应的代理
-->
  <aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
```



### 测试

```java
// 替换运行器，把当前Junit的运行环境指定为Spring环境
@RunWith(SpringJUnit4ClassRunner.class)
// 加载核心配置类
@ContextConfiguration("classpath:applicationContext.xml")   // 指定核心配置文件路径
public class AccountServiceTest {

    // 要调用AccountServiceImpl 中的transfer方法
    // 所以注入该对象
    // 要注入由Spring为我们生成的代理对象，所以要用共同的类型  也就是接口类型接收，不能使用实现类型接收。因为实际注入的是proxy
    @Autowired
    private AccountService accountService;

    @Test
    public void testTransfer(){

        accountService.transfer();
    }
}
```





## 5.2 注解配置AOP详解

### 5.2.1 切点表达式

切点表达式的抽取

```java
/*
    通知类
 */
@Component      // 将该类创建权交给Spring
// 使用@Aspect注解：标注切面类。Spring会 根据 @Aspect 这个注解 帮助我们完成织入增强，并且生成代理

@Aspect         // 升级为切面类：配置切入点和通知的关系
public class MyAdvice {

    // 使用 @Pointcut注解的方式来对切点表达式进行抽取
    // 在当前通知类中 添加方法，在方法上借助 @Pointcut 这个注解，把切点表达式进行抽取

    // 在@Pointcut 这个注解中配置要去抽取的表达式
    // 相当于把重复用到的切点表达式抽取到注解中
    @Pointcut("execution(* com.lagou.service.impl.AccountServiceImpl.*(..))")
    public void myPointcut(){

    }


    // 使用@Before等注解  来标注通知方法
    // execution(切入点表达式)    对AccountServiceImpl 这个类里面的任意类型，任意个数的参数 的方法进行增强

    // 借助注解进行织入。表示要使用before这个方法 来对这个切点方法进行前置增强

    // @Before("MyAdvice.myPointcut()"):相当于引用了 抽取的切点表达式
    // 引用的时候，就写     类名.方法名
    @Before("MyAdvice.myPointcut()")
    public void before(){
        System.out.println("前置通知执行了...");
    }
```



### 5.2.2 通知类型

通知的配置语法：@通知注解("切点表达式")

| 名称     | 标签            | 说明             |
| -------- | --------------- | ---------------- |
| 前置通知 | @Before         | 用于配置前置通知 |
| 后置通知 | @AfterReturning | 用于配置后置通知 |
| 异常通知 | @AfterThrowing  | 用于配置异常通知 |
| 最终通知 | @After          | 用于配置最终通知 |
| 环绕通知 | @Around         | 用于配置环绕通知 |

**原生注解存在执行顺序的问题，注意：**

​	当前四个通知组合在一起时，执行顺序如下：

​	@Before -> @After -> @AfterReturning (如果有异常：@AfterThrowing)



环绕通知

```java
// 当使用 环绕通知时，该通知要单独使用
    // 使用环绕通知，可以手动控制通知的执行顺序
    // 所以非要使用注解AOP开发的话，因为原生注解存在执行顺序的问题。
    // 那么就可以通过环绕通知来进行解决，手动在代码中控制通知的执行顺序
    @Around("MyAdvice.myPointcut()")
    public Object around(ProceedingJoinPoint pjp){


        // 切点对象调用方法，让目标方法执行
        Object proceed = null;
        try {
            System.out.println("前置通知执行了...");
            proceed = pjp.proceed();
            System.out.println("后置通知执行了...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知执行了...");
        } finally {
            System.out.println("最终通知执行了...");
        }

        return proceed;
    }
```



### 5.2.3 AOP的纯注解配置

```java
/**     基于注解的AOP开发
 * 在核心配置类中配置注解把 applicationContext.xml 配置文件替换掉
 *      使用核心配置类把 核心配置文件 替换掉
 */

// 让该类变成核心配置类
@Configuration
// 替换 开启IOC注解扫描的 注解
@ComponentScan("com.lagou")
// 替换 开启AOP的自动代理的标签  的注解
@EnableAspectJAutoProxy     //  开启了AOP的自动代理    替代了<aop:aspectj-autoproxy> 这个标签
public class SpringConfig {
}
```





## 5.3 知识小结

```markdown
* 使用@Aspect注解，标注切面类。Spring会根据这个注解完成织入增强，并生成代理对象

* 使用@Before等注解，标注通知方法

* 使用@Pointcut注解，抽取切点表达式

* 配置aop自动代理：
		使用xml方式就添加 <aop:aspectj-autoproxy/> 这个标签
		使用注解配置就在核心配置类上添加@EnableAspectJAutoProxy 这个注解
```





# 六、AOP优化转账案列

​	使用Spring的AOP替换掉动态代理，完成对转账案列的优化，降低业务代码和事务控制的耦合度

​	

**要确定的**

* 哪个方法为切入点
* 哪个方法为通知
* 切面如何配置



## 6.1 xml配置实现优化转账案列

### 1）配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">

<!--    开启注解扫描，使注解生效-->
    <context:component-scan base-package="com.lagou"></context:component-scan>

<!--引入properties文件
        location：指定文件路径，要加上classpath:       在核心配置文件中引入其他配置文件前面都要加classpath:
-->
<context:property-placeholder location="classpath:jdbc.properties"/>

<!--配置DataSource 数据源对象-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--        配置数据库信息-->
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

<!--    配置QueryRunner-->

<bean id="qr" class="org.apache.commons.dbutils.QueryRunner">
<!--    在构建QueryRunner时，要使用有参方式注入DataSource数据源对象-->
    <constructor-arg name="ds" ref="dataSource"></constructor-arg>
</bean>



<!--    借助Spring的AOP 优化转账案列，实现了业务代码和事务控制代码的分离，降低耦合度-->



<!--AOP配置编写时要

    确定切入点方法，通知类，通知方法

        在核心配置文件中借助标签进行切面配置
-->


<!--   基于XML方式， 进行AOP配置-->
   <aop:config>
<!--        1.配置切点表达式-->
<!--                expression="execution(* com.lagou.service.impl.AccountServiceImpl.*(..))"：切点表达式：-->
<!--                    execution(返回值类型 AccountServiceImpl类的全路径.方法名 )   任意名称的方法，任意参数类型，任意个数类型-->
<!--                        不管方法的参数类型是什么，参数个数有多少个，都进行通配-->

      <aop:pointcut id="myPointcut" expression="execution(* com.lagou.service.impl.AccountServiceImpl.*(..))"/>

<!--       2.切面配置-->
<!--                ref="transactionManager"：引用通知类-->

       <aop:aspect ref="transactionManager">
<!--            配置前置通知方法   开启事务     pointcut-ref="myPointcut"：引用配置的切点表达式-->
          <aop:before method="beginTransaction" pointcut-ref="myPointcut"/>
<!--            配置后值通知方法   提交事务-->
           <aop:after-returning method="commit" pointcut-ref="myPointcut"/>
<!--            配置异常通知      回滚事务-->
           <aop:after-throwing method="rollback" pointcut-ref="myPointcut"/>
<!--            配置最终通知      释放资源-->
           <aop:after method="release" pointcut-ref="myPointcut"/>
       </aop:aspect>
    </aop:config>

</beans>
```



### 2）事务管理器（通知）

```java
/*
    事务管理器工具类：包含：开启事务、提交事务、回滚事务、释放资源方法
        通知类
 */
@Component("transactionManager")      // 生成该类的实例存到IOC容器中
public class TransactionManager {

    @Autowired  // 注入
    private ConnectionUtils connectionUtils;


    /*
        开启事务
     */
    public void beginTransaction(){

        // 获取Connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            // 开启了一个手动事务，关闭自动提交
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        提交事务
     */
    public void commit(){

        // 获取的是同一个connection对象
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        回滚事务
     */
    public void rollback(){

        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        释放资源
     */
    public void release(){

        // 将手动事务改回成自动提交事务
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.setAutoCommit(true);

            // 将连接归还到连接池
            connectionUtils.getThreadConnection().close();
            // 解除线程绑定
            connectionUtils.removeThreadConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}	
```





## 6.2 注解配置实现优化转账案列

### 1）配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">

<!--    开启注解扫描，使注解生效-->
    <context:component-scan base-package="com.lagou"></context:component-scan>

<!--引入properties文件
        location：指定文件路径，要加上classpath:       在核心配置文件中引入其他配置文件前面都要加classpath:
-->
<context:property-placeholder location="classpath:jdbc.properties"/>

<!--配置DataSource 数据源对象-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--        配置数据库信息-->
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

<!--    配置QueryRunner-->

<bean id="qr" class="org.apache.commons.dbutils.QueryRunner">
<!--    在构建QueryRunner时，要使用有参方式注入DataSource数据源对象-->
    <constructor-arg name="ds" ref="dataSource"></constructor-arg>
</bean>
</beans>
```



### 2）事务管理器（通知）

```java
/*
    事务管理器工具类：包含：开启事务、提交事务、回滚事务、释放资源方法
        通知类
 */

// 使用注解配置AOP方式实现   对转账案列进行优化
@Aspect         // 表明该类为切面类，Spring会为该类实现织入增强，并且生成对应的代理对象
@Component("transactionManager")      // 生成该类的实例存到IOC容器中
@EnableAspectJAutoProxy               // 使用注解方式 开启AOP自动代理
public class TransactionManager {

    @Autowired  // 注入
    private ConnectionUtils connectionUtils;

    /*
        完成切入点和通知的结合
            使用环绕通知方式完成对案列的优化
                使用环绕通知可以借助代码手动控制增强逻辑的执行顺序
     */
    // @Around：环绕注解方式，写切入点表达式
    @Around("execution(* com.lagou.service.impl.AccountServiceImpl.*(..)))")
    // 表示使用该方法对 AccountServiceImpl 里面的任意方法进行环绕通知增强
    // 参数：正在执行的连接点，就是切入点
    public Object around(ProceedingJoinPoint pjp) throws SQLException {

        // 手动控制增强逻辑的执行顺序，就是事务控制相关代码


        // 在调用原方法执行进行事务增强，就是事务控制相关代码
        Object proceed = null;
        try {

            // 事务的开启       开启手动事务
            connectionUtils.getThreadConnection().setAutoCommit(false);

            // 调用原方法        切入点方法执行
            proceed = pjp.proceed();

            // 手动提交事务
            connectionUtils.getThreadConnection().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            // 产生了异常，手动回滚事务
            connectionUtils.getThreadConnection().rollback();
        } finally {
            // 最终执行的 释放资源方法

            // 将手动事务恢复成自动事务
            connectionUtils.getThreadConnection().setAutoCommit(true);

            // 将连接归还到连接池
            connectionUtils.getThreadConnection().close();
            // 解除线程绑定
            connectionUtils.removeThreadConnection();
        }

        return proceed;
    }
}
```

