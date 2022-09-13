# 任务五 JDBC

# 1.JDBC 概述

	## 什么是JDBC

​		JDBC就是一套操作关系型数据库的规则(接口)，**是Java访问数据库的标准规范**。

## JDBC的作用

​		统一了访问数据库的标准规范，使我们使用一套代码就可以操作所有关系型数据库

## 数据库驱动

​		JDBC是一套接口，具体的实现类由数据库厂商提供，具体的实现类被封装成了jar，这个jar包就是数据库驱动

​		我们去使用这套接口编程，真正执行的代码是	对应的驱动包中的实现类	（面向接口编程，不依赖与任何数据库）





# 2.JDBC 开发步骤	

## 1. 注册驱动(可以省略)	Class.forName("com.mysql.jdbc.driver")

* JDBC规范定义驱动接口：java.sql.Driver
* MySql驱动包提供了实现类：com.mysql.jdbc.Driver

## 2.获取连接	Connection连接对象

**Connection connection = DriverManager.getConnection(url, user, password);**

URL详细

jdbc:mysql://localhost:3306/db4?characterEncoding=UTF-8

## 3.获取语句执行平台	Statement

通过语句执行平台对象Statement去执行SQL

**Statement statement = connection.createStatement();**

Statement类中的方法 	

#### DML操作方法	增删改操作

int executeUpdate(String sql);	执行insert，update，delete语句，返回int类型，代表受影响的行数		增删改操作调用这个方法

#### DQL操作方法	查询操作

ResultSet	executeQuery(String sql);	执行select语句，返回ResultSet结果集对象										查询方法



## 4.处理结果集	Resultset接口 只有查询有结果集

**ResultSet resultset = statement.executeQuery(sql);**	查询

ResultSet接口中的方法

boolean	next()		判断结果集中是否有下一条数据	返回boolean类型

xxx getXxx(Stirng or int)	如果传递的参数是int类型	表示要通过列号查询	如果传递的参数是String类型	表示通过列名查询

xxx getXxx(String or int)	通过列名

## 5.释放资源	关闭顺序 Resultset==>Statement==>Connection	后开先关

关闭流操作放在finally代码中执行	能够保证最终会把资源释放



# 3. SQL注入	使用Statement方式登录，存在SQL注入问题

## 1. 什么是SQL注入？

用户输入的字符串与我们编写的SQL语句进行了拼接，用户输入的内容成为了SQL语法的一部分，用户会利用这里漏洞	输入一些其他的字符串，改变SQL原有的含义	**这就是SQL注入**

select * from jdbc_user where username = 'tom' and password = '123' or '1' = '1';

## 2. 如何解决SQL注入呢？

**要解决SQL注入就不能让用户输入的数据和我们的SQL语句进行直接拼接**		使用preparestatement预处理对象解决

# 4.预处理对象	preparestatement

## 1.预处理对象 PrepareStatement	是Statement接口的子接口，它是一个预编译对象



## 2 .PrepareStatement特点

* 使用预处理对象 他有预编译的功能，提高SQL的执行效率
* 使用预处理对象 通过占位符的方式 设置参数 可以有效的防止SQL注入

​		通过Connection创建PrepareStatement对象

PrepareStatement ps =  connection.prepareStatemen(String sql);				指定预编译的SQL语句，SQL语句中使用占位符?创建一个语句对象



## 3.预处理的使用方式

使用?占位符的方式，去动态的设置参数

select * from jdbc_user where username = ? and password = ?;





**设置实际参数：使用setXxx(占位符的位置(整数), 要设置的值)的方法设置占位符的参数**

如：ps.setString(1, name);		// 设置第一个问号值	为name

ps.setString(2, pass);



## 4. Statement与PrepareStatement的区别

**Statement对象每执行一条SQL	就会发个数据库	数据库要先编译再执行		每次执行都需要编译**	

**预处理对象会将SQL发给数据库进行一个预编译	然后将预编译的SQL保存起来	这样就只需要编译一次了**	进行预编译，并且将SQL缓存		当我们执行多次插入操作的时候，只需要设置参数就可以了						**提高SQL执行效率**

1. Statement用于执行静态SQL语句，也就是说必须事先准备好一个完整的SQL语句去执行
2. PrepareStatement是预编译SQL语句对象，可以使用?占位符的方式动态的去设置参数
3. PrepareStatement可以减少编译的次数，提高数据库性能

# 5.JDBC控制事务

使用JDBC来操作事务

## 事务相关API

使用Connection数据库连接对象去实现事务管理

调用Connection中的方法

void setAutoCommit(boolean autoCommit)		如果设置false就表示关闭自动提交，就相当于开启事务

void commit()															提交事务

void rollback()															回滚事务



## 开发步骤

1. 获取连接

​	con = JDBCUtils.getConnection();

2. 开启事务

   con.setAutoCommit(false);

3. 获取到 PrepareStatement，执行操作

   ps = con.prepareStatement(sql);

4. 正常情况下提交事务

​	con.commit();

5. 出现异常回滚事务

   con.rollback();

6. 最后关闭资源

​	ps.close();

​	con.close();

