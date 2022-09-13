# 任务三：实现SSM整合并完成对Account表的增删改查

# 1.1 需求和步骤分析

**需求：**

​	使用ssm框架完成对account表的增删改查操作

**步骤分析：**

```markdown
1. 准备数据库和表记录

2. 创建web项目

3. 编写mybatis在ssm环境中可以单独使用

4. 编写spring在ssm环境中可以单独使用

5. spring整合mybatis

6. 编写springMVC在ssm环境中可以单独使用

7. spring整合springmvc
```



# 1.2 环境搭建

## 1）准备数据库和表记录

```sql
CREATE DATABASE `spring_db` CHARACTER SET utf8;
USE `spring_db`;
CREATE TABLE `account` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(32) DEFAULT NULL,
`money` DOUBLE DEFAULT NULL,
PRIMARY KEY (`id`)
) ;
INSERT INTO account(id,NAME,money) VALUES(1,'tom',1000),(2,'jerry',1000)
```

## 2）创建web项目





# 1.3 编写mybatis在ssm环境中可以单独使用

​	需求：基于mybatis先来实现对account表的查询

## 1）相关坐标

```xml
<!--    mybatis坐标-->
<!--    mysql驱动-->
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
<!--        druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.15</version>
        </dependency>
<!--        mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.1</version>
        </dependency>
<!--        Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
```



## 2）Account实体

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



## 3）AccountDao接口

```java
/*
    Dao层接口
 */
public interface AccountDao {

    /*
        查询所有账户
     */
    public List<Account> findAll();
}
```



## 4）AccountDao.xml 映射

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace：命名空间的值要和接口的全路径一致-->
<mapper namespace="com.lagou.dao.AccountDao">
<!--    查询所有账户
            id和接口里的方法名保持一致
-->
    <select id="findAll" resultType="account">
        select * from account
    </select>
</mapper>
```



## 5）mybatis核心配置文件

jdbc.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///spring_db?useSSL=false&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
```

SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis核心配置文件-->
<configuration>

<!--加载properties文件-->
    <properties resource="jdbc.properties"></properties>

<!--    类型别名配置-->
<typeAliases>
    <package name="com.lagou.domain"/>-->
</typeAliases>



<!--数据源-->
<!--    运行环境-->
  <environments default="dev">
<!--       开发运行环境-->
       <environment id="dev">
<!--           事务管理器-->
           <transactionManager type="JDBC"></transactionManager>
<!--          使用连接池-->
           <dataSource type="POOLED">
<!--               数据库的相关信息-->
              <property name="driver" value="${jdbc.driver}"/>
              <property name="url" value="${jdbc.url}"/>
              <property name="username" value="${jdbc.username}"/>                   <property name="password" value="${jdbc.password}"/>
           </dataSource>
       </environment>
    </environments>


<!--    加载映射-->
<mappers>
<!--    批量加载，写当前接口所在的包，因为当前的映射文件和接口处在同包同名的状态的，配置接口所在的包就ok-->
   <package name="com.lagou.dao"/>
</mappers>

</configuration>
```



## 6）测试

```java
public class MybatisTest {

    @Test
    public void testMybatis() throws IOException {

        // 加载核心配置文件，获取输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 借助Mybatis的API通过sqlSession.getMapper 获取mapper接口代理对象
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> all = mapper.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
        // 释放资源
        sqlSession.close();
    }

}
```





# 1.4 编写spring在ssm环境中可以单独使用

## 1）相关坐标

```xml
<!--        spring依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
<!--切点表达式-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.3</version>
        </dependency>
<!--       JdbcTemplate和 事务管理器-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
<!--        spring整合junit-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
```



## 2）AccountService接口

```java
/*
    service层接口
 */
public interface AccountService {

    public List<Account> findAll();
}
```



## 3）AccountServiceImpl实现类

```java
/*
    service层实现类
 */
@Service("accountService")      // 生成该类的实例对象存到IOC
public class AccountServiceImpl implements AccountService {


    /*
        测试Spring在ssm环境中的单独使用
     */
    @Override
    public List<Account> findAll() {
        System.out.println("findAll执行了....");
        return null;
    }
}
```



## 4）spring核心配置文件

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">


<!--    配置IOC相关操作：开启注解扫描，service层-->
<context:component-scan base-package="com.lagou.service"/>
    
</beans>
```



## 5）测试

```java
@RunWith(SpringJUnit4ClassRunner.class) // 替换运行环境为Spring环境
@ContextConfiguration("classpath:applicationContext.xml")         // 加载Spring核心配置文件路径
public class SpringTest {

    @Autowired      // 根据类型完成自动注入
    private AccountService accountService;

    @Test
    public void testSpring(){

        accountService.findAll();
        }
    }
}
```





# 1.5 spring整合mybatis

## 1）整合思想

