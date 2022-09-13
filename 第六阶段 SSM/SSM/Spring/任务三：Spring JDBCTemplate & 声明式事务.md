# 任务三：Spring JDBCTemplate & 声明式事务

# 一、Spring的jdbcTemplate

## 1.1 JdbcTemplate是什么？

​	JdbcTemplate是Spring框架中提供的一个模版对象，是对原始的JDBC API 的封装。相当于DBUtils里的 QueryRunner 核心对象。调用一些API方法，对数据库操作的



**核心对象**

```java
JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSource dataSource)
```



**核心方法**

```java
int update();	执行增、删、改语句

List<T> query();	查询多个
T queryForObject();	查询一个
    new BeanPropertyRowMapper<>();	实现ORM映射封装，也就是把查询结果记录通过这个对象封装成某个实体并返回
```



例：

​	查询数据库所有账户信息到Account实体中

```java
public class JdbcTemplateTest {
  @Test
  public void testFindAll() throws Exception {
    // 创建核心对象		并通过有参构造传入数据源对象
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

      // 编写sql
    String sql = "select * from account";
  
      // 执行sql
      List<Account> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>
(Account.class));
 }
}
```



## 1.2 Spring整合JdbcTemplate

**需求：**

​	基于Spring的xml配置实现账户的CRUD案列，借助JdbcTemplate实现 对Account的增删改查

**步骤分析**

```markdown
1. 创建java项目，导入依赖
2. 编写Account实体类
3. 编写AccountDao接口和实现类		使用JdbcTemplate对数据库CRUD
4. 编写AccountService接口和实现类	调用dao层方法
5. 编写Spring核心配置文件			编写Bean标签
6. 编写测试代码					 测试增删改查
```



### 1）创建java项目，导入依赖

```xml
<dependencies>
<!--        驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
<!--        Druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.15</version>
        </dependency>
<!--        spring全局上下文-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

<!--        AOP需要用到的切点表达式的jar-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>

<!--        spring_JDBCTemplate-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
<!--           事务的相关jar-->    
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

<!--        spring整合Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
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

    /*
        查询所有账户方法
     */
    public List<Account> findAll();

    /*
        根据ID查询账户
     */
    public Account findById(Integer id);

    /*
        添加账户
     */
    public void save(Account account);

    /*
        更新账户信息
     */
    public void update(Account account);

    /*
        根据ID删除账户
     */
    public void delete(Integer id);

}
```

```java
@Repository("accountDao")     // 采用注解形式生成该实例存到IOC容器中
public class AccountDaoImpl implements AccountDao {

    // 注入
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        查询所有账户
     */
    @Override
    public List<Account> findAll() {
        // 需要用到jdbcTemplate
        String sql = "select * from account";
        List<Account> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Account>(Account.class));

        return list;
    }

    /*
        根据id查询账户
     */
    @Override
    public Account findById(Integer id) {

        String sql = "select * from account where id = ?";
        // 占位符在解析时就会被替换成参数值
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class), id);

        return account;
    }

    /*
        添加账户
     */
    @Override
    public void save(Account account) {

        String sql = "insert into account values(null,?,?)";
        jdbcTemplate.update(sql,account.getName(),account.getMoney());
    }

    /*
        更新账户
     */
    @Override
    public void update(Account account) {
        String sql = "update account set money = ? where name = ?";
        jdbcTemplate.update(sql,account.getMoney(),account.getName());
    }

    /*
        根据ID删除账户
     */
    @Override
    public void delete(Integer id) {

        String sql = "delete from account where id = ?";
        jdbcTemplate.update(sql,id);

    }
}
```



### 4）编写AccountService接口和实现类

```java
public interface AccountService {

    /*
            查询所有账户方法
         */
    public List<Account> findAll();

    /*
        根据ID查询账户
     */
    public Account findById(Integer id);

    /*
        添加账户
     */
    public void save(Account account);

    /*
        更新账户信息
     */
    public void update(Account account);

    /*
        根据ID删除账户
     */
    public void delete(Integer id);
}
```

