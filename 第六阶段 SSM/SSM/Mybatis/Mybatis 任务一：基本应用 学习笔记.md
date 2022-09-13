

# Mybatis 任务一：基本应用 学习笔记

---

SSM=springmvc+spring+mybatis



# 一 、框架简介

## 1.1  Web阶段的三层架构

软件开发常用的三层架构，有着清晰的任务划分：

* 持久层：主要完成对数据库的相关的操作，对数据库的增删改查
  * DAO层
* 业务层：主要根据功能需求完成业务逻辑的定义和实现
  * Service层
* 表现层：主要完成与最终软件使用用户的交互，需要有交互界面（UI）
  * Web层

三层架构之间调用关系：**表现层调用业务层，业务层调用持久层**

各层之间数据交互，用JavaBean对象来传递数据

---



服务器后台代码编写分为表现层，业务层，持久层。高内聚，低耦合。每一层都有自己执行的逻辑。

* 表现层，Web层：参数的接收与封装，调用业务层方法和视图的渲染与跳转
  * 把从前台传过来的参数封装成JavaBean对象，方便进行参数传递
* 业务层，Service层：编写业务逻辑的代码和调用持久层方法
* 持久层，Dao层：对数据库进行CRUD

<img src="C:\Users\zs\Desktop\学习\阶段六\模块一Mybatis\Mybatis画图\1-1.png" style="zoom: 200%;" />

**框架就是针对与每一层的技术解决方案，如现在学的Mybatis就是持久层的框架，应用在持久层的技术**



## 1.2 框架

### 1.2.1 什么是框架？

框架就是一套规范，使用这个框架就要遵守这个框架所规定的约束。

**框架就是一个半成品软件**，就是把一些通用的代码进行封装。我们只需要在框架的基础上进行业务逻辑代码编写

## 1.2.2 为什么使用框架？

框架就是把一些冗余的，重用率低的代码进行封装，将代码实现通用性。所以使用框架可以提高开发效率

## 1.2.3 常用框架

后台代码的三层架构：

* 表现层框架：应用在Web层的框架，解决与用户交互的框架
  * struts2、springmvc
* 全栈框架：全栈就是该框架在每一层都有自己的解决方案
  * spring
* 持久层框架：解决数据持久化的框架
  * hibernate、mybatis、spring jdbc

---

开发用到的框架：

SSH：struts2+spring+hibernate

**SSM**：**springmvc+spring+mybatis**



# 二、Mybatis简介

## 2.1 原始jdbc操作（查询数据）

```java
@Test
public void testJDBC() throws ClassNotFoundException,SQLException {

	// 注册驱动
	Class.forName("com.mysql.jdbc.Driver");
	// 获取连接
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lagou_test","root","123456");
	// 获取Statement
	String sql = "select id,username from user";
    PrepareStatement ps = con.prepareStatement(sql);
    // 执行查询
    ResultSet resultSet = ps.executeQuery();
    // 遍历结果集
    while(resultSet.next()){
    	// 封装实体
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		// 实体封装完毕
		System.out.println(user);
    }
    // 释放资源
    resultSet.close();
    ps.close();
    con.close();
}
```





## 2.2 原始jdbc操作的分析

使用原始jdbc操作数据存在的一些问题：

* 数据库配置信息硬编码问题，配置信息写死在了源代码
* 频繁创建释放数据库连接，影响系统性能
* sql语句硬编码问题，sql变动需要改变源代码
* 需要手动封装返回结果集实体对象



**解决思路**

* 配置文件解决硬编码问题，就是把硬编码信息抽取到XML配置文件中
* 使用数据库连接池初始化连接资源
* XML配置文件解决sql语句硬编码问题
* 反射+内省解决手动封装返回结果集。
  * 自动将实体与表进行属性与字段的自动映射



---

**Mybatis：持久层框架：本身就是对JDBC进行了封装，可以简化开发，也规避了JDBC操作数据库的一些问题**





## 2.3 Mybatis简介

Mybatis是一个基于**ORM**的**半自动轻量级**持久层框架，**它对jdbc的操作数据库的过程进行了封装**

* 半自动：mybatis              是需要手动编写sql 
* 全自动：hibernate
* 本质区别：是否需要手动编写sql
* 轻量级：程序在启动期间所需要消耗的资源多少