​	将mybatis接口代理对象的创建权交给spring管理，我们就可以把dao的代理对象注入到service中，此时也就完成了spring与mybatis的整合了

## 2）导入整合包

```xml
<!--        mybatis整合spring坐标-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.1</version>
        </dependency>
```

## 3）spring配置文件管理mybatis

注意：此时可以将mybatis主配置文件删除

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">


<!--    配置IOC相关操作：开启注解扫描，service层-->
<context:component-scan base-package="com.lagou.service"/>

<!--    引入properties文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

<!--spring整合mybatis开始...................
    主要配置三个bean标签，把以前写在SqlMapperConfig.xml 中的配置信息都转移到Spring的核心配置文件里
    这样只加载applicationContext.xml 就ok了。那么当前所有的代理对象都由Spring创建管理存到IOC中


        SqlSessionFactoryBean在创建时可以设置一些属性，设置DataSource。
        SqlSessionFactory 工厂对象 是用来生产SqlSession会话对象的，而SqlSession会话对象要与数据库进行交互。
        所以在创建SqlSessionFactory 时要指定数据源信息

-->

<!--    所以先来配置数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

<!--sqlSessionFactory的创建权交给了spring  生产sqlSession了-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
<!--   引入dataSource数据源对象-->
        <property name="dataSource" ref="dataSource"/>
<!--        批量起别名，扫描包，为包下的实体类起别名，别名就是类名，不区分大小写-->
        <property name="typeAliasesPackage" value="com.lagou.domain"/>
<!--       引入加载mybatis的核心配置文件，可以不用去加载
            加载spring的核心配置文件的同时，把mybatis核心配置文件也加载-->
        <!--<property name="configLocation" value="classpath:SqlMapConfig.xml"/>-->
    </bean>

<!--    mapper映射扫描  MapperScannerConfigurer：扫描该包下的所有接口，生成对应的代理对象存到IOC容器中
            MapperScannerConfigurer：就是对接口进行扫描，并且生成该接口的代理对象存到IOC中
-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
<!--    接口所在的包路径-->
    <property name="basePackage" value="com.lagou.dao"/>
</bean>
<!--spring整合mybatis结束......................-->


</beans>
```



## 4）修改AccountServiceImpl

```java
/*
    service层实现类
 */
@Service("accountService")      // 生成该类的实例对象存到IOC
public class AccountServiceImpl implements AccountService {

    // 需要用到AccountDao的代理对象的
    @Autowired      // 注入生成的Dao 代理对象
    private AccountDao accountDao;


    /*
       	Spring整合mybatis，调用dao层方法
     */
    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }
```



## 5）测试

```java
@RunWith(SpringJUnit4ClassRunner.class) // 替换运行环境为Spring环境
@ContextConfiguration("classpath:applicationContext.xml")         // 加载Spring核心配置文件路径，并整合了mybatis
public class SpringTest {

    @Autowired      // 根据类型完成自动注入
    private AccountService accountService;

    @Test
    public void testSpring(){

        // service调用Dao，从而执行对account表的查询所有操作，实现了Spring整合mybatis
        List<Account> all = accountService.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }
}
```





# 1.6 编写springMVC在ssm环境中可以单独使用

需求：访问到controller里面的方法查询所有账户，并跳转到list.jsp 页面进行列表展示

## 1）相关坐标

```xml
<!--SpringMVC坐标-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
<!--        servlet-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
<!--        jsp-->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
            <scope>provided</scope>
        </dependency>
        <!--引入jstl-->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
```



## 2）导入页面资源

## 3）在web.xml配置前端控制器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">


<!--    配置前端控制器-->
<servlet>
    <servlet-name>DispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--    初始化参数，servlet初始化时区加载SpringMVC核心配置文件-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
<!--    在服务器启动时对该servlet进行实例化，并且执行初始化方法-->
    <load-on-startup>2</load-on-startup>
</servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
<!--        拦截所有Controller类里的映射方法，除了静态资源-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>



<!--    中文乱码过滤器：解决POST方式提交的乱码问题，GET方式乱码问题已经被Tomcat8.5以上版本解决了-->
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
<!--    配置初始化参数，设置encoding 编码方式为utf-8-->
<init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
</init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
<!--    对所有资源进行拦截-->
    <url-pattern>/*</url-pattern>
</filter-mapping>
</web-app>
```



## 4）AccountController 和 list.jsp

```java
@Controller     // 生成该类实例对象存到IOC容器中
@RequestMapping("/account")   // 一级目录
public class AccountController {


    /*
        查询所有用户
     */
    @RequestMapping("/findAll")
    public String findAll(Model model){

        List<Account> list = new ArrayList<>();
    list.add(new Account(1,"张三",1000d));
    list.add(new Account(2,"李四",1000d));

        // 把封装好的list存到Model中
        model.addAttribute("list",list);

        // 跳转页面
        return "list";
    }
}
```