```java
@Service("accountService")    // 生成该类实例存到IOC容器中
public class AccountServiceImpl implements AccountService {

    // 在Service层中调用dao层方法
    // 所以需要注入dao层的对象
    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
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



### 5）编写核心配置文件

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


<!--    开启IOC注解扫描-->
<context:component-scan base-package="com.lagou"/>

<!--    引入properties文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>


<!--    配置dataSource 数据源对象
            先生成数据源的实例对象存到容器中，才能在创建JDBCTemplate时候，通过有参构造进行注入
-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
<!--        数据库连接配置信息-->
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>


<!--    配置jdbcTemplate		操作数据库的，所以当创建该对象时，要通过有参构造去传递一个数据源对象
            需要使用有参构造注入数据源对象
-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
<!--    通过有参构造注入配置好的数据源对象-->
        <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>


</beans>
```



### 6）编写测试

```java
// Spring整合Junit
// 替换运行环境为Spring环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定加载核心配置文件的路径
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountServiceImplTest {

    // 注入AccountService
    @Autowired
    private AccountService accountService;


    // 测试保存
    @Test
    public void testSave(){

        Account account = new Account();
        account.setName("lucy");
        account.setMoney(1000d);
        accountService.save(account);
    }


    // 测试查询所有
    @Test
    public void testFindAll(){

        List<Account> all = accountService.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }


    // 测试根据ID进行查询
    @Test
    public void testFindById(){

        Account account = accountService.findById(3);
        System.out.println(account);
    }


    // 测试账户修改
    @Test
    public void testUpdate(){

        Account account = new Account();
        account.setName("tom");
        account.setMoney(1000d);
        accountService.update(account);
    }


    // 测试根据ID删除账户
    @Test
    public void testDelete(){

        accountService.delete(3);
    }
}
```



## 1.3 实现转账案列

**步骤分析：**

```markdown
1. 创建java项目，导入依赖
2. 编写Account实体类
3. 编写AccountDao接口和实现类		采用JdbcTemplate对数据库操作
4. 编写AccountService接口和实现类	调用Dao层方法
5. 编写Spring核心配置文件
6. 编写测试
```



### 1）创建java项目，导入依赖

```xml
<dependencies>
        <!--        驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!--        Druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.15</version>
        </dependency>
        <!--        spring全局上下文-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>

        <!--        AOP需要用到的切点表达式的jar-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>

        <!--        spring_JDBCTemplate-->
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

        <!--        spring整合Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.5.RELEASE</version>
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

    /*
        减钱：转出操作
        	转出账户名和金额
     */
    public void out(String outUser,Double money);

    /*
        加钱：转入操作
     */
    public void in(String inUser,Double money);
}
```

```java
// dao层要被service层调用，service层要注入该实例对象
// 所以添加注解，生成该类实例存到IOC容器中
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    // 要借助jdbcTemplate模板对象调用API方法，完成对数据库的操作
    // 所以要注入
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void out(String outUser, Double money) {

        String sql = "update account set money = money - ? where name = ?";
        jdbcTemplate.update(sql,money,outUser);
    }

    @Override
    public void in(String inUser, Double money) {

        String sql = "update account set money = money + ? where name = ?";
        jdbcTemplate.update(sql,money,inUser);
    }
}
```



### 4）编写AccountService接口和实现类

```java
public interface AccountService {

    /*
        转账方法
            转出账户名、转入账户名和金额
     */
    public void transfer(String outUser,String inUser,Double money);
}
```