### mybatis 历史

Mybatis 本是Apache的一个开源项目iBatis



## 2.4 ORM思想

**ORM**：**Object Relational Mapping：对象关系映射**

O：对象模型：**实体对象**，JavaBean

R：关系型数据库的结构模型：**数据库表**

M：映射：从数据库到对象模型的映射，可通过XML文件映射



**实现**：

（1）让实体类和数据库表进行一一对应关系

​	先让实体类和数据库表对应

​	再让实体类属性和表里面字段对应

（2）不需要直接操作数据库表，直接操作表对应的实体类对象



怎么将实体对象和数据库表建立映射关系？

**借助XML或者注解来完成映射关系的建立**



* 一个实体对应一张数据库表
* 一个对象对应数据库中的一条记录



**ORM是一种思想**

会跟踪实体的变化，将实体的变化翻译成sql脚本，执行到数据库中，也就是将实体变化映射到表的变化

mybatis采用**ORM**思想解决了实体和数据库映射的问题，封装了jdbc，屏蔽了jdbc api底层访问细节，就是我们不用与jdbc api打交道，就可以完成对数据库的持久化操作了。





# 三、Mybatis快速入门

## 3.1 Mybatis开发步骤

案列需求：通过mybatis查询数据库user表的所有记录，封装到User对象中并打印

步骤：

```markdown
1. 创建数据库及user表
2. 创建maven工程，导入依赖(mysql驱动，mybatis，junit)
3. 编写User实体类
4. 编写UserMapper.xml映射配置文件(ORM思想，就是配置实体类与表的映射关系的)
5. 编写SqlMapConfig.xml 核心配置文件      (就是在核心配置文件中引入映射配置文件)
	数据库环境配置
	映射关系配置的引入(引入映射配置文件的路径)
	
6. 编写测试代码
	1.加载核心配置文件
	2.获取sqlSessionFactory工厂对象
	3.获取sqlSession会话对象
	4.执行sql
	5.打印结果
	6.释放资源
```



## 3.2 代码实现

### 1）创建user数据表

```sql
CREATE DATABASE mybatis_db;
USE mybatis_db;

CREATE TABLE USER(
	id INT(11) NOT NULL AUTO_INCREMENT,
	username VARCHAR(32) NOT NULL COMMENT '用户名称',
	birthday DATETIME DEFAULT NULL COMMENT '生日',
	sex CHAR(1) DEFAULT NULL COMMENT '性别',
	address VARCHAR(256) DEFAULT NULL COMMENT '地址',
	PRIMARY KEY (id)
	) ENGINE=INNODB DEFAULT CHARSET=utf8;
	

-- insert..
INSERT INTO USER(id,username,birthday,sex,address) VALUES(1,'孙悟空','2020-11-11 00:00:00','男','花果山'),
(2,'猪八戒','2020-12-14 10:04:33','男','高老庄');
```



### 2）创建maven工程，导入依赖

```xml
<!--    指定编码及版本-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        <java.version>1.11</java.version>
        <maven.compiler.source>1.11</maven.compiler.source>
        <maven.compiler.target>1.11</maven.compiler.target>
    </properties>

<!--    引入相关依赖-->
    <dependencies>
<!--        引入mybatis依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.4</version>
        </dependency>

        <!--        引入mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <!--        引入junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>


    </dependencies>
```



### 3）编写User实体类

```java
public class User {
  private Integer id;
  private String username;
  private Date birthday;
  private String sex;
  private String address;
  // getter/setter 略
}
```



### 4）编写UserMapper映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="userMapper">
<!--    namespace:命名空间：与id属性共同构成唯一标识 namespace.id:user.findAll
        resultType:返回结果类型(完成自动映射封装):要封装的实体的全路径
-->
<!-- resultType：就是将SQL查询出来的数据封装成一个个User实体对象 -->
    <!--查询所有-->
    <select id="findAll" resultType="com.lagou.domain.User">
        select * from user
    </select>