```jsp
<%--              要对模型中的list遍历      var="account"：表示现在遍历的对象--%>
              <c:forEach items="${list}" var="account">

              <tr>
                  <td>
                      <input type="checkbox" name="ids" value="${account.id}">
                  </td>
<%--                  动态获取，当前遍历对象里面的id属性值--%>
                  <td>${account.id}</td>
                  <td>${account.name}</td>
                  <td>${account.money}</td>
                  <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/account/findById?id=${account.id}">修改</a>&nbsp;<a class="btn btn-default btn-sm" href="">删除</a></td>
              </tr>
              </c:forEach>
```



## 5）springmvc核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/mvc
		http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
">


<!--    1.配置组件扫描：只扫描controller-->
<context:component-scan base-package="com.lagou.controller"/>


<!--    2.mvc注解增强：处理器映射器及处理器适配器，增强JSON的读写-->
<mvc:annotation-driven/>


<!--   3.视图解析器，当返回逻辑视图时，拼接前缀和后缀-->
<bean id="resourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
<!--    配置前缀-->
    <property name="prefix" value="/"/>
    <property name="suffix" value=".jsp"/>
</bean>



<!--    4.放行静态资源     全部的-->
<mvc:default-servlet-handler/>
    
</beans>
```





# 1.7 spring整合springmvc

## 1）整合思想

​	spring和springmvc其实根本不用整合，本来就是一家人

​	但是我们需要做到spring和web容器整合，让web容器启动时自动加载spring配置文件，web容器销毁的时候spring的ioc也销毁

## 2）spring和web容器整合

**ContextLoaderListener加载【掌握】**

​	可以使用spring-web包中的**ContextLoaderListener 监听器，去监听servletContext容器的创建和销毁，来同时创建或销毁IOC容器**。因为servletContext是项目启动时，对象就创建，所以可以让servletContext创建时去加载spring核心配置文件同时创建IOC，而项目关闭时，servletContext对象销毁，同时让它销毁ioc

```xml
<!--    spring 与 web容器整合
            相当于完成了spring整合SpringMVC，实现spring整合 web容器就ok了
-->

<!--    配置Spring的监听器
            监听ServletContext对象的创建与销毁
                当ServletContext对象创建时，需要去加载applicationContext.xml 核心配置文件，同时构建IOC容器
-->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!--    配置全局参数
            指定applicationContext.xml 核心配置文件的路径
                这样就可以借助ContextLoaderListener 这个监听器在服务器启动时去加载解析Spring核心配置文件，同时构建IOC
-->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
```



## 3）修改AccountController

```java
@Controller     // 生成该类实例对象存到IOC容器中
@RequestMapping("/account")   // 一级目录
public class AccountController {


    // controller调用service，service调用dao
    @Autowired      // 注入
    private AccountService accountService;

    /*
        查询所有用户
     */
    @RequestMapping("/findAll")
    public String findAll(Model model){

        // 实现查询所有账户信息
        List<Account> list = accountService.findAll();

        // 把封装好的list存到Model中
        model.addAttribute("list",list);

        // 跳转页面
        return "list";
    }
}
```





# 1.8 spring配置声明式事务

## 1）spring配置文件加入声明式事务

```xml
<!--    spring的声明式事务-->
<!--1.配置事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--    注入数据源对象-->
    <property name="dataSource" ref="dataSource"/>
</bean>


<!--  2.开启事务注解的支持  -->
<tx:annotation-driven/>
```

```java
/*
    service层实现类
 */
@Service("accountService")      // 生成该类的实例对象存到IOC
@Transactional      // 开启事务控制
public class AccountServiceImpl implements AccountService {
}
```



## 2）add.jsp

```jsp
<form action="${pageContext.request.contextPath}/account/save" method="post">
                <div class="form-group">
                    <label for="name">姓名：</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">
                </div>
                      <div class="form-group">
                    <label for="money">余额：</label>
                    <input type="text" class="form-control" id="money" name="money" placeholder="请输入余额">
                </div>

                <div class="form-group" style="text-align: center">
                    <input class="btn btn-primary" type="submit" value="提交" />
                    <input class="btn btn-default" type="reset" value="重置" />
                    <input class="btn btn-default" type="button" onclick="history.go(-1)" value="返回" />
                </div>
            </form>
```



## 3）AccountController

```java
@RequestMapping("/save")
    // 接收前台的请求参数，直接封装成Account对象
    public String save(Account account){

        // 调用service层的save方法，完成对账户的保存
        accountService.save(account);
        // 跳转到findAll方法重新查询一次数据库进行数据的遍历展示
        // 跳转account这个controller类里面findAll方法完成重新查询所有账户，完成重定向
        return "redirect:/account/findAll";
    }