```java
// 在测试类中要使用AccountServiceImpl实例对象，从而调用transfer方法
// 所以添加注解生成实例存到IOC容器中
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    // 注入accountDao
    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String outUser, String inUser, Double money) {

        // 调用dao的out及in方法
        accountDao.out(outUser,money);

        // int i = 1/0;

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


<!--开启注解扫描
        因为当前在AccountDaoImpl和AccountServiceImpl 上都添加了对应的IOC注解
-->
    <context:component-scan base-package="com.lagou"/>

<!--    数据库的配置信息要抽取到properties文件中，所以要引入properties文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    

<!--    配置dataSource数据源对象-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>

<!--    配置jdbcTemplate
            jdbcTemplate模板对象在创建时需要用到有参构造来创建
                也就是创建该对象时需要通过有参构造来注入DataSource对象
-->

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
<!--    通过有参构造注入DataSource数据源对象-->
    <constructor-arg name="dataSource" ref="dataSource"/>
</bean>

</beans>
```



### 6）编写测试

```java
// 替换运行环境为Spring
@RunWith(SpringJUnit4ClassRunner.class)
// 指定加载核心配置文件路径
//@ContextConfiguration({"classpath:applicationContext.xml"})
// 加载核心配置类
@ContextConfiguration(classes = SpringConfig.class)
public class AccountServiceImplTest {

    // 注入AccountServiceImpl对象
    @Autowired
    private AccountService accountService;


    @Test
    public void testTransfer(){
        accountService.transfer("tom","jerry",100d);
    }
}
```





# 二、Spring的事务

## 2.1 Spring中的事务控制方式

​	Spring的事务控制可以分为编程式事务控制和声明式事务控制。

**编程式（了解）**

​	采用代码的方式，直接把事务代码和业务代码耦合在一起，实际不用

**声明式（重点）**

​	采用配置的方式，可以把事务代码和业务代码实现解耦合，AOP思想。通过AOP对方法进行增强，实现事务控制

---

**面试：Spring中的事务控制方式有几种？**

* 编程式事务控制：存在事务代码和业务代码耦合度高的情况，不用
* 声明式事务控制：就是采用配置的方式来实现事务控制，实现业务代码和事务代码解耦合

---



## 2.2 编程式事务控制相关对象【了解】

### 2.2.1 PlatformTransactionManager

​	PlatformTransactionManager是接口，**是Spring的事务管理器**，提供了常用的操作事务的方法。

| 方法                                                         | 说明                         |
| ------------------------------------------------------------ | ---------------------------- |
| TransactionStatus getTransaction(TransactionDefinition definition); | 开启事务，获取事务的状态信息 |
| void commit(TransactionStatus status)；                      | 提交事务                     |
| void rollback(TransactionStatus status)；                    | 回滚事务                     |

**注意：**

```markdown
* PlatformTransactionManager 是接口类型，Spring的事务管理器，不同的 Dao层技术有不同的实现类
	具体在配置时，实际配置该接口具体的实现类

* Dao层技术是 jdbcTemplate或mybatis时：
		该接口的具体实现类就是 DataSourceTransactionManager
		
* Dao层技术是 hibernate时：
		HibernateTransactionManager

* Dao层技术是 JPA时：
		JpaTransactionManager
```



### 2.2.2 TransactionDefinition 

​	TransactionDefinition接口**提供事务的定义信息**（**事务隔离级别、事务传播行为**、是否是只读、超时时间等等）

| 方法                         | 说明               |
| ---------------------------- | ------------------ |
| int getIsolationLevel()      | 获得事务的隔离级别 |
| int getPropogationBehavior() | 获得事务的传播行为 |
| int getTimeout()             | 获得超时时间       |
| boolean isReadOnly()         | 是否只读           |

**1）事务隔离级别**

​	设置隔离级别，可以解决事务并发产生的问题，如脏读、不可重复读和幻读

```markdown
* ISOLATION_DEFAULT 使用数据库默认级别
		就看底层数据库的默认隔离级别，根据底层数据库进行隔离级别设置
* ISOLATION_READ_UNCOMMITTED 读未提交
		什么都不能防止 
* ISOLATION_READ_COMMITTED 读已提交
		防止脏读，Oracle默认隔离级别
* ISOLATION_REPEATABLE_READ 可重复读
		防止脏读，不可重复读。MySQL默认隔离级别
* ISOLATION_SERIALIZABLE 串行化
		级别最高的隔离级别，都可以防止。但是效率最低
```