</mapper>
```



### 5）编写Mybatis核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--环境配置-->
<!--    environments：运行环境-->
<!--        默认走id为development的environment开发环境-->
    <environments default="development">
        <environment id="development">
<!--           transactionManager事务管理器，当前的事务交由JDBC管理 -->
            <!--使用JDBC类型事务管理器-->
            <transactionManager type="JDBC"></transactionManager>
<!--            数据源信息,数据库的一些配置信息  POOLED:使用mybatis的连接池  UNPOOLED：不使用连接池-->
            <!--使用连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///mybatis_db?characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

<!--    引入映射配置文件-->
    <mappers>
        <mapper resource="mapper/UserMapper.xml"></mapper>
    </mappers>
</configuration>
```



### 6）编写测试类

```java
/*
        快速入门测试方法
     */
    @Test
    public void mybatisQuickStart() throws IOException {

        // 1.加载核心配置文件,加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象，就是生产sqlSession会话对象的。再借助工厂对象获取到会话对象
        // SqlSessionFactoryBuilder SQLSession工厂类的构建者
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象，借助sqlSession会话对象去操作数据库
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，操作数据库    参数：statementid：namespace.id     返回结果就是List集合，集合中就是user对象
        List<User> users = sqlSession.selectList("userMapper.findAll");

        // 5.遍历打印结果
        for (User user : users) {
            System.out.println(user);
        }

        // 6.释放资源
        sqlSession.close();
    }
```





## 3.3 知识小结

```markdown
1. 创建了mybatis_db数据库和user表
2. 创建项目，导入依赖
3. 创建User实体类，属性名要和user表中的字段名一样，有对应关系
4. 编写映射文件UserMapper.xml
5. 编写核心配置文件sqlMapConfig.xml，并在核心配置文件中引入映射配置文件的路径
6. 编写测试类
```





# 四、Mybatis映射文件概述

```xml
<!-- 映射配置文件的DTD约束头 -->
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 根标签 namespac和id共同构成了唯一标识 -->
<mapper namespace="userMapper">
    <!-- 查询操作select标签 	resultType返回结果类型，要封装的实体的全路径，当前要把查询结果封装成一个个user，所以写User实体类的全路径 -->
    <select id="findAll" resultType="com.lagou.domain.User">
       	<!-- 配置的就是要执行的sql语句 -->
        select * from user
    </select>
</mapper>
```





# 五、Mybatis增删改查

## 5.1 新增      就是借助Mybatis在user表中新增一条记录

### 1）编写映射配置文件UserMapper.xml

```xml
<!--    新增用户-->
<!--    #{}:mybatis中的占位符，等同于JDBC中的?-->
<!--    parameterType:指定接收到参数类型   就是封装好属性的一个user对象-->
<!--    需要在#{}里面指明当前接收的实体参数中的哪个属性值-->
<!--    #{}里面的值要和传递过来的实体对象里面的get方法后面的首字母小写保持一致-->
<!--    #{}里面的值要和传递过来的实体对象里面的属性名称保持一致-->
    <insert id="saveUser" parameterType="com.lagou.domain.User">
        insert into user (username,birthday,sex,address) values (#{username},#{birthday},#{sex},#{address});
    </insert>
```



### 2）编写测试类

```java
/*
        测试新增用户
     */
    @Test
    public void testSave() throws IOException {
        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，调用api方法
        User user = new User();
        user.setUsername("沙悟净");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("流沙河");
        sqlSession.insert("userMapper.saveUser",user);

        // 5.关闭资源
        // 当前做的是新增操作，增删改这样的操作都会改变数据库的状态，所以对于增删改操作需要进行事务提交
        // 手动提交事务
        // 因为数据库表id是自动增长的，就算执行sql后，没有提交事务，也会自增id，没有提交事务，进行回滚，id自增。
        // DML语句，手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }
```



### 3）新增的注意事项

```markdown
- 在映射配置文件中插入语句使用insert标签

- 在映射配置文件中使用parameterType属性来指定要插入的数据类型

- sql语句中要使用#{实体属性名}的方式引用实体中属性的值，其实写的是get方法后面的首字母小写。在执行过程中就会根据名称找到get方法来引用实体中的属性值

- 插入操作使用的API是sqlSession.insert("命名空间.id",实体对象);

- 插入操作涉及数据库数据的变化的，所以要使用sqlSession对象显示的提交事务，即sqlSession.commit()
```





## 5.2 用mybatis实现修改操作