```



## 4）AccountService接口和实现类

```java
public void save(Account account);
```

```java
 /*
        账户添加
     */
    @Override
    public void save(Account account) {

        accountDao.save(account);
    }
```



## 5）AccountDao

```java
void save(Account account);
```



## 6）AccountDao.xml 映射

```xml
<!--添加账户   void save(Account account);-->
    <insert id="save" parameterType="account">
<!-- 占位符里的值和account实体中的属性名保持一致，调用get方法 -->
        insert into account values(null,#{name},#{money})
    </insert>
```





# 1.9 修改操作

## 1.9.1 数据回显

### 1）AccountController

```java
/*
        根据ID查询账户信息，完成账户回显
     */
    @RequestMapping("/findById")
    public String findById(Integer id,Model model){

       Account account = accountService.findById(id);

       // 存到model中，进行数据回显
        model.addAttribute("account",account);

        // 视图跳转
        return "update";
    }
```



### 2）AccountService接口和实现类

```java
Account findById(Integer id);
```

```java
/*
        根据ID查询
     */
    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }
```



### 3）AccountDao接口和映射文件

```java
Account findById(Integer id);
```

```xml
<!--    根据ID查询账户信息  Account findById(Integer id);-->
    <select id="findById" parameterType="int" resultType="account">
        select * from account where id = #{id}
    </select>
```



### 4）update.jsp

```jsp
<form action="${pageContext.request.contextPath}/account/update" method="post">
                <input type="hidden" name="id" value="${account.id}">
                <div class="form-group">
                    <label for="name">姓名：</label>
                    <input type="text" class="form-control" id="name" name="name" value="${account.name}" placeholder="请输入姓名">
                </div>
                <div class="form-group">
                    <label for="money">余额：</label>
                    <input type="text" class="form-control" id="money" name="money"  value="${account.money}" placeholder="请输入余额">
                </div>

                <div class="form-group" style="text-align: center">
                    <input class="btn btn-primary" type="submit" value="提交" />
                    <input class="btn btn-default" type="reset" value="重置" />
                    <input class="btn btn-default" type="button" onclick="history.go(-1)" value="返回" />
                </div>
            </form>
```





## 1.9.2 账户更新

### 1）AccountController

```java
/*
        更新账户
     */
    @RequestMapping("/update")
    public String update(Account account){

        accountService.update(account);

        // 跳转到findAll方法进行账户信息的更新查询
        return "redirect:/account/findAll";
    }
```



### 2）AccountService接口和实现类

```java
void update(Account account);
```

```java
/*
        更新操作
     */
    @Override
    public void update(Account account) {
        accountDao.update(account);
    }
```



### 3）AccountDao接口和映射文件

```java
void update(Account account);
```

```xml
<!--    更新账户  void update(Account account);-->
    <update id="update" parameterType="account">
        update account set name = #{name},money = #{money} where id = #{id}
    </update>
```





# 1.10 批量删除

## 1）list.jsp

```js
<script>
   // 实现全选不选效果
    $('#checkAll').click(function () {
    // 让下面的复选框与第一个复选框的状态保持一致
    $('input[name="ids"]').prop('checked', $(this).prop('checked'));
 })
    
    
    /*给删除选中按钮绑定点击事件*/
    $('#deleteBatchBtn').click(function () {
            if(confirm('您确定要删除吗')){
                if($('input[name=ids]:checked').length > 0){
                    /*提交表单*/
                    $('#deleteBatchForm').submit();
                }

            }else {
                alert('想啥呢，没事瞎操作啥')
            }

    })

</script>
```



## 2）AccountController

```java
/*
        批量删除
     */

    @RequestMapping("/deleteBatch")
// 前台表单所提交的参数就是多条记录所对应的id值
// 参数名要和list.jsp 中的复选框的name值 保持一致
    public String deleteBatch(Integer[] ids){

        accountService.deleteBatch(ids);

        // 重新发起请求再去查询findAll方法，重新查询数据库的记录，进行展示
        return "redirect:/account/findAll";
    }
```



## 3）AccountService接口和实现类

```java
void deleteBatch(Integer[] ids);
```

```java
/*
        删除操作
     */
    @Override
    public void deleteBatch(Integer[] ids) {
        accountDao.deleteBatch(ids);
    }
```



## 4）AccountDao接口和映射文件

```java
void deleteBatch(Integer[] ids);
```

```xml
<!--      批量删除  void deleteBatch(Integer[] ids);  id in(1,2)-->
    <delete id="deleteBatch" parameterType="int">
        delete from account
        <where>
            <foreach collection="array" open="id in(" close=")" separator="," item="id">
            <!-- 通过占位符取到当前遍历元素的值 -->
                #{id}
            </foreach>
        </where>
    </delete>
```