**2）事务传播行为**

​	事务传播行为指的就是当一个业务方法被另一个业务方法调用时，应该如何进行事务控制。	**REQUIRED （增删改操作的传播行为）**和**SUPPORTS （查询操作）** 常用

| 参数                                  | 说明                                                         |
| ------------------------------------- | ------------------------------------------------------------ |
| **REQUIRED （增删改操作的传播行为）** | 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，就加入到这个事务。（默认值） **就是当前被调用的方法必须要进行事务控制**   （增删改） |
| **SUPPORTS （查询操作）**             | 支持当前事务，如果当前没有事务，就以非事务方式运行（没有事务）     **当前被调用的方法有没有事务都可以执行**    （查询） |
| MANDATORY                             | 使用当前的事务，如果当前没有事务，就抛出异常                 |
| REQUERS_NEW                           | 新建事务，如果当前在事务中，把当前事务挂起                   |
| NOT_SUPPORTED                         | 以非事务方式执行，如果当前存在事务，就把事务挂起             |
| NEVER                                 | 以非事务方式执行，如果当前存在事务，抛出异常                 |
| NESTED                                | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行 REQUIRED 类似的操作 |

```markdown
* read-only（是否只读）：查询时设置为只读

* timeout（超时时间）：默认-1，没有超时限制。如果有，以秒为单位进行设置
```



### 2.2.3 TransactionStatus

​	TransactionStatus接口 提供的就是 **事务具体的运行状态**

| 方法                       | 说明         |
| -------------------------- | ------------ |
| boolean isNewTransaction() | 是否是新事务 |
| boolean hasSavepoint()     | 是否是回滚点 |
| boolean isRollbackOnly()   | 事务是否回滚 |
| boolean isCompleted()      | 事务是否完成 |

可以简单的理解三者的关系：**事务管理器**通过**读取事务定义参数**进行事务管理，然后会产生一系列的**事务状态**。



### 2.2.4 实现代码

1）配置文件

```xml
<!--事务管理器交给IOC-->
<bean id="transactionManager"
class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource"/>
</bean>
```



2）业务层代码

```java
@Service
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private PlatformTransactionManager transactionManager;
  @Override
  public void transfer(String outUser, String inUser, Double money) {
    // 创建事务定义对象
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    // 设置是否只读，false支持事务
    def.setReadOnly(false);
    // 设置事务隔离级别，可重复读mysql默认级别
    def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
    // 设置事务传播行为，必须有事务
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    // 配置事务管理器
    TransactionStatus status = transactionManager.getTransaction(def);
   
    try {
      // 转账
      accountDao.out(outUser, money);
      accountDao.in(inUser, money);
      // 提交事务
      transactionManager.commit(status);
   } catch (Exception e) {
      e.printStackTrace();
      // 回滚事务
      transactionManager.rollback(status);
   }
 }
}
```



### 2.2.5 知识小结

​	Spring中的事务控制主要就是通过这三个API实现的

```markdown
* PlatformTransactionManager 负责事务的管理，它是个接口，其子类负责具体工作

* TransactionDefinition 定义了事务的一些相关参数，隔离级别啊，传播行为啊等等

* TransactionStatus 代表事务运行的一个实时状态
```

三者的关系：**事务管理器**通过读取**事务定义参数**进行事务管理，然后产生一系列的**事务状态**





## 2.3 基于XML方式的声明式事务控制【重点】

​	在Spring配置文件中通过配置的方式来处理事务，从而代替代码式的处理事务。底层就是采用AOP思想实现的

**声明式事务控制明确事项：**

* 核心业务代码（目标对象）	（切入点是谁？）
* 事务增强代码（Spring已提供事务管理器）        （通知是谁？）
* 切面配置                （切面如何配置？）