### 1）编写映射文件UserMapper.xml

```xml
<!--    更新用户-->
<!--    参数封装成user实体对象，当做参数传递-->
<!--    parameterType：指定传递参数的类型-->
<!--    #{}中指明当前引用的是传递过来的实体中的哪个属性值-->
    <update id="updateUser" parameterType="com.lagou.domain.User">
        update user set username = #{username},birthday = #{birthday},sex = #{sex},address = #{address} where id = #{id}
    </update>

```



### 2）编写测试类

```java
/*
        测试更新用户
     */
    @Test
    public void testUpdate() throws IOException {

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，调用sqlSession的api方法
        User user = new User();
        user.setId(4);
        user.setUsername("蜘蛛精");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("盘丝洞");

        sqlSession.update("userMapper.updateUser",user);
        // 5.手动提交事务并关闭资源
        // DML语句，手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }
```



### 3）修改注意事项

```markdown
- 在映射配置文件中，修改语句要使用update标签

- 修改操作使用的API是sqlSession.update("命名空间.id",实体对象);
```





## 5.3 删除操作   使用mybatis根据id删除数据

### 1）编写映射配置文件UserMapper.xml

```
#{}中的值
如果parameterType传递的是对象类型，#{}里面的值要和对象类型里面的属性名保持一致

如果parameterType传递的是基本数据类型或String，且个数只有一个时，#{}里面的值是随便写的
```



```xml
<!--    删除用户-->
<!--    parameterType接收到的实际参数就是个int类型的数字，所以类型就是int类型的数值-->
<!--    当parameterType的类型为基本数据类型或String时，且个数只有一个时，#{}里面的值随便写，因为不管写什么，要引用的都是实际传递过来的唯一值-->
    <delete id="deleteUser" parameterType="java.lang.Integer">
        delete from user where id = #{abc}
    </delete>
```



### 2）编写测试类

```java
/*
        测试删除用户
     */
    @Test
    public void testDelete() throws IOException {

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql，调用sqlSession的API方法
        sqlSession.delete("userMapper.deleteUser",4);

        // 5.动提交事务
        // DML语句，手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }
```



### 3）删除注意事项

```markdown
- 在映射文件中，删除语句使用delete标签

- Sql语句中使用#{任意字符串}

- 删除操作使用的API是sqlSession.delete("命名空间.id",Object)
```





## 5.4 基于mybatis的增删改查操作的知识小结

```markdown
* 查询时调用的api是selectList根据namespace.id来定位到SQL语句，SQL语句执行完后将查询出来的结果每一条记录封装成一个user对象，再由mybatis将封装好的user对象封装成一个list集合并返回
	
	代码：
		List<User> list = sqlSession.selectList("UserMapper.findAll");
	映射文件：
  <select id="findAll" resultType="com.lagou.domain.User">
	 select * from user
  </select>
 
* 新增api是insert。SQL语句中的#{}占位符的值是和参数实体对象中的属性名称保持一致的
	代码：
		sqlSession.insert("UserMapper.save", user);
	映射文件：
  <insert id="save" parameterType="com.lagou.domain.User">
   insert into user(username,birthday,sex,address)
   values(#{username},#{birthday},#{sex},#{address})
  </insert>
 
* 修改
	代码：
		sqlSession.update("UserMapper.update", user);
	映射文件：
  <update id="update" parameterType="com.lagou.domain.User">
   update user set username = #{username},birthday = #{birthday},
   sex = #{sex},address = #{address} where id = #{id}
  </update>
 
* 删除。当参数实体类型为基本数据或String时，#{}中的值可以随便写
	代码：
		sqlSession.delete("UserMapper.delete", 4);
	映射文件：
  <delete id="delete" parameterType="java.lang.Integer">
   delete from user where id = #{id}
  </delete>
```





# 六、Mybatis核心配置文件概述

## 6.1 Mybatis核心配置文件层级关系

Mybatis的配置文件包含了会影响Mybatis行为的设置和属性信息

配置文档的顶层结构：

* configuration(配置根标签)
  * properties(属性)
  * settings(设置)
  * typeAliases(类型别名)
  * typeHandlers(类型处理器)
  * objectFactory(对象工厂)
  * plugins(插件)
  * environments(环境配置)
    * environment(环境变量)
      * transactionManager(事务管理器)
      * dataSource(数据源)
  * databaseldProvider(数据库厂商标识)
  * mappers(映射器)



## 6.2 Mybatis常用配置解析

### 1）environments标签	 	配置运行环境的

**对数据库环境的配置，支持多环境配置，也就是一个environments里可以配置多个environment子标签**

**每一个environment就代表一种环境，数据库的配置信息**

transactionManager这个子标签就是配置事务管理器的，因为我们要借助mybatis来完成对数据库的增删改操作，所以要指定事务管理器

dataSource子标签是来进行数据源的配置，type属性值为**UNPOOLED：就是不使用连接池**，**POOLED：就是使用连接池**

property子标签就是来配置数据库的配置信息的

```xml
<!-- default="development"  指定默认的环境名称 -->
<environments default="development">
<!-- id="development"  指定当前环境的名称 -->
        <environment id="development">
<!-- type="JDBC" 指定事务管理类型是JDBC -->
            <transactionManager type="JDBC"></transactionManager>
<!-- type="POOLED" 指定当前数据源类型是连接池-->
            <dataSource type="POOLED">
                <!-- 数据源配置的基本参数 -->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
```

```markdown
1. 其中，事务管理器（transactionManager）类型有两种：
 - JDBC：
  这个配置就是直接使用了JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
 
 - MANAGED：
  这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期。
  例如：mybatis与spring整合后，事务交给spring容器管理。
 
2. 其中，数据源（dataSource）常用类型有三种：
 - UNPOOLED：也就是不使用连接池
 这个数据源的实现只是每次被请求时打开和关闭连接。
 
 - POOLED：也就是使用连接池
  这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来。
 
 - JNDI :
  这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据
源，然后放置一个 JNDI 上下文的数据源引用
```



### 2）properties标签

实际开发中，习惯将数据源的数据库配置信息单独抽取成一个properties文件，**该标签就是去加载额外配置的properties文件的**

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///mybatis_db?characterEncoding=UTF-8&useSSL=false
jdbc.username=root
jdbc.password=123456
```

需要在核心配置文件中使用properties标签来加载外部的properties文件,并用${key}的方式获取对应properties文件中设置的value值



**在configuration这个根标签中配置properties这个子标签时，那么这个properties子标签必须要在其他标签之上，就是必须配置在第一顺序**

**最先配置的是properties子标签，然后environments，然后mappers**

****

```xml
<!--    加载properties文件  resource里填写的就是properties文件的全路径-->

<!--    在加载sqlMapConfig.xml这个核心配置文件的同时，把jdbc.properties这个文件也进行加载了-->
    <properties resource="jdbc.properties"></properties>
<!--    environments：运行环境-->
<!--        默认走id为development的environment开发环境-->
    <environments default="development">
        <environment id="development">
<!--           transactionManager事务管理器，当前的事务交由JDBC管理 -->
            <transactionManager type="JDBC"></transactionManager>
<!--            数据源信息,数据库的一些配置信息  POOLED:使用mybatis的连接池  UNPOOLED：不使用连接池-->
            <dataSource type="POOLED">
<!--                已经加载了jdbc.properties这个文件。所以数据库的配置信息的value值不用写死了，就可以动态地获取到jdbc.properties里面配置的value值-->
<!--                使用${}来引用jdbc.properties里面配置的key值所对应的value值-->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
```



### 3）typeAliases标签	   

####  就是可以给自己编写的实体类的类型起别名的

类型别名是**为Java类型设置一个短的名字。**

为了简化映射文件Java类型设置，mybatis框架为我们设置好的一些常用的类型的别名：

| 别名    | 数据类型 |
| ------- | -------- |
| string  | String   |
| long    | Long     |
| int     | Integer  |
| double  | Double   |
| boolean | Boolean  |

自己编写的实体类的类型名称配置：

```xml
<!-- 查询用户 -->
<!-- 类型配置为User全限定名称 -->
<select id = "findAll" resultType="com.lagou.domain.User">
	select * from User
</select>
```



我们可以配置typeAliases，为com.lagou.domain.User定义别名为user:

**当起了别名后，以后在配置文件写自定义实体的类全路径时就可以写别名了**

```xml
<!--    设置别名-->
    <typeAliases>