### 2.3.1 快速入门

**需求**：

​	使用spring声明式事务控制转账业务，实现了事务控制代码和业务代码的解耦合，通过配置的方式实现了事务控制

**步骤分析**

```markdown
1. 引入tx命名空间和约束路径

2. 事务管理器通知配置

3. 事务管理器AOP配置

4. 测试事务控制转账业务代码
```



1）引入tx命名空间

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
</beans>
```



2）事务管理器通知配置		**声明事务管理器，配置通知**

```xml
<!--借助Spring的声明式事务完成对AccountServiceImpl 类里面的方法进行事务控制增强，实现事务代码和业务代码的解耦合-->

<!--    声明当前配置事务管理器对象-->
<!--这个事务管理器在进行事务控制时，主要要借助Connection来完成事务控制
    所以在创建该对象时，要注入数据源对象
-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--通过set方式注入数据源对象-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    

    <!--    配置通知增强，事务通知的配置，在通知中指定了事务的相关属性
            transaction-manager="transactionManager"：指定事务管理器对象
-->

    <tx:advice id="txAdvice" transaction-manager="transactionManager">
<!--       定义事务的一些属性  * 表示当前任意名称的方法都走默认配置。如事务隔离级别，事务传播行为都走默认值-->

<!--                Spring在使用transactionManager这个事务管理器进行事务控制时，就可以根据这些事务的属性配置来完成事务控制-->
     <tx:attributes>
			<tx:method name="*"/>
      </tx:attributes>
 </tx:advice>
```



3）事务管理器AOP配置			**AOP配置，配置切面**

```xml
<!--完成事务aop织入的配置，也就是通知与切点的结合-->

<!-- aop配置：配置切面-->
<!-- 普通的aop配置用到 aop:aspect 标签-->
<!--        声明式事务用到 aop:advisor 标签-->


   <aop:config>
<!--声明式事务配置时，配置切面的子标签是aop:advisor-->
<!--        advice-ref="txAdvice"：表示当前要用的通知-->
<!--            txAdvice：当前的事务管理器对象里的方法作为通知方法-->
<!--        pointcut="execution(* com.lagou.service.impl.AccountServiceImpl.*(..))"：-->
<!--            表示 通知为AccountServiceImpl中的任意参数类型，任意参数个数，任意返回值的方法 进行增强-->

       <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.lagou.service.impl.AccountServiceImpl.*(..))"/>
  </aop:config>
```



4）测试事务控制转账业务代码

```java
@Override
    public void transfer(String outUser, String inUser, Double money) {

        // 调用dao的out及in方法
        accountDao.out(outUser,money);

        // 制造异常
        int i = 1/0;

        accountDao.in(inUser,money);
    }
```



### 2.3.2 事务参数的配置详解

```xml
<!--        name：切点方法名称
        	isolation：事务的隔离级别
      		propagation：事务的传播行为
       		read-only：是否只读
    	    timeout：超时时间-->

         <tx:method name="transfer" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" timeout="-1"/>
```



**CRUD常用配置**

```xml
<tx:attributes>
<!--           **CRUD常用配置**-->
<!--          当前在切点表达式中所能匹配到的方法，若是save开头的，事务传播行为就是REQUIRED。其他都走默认值-->
    
           <tx:method name="save*" propagation="REQUIRED"/>
       <tx:method name="delete*" propagation="REQUIRED"/>
        <tx:method name="update*" propagation="REQUIRED"/>
    
<!--         以find开头的方法，是要做查询的。read-only="true"：设置为只读的-->
        <tx:method name="find*" read-only="true"/>
<!--        别的方法都走默认配置  -->
         <tx:method name="*"/>
   </tx:attributes>
```



### 2.3.3 知识小结

```markdown
* 配置事务管理器

* 事务通知的配置	
		tx:advice，在通知中指定了事务的相关属性
		
* 事务aop织入的配置	
		通知与切点的结合