<!--        type值为实体类的全路径，alias值是为当前实体所起的别名-->
<!--        方式一：给单个实体起别名-->
<!--        <typeAlias type="com.lagou.domain.User" alias="user"></typeAlias>-->

<!--        方式二：批量起别名，别名就是类名，且不区分大小写。常用-->
<!--        name值配置的就是实体类所在的包路径
这样就会去扫描domain这个包下所有的实体类，并给所有的实体起别名-->
        <package name="com.lagou.domain"/>
    </typeAliases>


<!-- 类型配置user为别名 -->
<select id = "findAll" resultType="USeR">
	select * from User
</select>
```



### 4）mappers标签

**该标签的作用是加载映射文件的**，加载方式有：

```markdown
1. 使用相对于类路径的资源引用，例如：
	常用
	使用resource加载配置文件的话，映射配置文件需要存在于当前工程目录中
<mapper resource="org/mybatis/builder/userMapper.xml"/>

2. 使用完全限定资源定位符（URL），例如：
	使用url加载配置文件的话，映射配置文件无需存在于当前工程目录中，要有file协议
<mapper url="file:///var/mappers/userMapper.xml"/>


《下面两种mapper代理开发中使用:暂时了解》
3. 使用映射器接口实现类的完全限定类名，例如：
<mapper class="org.mybatis.builder.userMapper"/>

4. 将包内的映射器接口实现全部注册为映射器，例如：
<package name="org.mybatis.builder"/>
```





## 6.3 知识小结

**mybatis核心配置文件中经常配置的四个标签：**

properties标签：**就是去加载外部的properties文件的**

```xml
<properties resource="jdbc.properties"></properties>
```



typeAliases标签：**设置类型别名的**,两种方式：

```xml
<!-- 方式一：给单个实体类起别名 -->
<typeAlias type="com.lagou.domain.User" alias="user"></typeAlias>

<!-- 方式二：根据包路径批量起别名  常用-->
<typeAlias><package name="com.lagou.domain"/></typeAlias>
```



mappers标签：**加载映射配置文件的**

```xml
<mapper resource="com/lagou/mapper/UserMapping.xml"></mapper>
```



environments标签：**数据源的环境配置**

* transactionManager标签的type=JDBC	**表示将事务交给JDBC管理**
* dataSource标签的type=POOLED         **表示当前采用Mybatis自带的数据库连接池**

```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC"/>
    <dataSource type="POOLED">
      <property name="driver" value="${jdbc.driver}"/>
      <property name="url" value="${jdbc.url}"/>
      <property name="username" value="${jdbc.username}"/>
      <property name="password" value="${jdbc.password}"/>
    </dataSource>
  </environment>
</environments>
```





# 七、Mybatis的API概述

## 7.1 API介绍

### 7.1.1 SqlSession工厂构建器SqlSessionFactoryBuilder



---

**SqlSessionFactoryBuilder就是SqlSessionFactory的构建器，主要就是用来构建SqlSessionFactory的**

---



常用API：SqlSessionFactory	build(InputStream  inputStream)

**就是通过加载mybatis的核心配置文件的输入流形式构建一个SqlSessionFactory对象**



```java
       String resource = "org/mybatis/builder/mybatis-config.xml";
// 1.就是借助工具类完成资源的加载
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(inputStream);
```



其中， Resources 工具类，这个类在 org.apache.ibatis.io 包中。Resources 类帮助你从类路径下、文件系统或一个 web URL 中加载资源文件。**就把该核心配置文件加载成了一个字节输入流**



### 7.1.2 SqlSession工厂对象SqlSessionFactory

---

**SqlSessionFactory就是SqlSession工厂对象，就是用来获取SqlSession会话对象的。**

---



SqlSessionFactoryBuilder就是sqlSession工厂的一个构建器，**就是用来构建sqlSessionFactory类的**

所以使用SqlSessionFactoryBuilder对象调用build方法，**build方法就是通过加载mybatis的核心配置文件的输入流来构建出sqlSessionFactory工厂对象**



**sqlSessionFactory工厂对象就是用来生产sqlSession会话对象的**。因为只有拿到sqlSession会话对象，才能去调用相关方法去操作数据库

```java
// 2.获取sqlSessionFactory工厂对象
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
```



**所以使用sqlSessionFactory对象调用openSession方法来获取sqlSession对象**

| 方法                             | 解释                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| openSession()                    | 就是获取到SqlSession对象的同时会默认开启一个事务，但事务不会自动提交，也就是需要手动提交该事务 |
| openSessioin(boolean autoCommit) | 参数为是否自动提交，如果为true，表示自动提交事务             |



```java
// 3.获取sqlSession会话对象，借助sqlSession会话对象去操作数据库，默认开启不会提交的事务。所以当进行DML时，要借助sqlSession.commit()来手动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
// 3.获取sqlSession会话对象，并且当前事务为自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
```



### 7.1.3 SqlSession会话对象

---

**主要就是调用SqlSession会话对象里面的API方法来完成对数据库中数据的增删改查，也可以操作事务**

---



SqlSession方法：

执行语句的方法主要有：

```java
<T> T selectOne(String statement, Object parameter)
<E> List<E> selectList(String statement, Object parameter)
int insert(String statement, Object parameter)
int update(String statement, Object parameter)
int delete(String statement, Object parameter)
```

借助SqlSession操作事务的方法：

```java
void commit() 
void rollback()
```





## 7.2 Mybatis基本原理介绍

<img src="C:\Users\zs\Desktop\学习\阶段六\模块一Mybatis\Mybatis画图\1-5.png" style="zoom:200%;" />

---



**Mybatis配置文件**：

* 核心配置文件：SqlMapConfig.xml
  * 配置数据源相关信息，事务配置，引入映射配置文件
* 映射配置文件：主要配置了要执行的sql 语句，传入参数(parameterType)，传出参数(resultType)



**new SqlSessionFactoryBuilder().build(传入Resources加载的核心配置文件的字节输入流);**		build方法做了两件事：

* **进行了初始化配置：使用dom4j解析了配置文件**，将映射配置文件中的配置封装成了一个一个的**MappedStatement**对象，**构建map集合，完成初始化**
  * MappedStatement：类，就是封装了要执行的sql，传入参数和传出参数
  * 会将这些**mappedStatement对象封装成Map集合**，**key就是namespace.id,value就是针对于该namespace.id封装的MappedStatement对象**
* 创建了SqlSessionFactory工厂对象



**通过SqlSessionFactory工厂对象获取的SqlSession会话对象本身并不会直接操作数据库，而是委派给Executor执行器来操作**



Executor执行器底层就是去执行JDBC，**MappedStatement**对象里就封装了要执行的sql，返回结果类型，参数类型



---



**所以当SqlSession.selectList(namespace.id)这个方法执行时，会委派给执行器执行，执行器执行JDBC时会首先获取到MappedStatement，因为JDBC需要的内容都在MappedStatement。此时执行器就会根据传过来的namespace.id    key从Map集合中去取出对应的value值 就是MappedStatement，然后去执行JDBC并返回结果**

---





# 八、Mybatis的dao层开发使用

## 8.1 Mybatis在dao层的传统开发方式的使用

### 1）编写UserMapper接口

```java
public interface UserDao {

    /*
        查询所有
     */
    public List<User> findAll() throws Exception;
}
```



### 2）编写UserMapper实现类

```java
public class UserDaoImpl implements UserDao {

    @Override
    public List<User> findAll() throws IOException {
        // 关于Mybatis的API方法

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 4.调用方法
        List<User> users = sqlSession.selectList("userMapper.findAll");

        // 释放资源
        sqlSession.close();

        return users;
    }
}
```



### 3）编写UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="userMapper">

<!--    查询所有用户-->
    <select id="findAll" resultType="user">
        select * from user;
    </select>
</mapper>
```



### 4）测试

```java
public class MybatisTest {

    /*
        Mybatis的Dao层传统方式 测试方法
     */
    @Test
    public void test1() throws IOException {

        // 调用持久层方法
        UserDao userDao = new UserDaoImpl();
        List<User> users = userDao.findAll();

        // 遍历
        for (User user : users) {
            System.out.println(user);
        }

    }
}
```



### 5）知识小结

传统开发方式就是**需要自己来编写Dao层接口和对应的实现类**