```





## 2.4 基于注解的声明式事务控制【重点】

### 2.4.1 常用注解

**步骤分析**

```markdown
1. 修改service层，增加事务注解
2. 修改Spring核心配置文件，开启事务注解扫描，使得配置的事务注解生效
```

1）修改service层，增加事务注解

```java
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
```



2）修改Spring核心配置文件，开启事务注解支持

```xml
<!--借助Spring的声明式事务完成对AccountServiceImpl 类里面的方法进行事务控制增强，实现事务代码和业务代码的解耦合-->

<!--    声明当前配置事务管理器对象-->
<!--这个事务管理器在进行事务控制时，主要要借助Connection来完成事务控制
    所以在创建该对象时，要注入数据源对象
-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--通过set方式注入数据源对象-->
        <property name="dataSource" ref="dataSource"/>
    </bean>


    <!--    声明式事务控制： 要开启事务的注解支持-->
<!--    xml方式，事务注解驱动的配置-->
    <tx:annotation-driven/>
```



### 2.4.2 采用纯注解的方式实现声明式事务控制

通过核心配置类添加一些注解把核心配置文件替换

```java
/**
 *  让该类变成核心配置类，在该类上添加一些对应的注解把核心配置文件里面的标签替换掉
 */
@Configuration      // 让该类变成核心配置类，声明该类为核心配置类
@ComponentScan("com.lagou")      // 替换IOC注解扫描标签，包扫描
@Import(DataSourceConfig.class)     // 导入其他配置类
// 注解方式的  事务注解驱动的配置
@EnableTransactionManagement        // 事务的注解驱动，借助了这个注解 替换了 <tx:annotation-driven/> 这个标签，开启了事务的注解支持
public class SpringConfig {


    // JdbcTemplate 模板对象
    // 在调用这个方法时，在参数前面添加 @Autowired 这个注解
    // Spring在调用该方法时，就会从IOC容器中取出DataSource类型的实例对象 注入 给参数
    // 根据类型进行注入
    @Bean
    public JdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource){

        return new JdbcTemplate(dataSource);
    }



    // 事务管理器对象
    // 在构建事务管理器对象时，也要注入dataSource
    // 把返回值存到IOC容器中
    @Bean
    public PlatformTransactionManager getPlatformTransactionManager(@Autowired DataSource dataSource){

        return new DataSourceTransactionManager(dataSource);
    }

}
```



数据源子配置类

```java
/**
 *  关于数据库的一些配置信息的配置类
 */
@PropertySource("classpath:jdbc.properties")     // 加载对应的jdbc.properties 文件
public class DataSourceConfig {

    // 注入从jdbc.properties文件中读取到的value值
    @Value("${jdbc.driverClassName}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    // 由Spring加载核心配置类时，调用getDataSource方法，获取返回值，将返回值存到IOC容器中
    @Bean   // 会把当前方法的返回值对象放进IOC容器中
    public DataSource getDataSource(){

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        return druidDataSource;
    }

}
```



测试

```java
// 替换运行环境为Spring
@RunWith(SpringJUnit4ClassRunner.class)
// 指定加载核心配置文件路径
//@ContextConfiguration({"classpath:applicationContext.xml"})
// 加载核心配置类
@ContextConfiguration(classes = SpringConfig.class)
public class AccountServiceImplTest {

    // 注入AccountServiceImpl对象
    @Autowired
    private AccountService accountService;


    @Test
    public void testTransfer(){
        accountService.transfer("tom","jerry",100d);
    }
}
```



### 2.4.3 声明式事务控制知识小结

```markdown
* 配置事务管理器（xml、注解方式）

* 事务通知的配置（@Transactional 注解配置）
		这个注解可以添加在方法上，也可以添加在类上。添加在方法上就是针对于当前的方法进行事务控制，添加在类上表示这个类里面的所有方法都进行事务控制

* 事务注解驱动的配置 
		xml方式，事务注解驱动的配置 <tx:annotation-driven/>
		注解方式，在核心配置类上添加  @EnableTransactionManagement
```







# 三、Spring集成Web环境		主要就是配置监听器

## 3.1 ApplicationContext应用上下文获取方式

​	应用上下文对象是通过new ClasspathXmlApplicationContext(spring配置文件) 方式获取的，但是每次从容器中获得Bean时都要编写new ClasspathXmlApplicationContext(spring配置文件)，这样的**弊端是配置文件加载多次，应用上下文对象创建多次**。

​	解决思路：

​	在Web项目中，可以使用**ServletContextListener**监听Web应用的启动，因为ServletContextListener 是随着Web应用的启动而创建的，所以只要这个对象创建了，就代表这个Web 应用启动了。

​	可以**在Web应用启动时，就加载Spring的配置文件，创建应用上下文对**象**ApplicationContext**，在**将应用上下文对象存到最大的域****servletContext** 域中，这样就可以**在Web项目中的任意位置从域中获取应用上下**文**ApplicationContext**对象了。

----

就是借助ServletContextListener这个监听器实现，**在启动Web项目时，就完成Spring核心配置文件的加载以及应用上下文对象的创建，并且在应用上下文对象创建完后，把它存到ServletContext域中**。所以就可以在**这个Web项目的任意位置获取到ServletContext域，并从域中获取到应用上下文对象**。

---



## 3.2 Spring提供了获取应用上下文的工具

​	Spring提供了一个监听器**ContextLoaderListener**，就是对上述功能的封装，该监听器内部就可以加载Spring配置文件，创建应用上下文对象，并存到**ServletContext**域中。提供了一个客户端工具**WebApplicationContextUtils**供使用者获得应用上下文对象。



**只需要做两件事：**

1. 在web.xml中配置ContextLoaderListener监听器（导入spring-web依赖坐标）
   1. 当配置完这个监听器，在启动项目时，那么这个监听器就会完成对Spring配置文件的加载，以及应用上下文对象的创建，并把应用上下文对象存到ServletContext域中
2. 使用**WebApplicationContextUtils** 这个客户端工具类 直接获得应用上下文对象 ApplicationContext





## 3.3 实现

### 1）导入Spring集成web的坐标

```xml
		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
```

### 

### 2）在Web.xml 中配置ContextLoaderListener监听器（重点）

```xml
<!--    全局参数：指定applicationContext.xml 文件的路径-->
    <context-param>
<!--      param-name 要写 contextConfigLocation
                因为监听器在解析全局参数时是根据 contextConfigLocation 这个key值 找对应的value值的
    -->
        <param-name>contextConfigLocation</param-name>
        <!-- ApplicationContext.xml 的路径 -->
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

<!--    配置spring的监听器  contextLoaderListener
            如果启动web项目，监听器就会帮助我们完成对核心配置文件的加载
                并且创建应用上下文对象 ApplicationContext 并存到 servletContext 域中
-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
```



### 3）在Servlet中获取应用上下文对象就可以借助客户端工具类获取了

```java
public class AccountServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        // classPathXmlApplicationContext对象存到servletContext域中，因为servletContext域 是整个web项目
        // 所以就可以在这个web项目任意地方都可以获取到servletContext域对象
        // 从域对象中取出存入的 应用上下文对象classPathXmlApplicationContext对象

        // 获取到Spring的应用上下文对象
        //ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 借助应用上下文对象从IOC容器中取出account实例对象，并打印
        //Account account = (Account) classPathXmlApplicationContext.getBean("account");
        //System.out.println(account);


        // 借助 WebApplicationContextUtils 这个类获取  ApplicationContext  应用上下文对象
        // 应用上下文对象是直接从servletContext域中 获取到的

        // 这样就避免了应用上下文对象被创建多次，以及核心配置文件被加载解析多次
        ApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        Account account = (Account) webApplicationContext.getBean("account");
        System.out.println(account);

    }
}
```