```markdown
1. 编写UserMapper接口
2. 编写UserMapper实现类
3. 编写UserMapper.xml映射配置文件
```



**传统方式的问题：**

* 在实现类中，**存在Mybatis模版代码重复**
* **实现类在调用方法时，所传递的statementid 存在硬编码问题**

思考：能不能只写接口，不写实现类，因为问题都在实现类中。就是开发方式我**只写接口和映射配置文件，不写实现类了**?



因为在dao（mapper）的实现类中对sqlsession的使用方式很类似。因此mybatis提供了接口的动态代理。





## 8.2 Mapper代理开发方式

### 1）介绍

采用 Mybatis 的基于接口代理方式实现 持久层 的开发，**这种方式是我们后面进入企业的主流。**

**基于接口代理方式的开发只需要程序员编写 Mapper 接口**，Mybatis 框架会为我们动态生成实现类的对象。



**这种开发方式要求遵循的规范：**

* **Mapper.xml映射文件中的namespace与mapper接口的全限定名相同**
  * 映射文件要和mapper接口处在同包同名状态
* Mapper接口方法名和Mapper.xml映射文件中定义的每个Statement的id相同
* Mapper接口方法的输入参数类型和mapper.xml映射文件中定义的每个sql 的parameterType的类型相同
* Mapper接口方法的输出参数类型(返回值)和mapper.xml映射文件中定义的每个sql的resultType的类型相同



Mapper 接口开发方法只需要程序员编写Mapper 接口（相当于Dao 接口），由Mybatis 框架根据接口定义创建接口的动态代理对象，代理对象的方法体同上边Dao接口实现类方法。



### 2）编写UserMapper接口

```java
public interface UserMapper {

    /*
        根据id查询用户
     */

    public User findUserById(int id) throws Exception;
}
```



### 3）编写UserMapper.xml

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.mapper.UserMapper">

<!--    根据id查询用户-->
    <select id="findUserById" parameterType="int" resultType="user">
        select * from user where id = #{id}
    </select>
</mapper>
```



### 4）测试

```java
public class MybatisTest {

    /*
        Mybatis的Dao层mapper代理方式 测试方法
     */
    @Test
    public void test1() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findUserById(1);
        System.out.println(user);
		
        // 释放资源
          sqlSession.close();
    }
}
```





### 加载映射mapper标签

创建映射配置文件时要和mapper接口处在同包同名的状态

```xml
<!--引入映射配置文件-->
    <mappers>
        <!--       映射配置文件的全路径 -->
<!--        <mapper resource="com/lagou/mapper/UserMapper.xml"></mapper>-->

        <!--        mapper接口的全路径-->
<!--        使用该方式：接口和映射文件需要同包同名-->
<!--        <mapper class="com.lagou.mapper.UserMapper"></mapper>-->

        <!--       批量加载映射 常用 -->
<!--        name的值就是mapper接口所在的包路径，就会扫描mapper包下所有的mapper接口，并且加载对应的映射配置文件-->
        <package name="com.lagou.mapper"/>
    </mappers>
```





### 5）Mybatis基于接口代理方式的内部执行原理

我们的持久层现在只有一个接口，而接口是不实际干活的，那么是谁在做查询的实际工作呢？



#### Mapper代理开发方式的执行原理：

**通过追踪源码：**

在mapper代理开发方式中：

* **在获取到sqlSession会话对象后是调用getMapper方法，并且把接口的类类型当做参数进行传递。**

* **而实际返回的就是一个mapper代理对象，具体工作就是由mapper代理对象完成的。**

  

  

  #### **1.mapper代理对象怎么产生的？**

* **底层就是基于JDK动态代理所产生的代理对象**

* **代理对象调用接口中的任意方法时，底层的invoke方法都会执行**



#### **2.invoke方法是怎么执行的？**

在调用接口的方法时，实际执行的是**MapperProxy的invoke方法**，

**而invoke方法的内部还是去调用sqlSession原有的API方法去完成数据的增删改查操作**

---



1. 通过追踪源码我们会发现，我们使用的**mapper实际上是一个代理对象,是MapperProxy代理产生的。**
2. **追踪MapperProxy的invoke方法会发现，其最终调用了mapperMethod.execute(sqlSession, args)**
3. **进入execute方法会发现，最终工作的还是sqlSession**

