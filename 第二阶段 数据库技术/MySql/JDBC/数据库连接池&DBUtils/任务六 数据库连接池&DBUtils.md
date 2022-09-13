# 任务六	数据库连接池&DBUtils

## 1.数据库连接池

##  1. 数据库连接池就是存放数据库连接的池子，用来管理连接，好处就是可以重复的使用连接，减少资源的浪费



##2. 使用数据库连接池	：	Java提供了数据库连接池DataSource接口，具体实现类由数据库厂商完成



## 3.连接池的种类：	创建连接池对象，从连接池中获取连接

DBCP连接池

​	导入jar包 		编写工具类		连接池实现类	BasicDataSource



C3P0连接池			

​	导入jar包		编写工具类		连接池实现类	combopooleddatasource		数据库配置文件	c3p0-config.xml	配置文件名称是固定的	使用c3p0连接池，会首先加载配置文件	 



Druid连接池

导入jar包		编写工具类		连接池实现类	

获取数据库连接池对象

* 通过工厂类DruidDataSourceFactory的createdatasource方法获取实现类
* createDataSource(Properties p)方法参数是一个属性集对象

数据库的配置文件	Druid.properties		手动读取配置文件



常见的参数		

user 	用户名	

password	密码

driverClass	驱动

jdbcurl	路径



instialPoolSize	初始化连接数

maxPoolSize	最大连接数

minPoolSize	最小连接数

maxIdleTime	最大空闲时间





## 2. DBUtils工具类

### 1. 简介

对我们的jdbc进行的简单封装的库，可以简化开发，同时也不会影响性能



### 2.使用： 		

导入jar包



### 3.DBUtils核心类

1. QueryRunner	对SQL进行执行
2. ResultSetHandler接口，对结果集进行封装的
3. DBUtils类，工具类，关闭资源操作事务相关的方法



### 4.案列相关的知识

1. 表和类的关系

​	一个类对应一张数据库表		

​	表中的字段就对应了类中的成员变量

​	表中你的一条数据对应Java类的对象	



2. JavaBean类的特点		主要是用来封装数据的

​	JavaBean类就是用来从表中获取的数据的类，封装数据的类

	1. 需要实现序列化接口，serializable（可省略）
	2. 私有化变量
	1. 提供getter  setter
	1. 提供空参构造



	### 5.核心类QUeryrunner

query() 方法	执行查询

构造方法		：		

QueryRunner()	手动

query(Connection con, String sql, ResultSetHandler<T> rsh,  Object...params)		

QueryRunner(DataSource	ds)			自动模式创建			提供数据库连接池对象



update() 方法	执行修改

update(Connection con, String sql ,Object... params)



### 6.结果集封装对象	ResultSetHandler接口

对查询出的数据，进行处理

根据我们查询的结果所返回的类型不同，选择对应的封装类

实现类

1. ArrayHandler		将结果集第一条记录封装到Object[]数组中
2. ArrayListHandler   将结果集每一条记录封装到数组中，再将数组封装到集合中
3. BeanHandler         将结果集第一条记录封装到JavaBean中                     查询一条数据封装到Java对象中
4. BeanListHandler    将结果集中每一条记录封装到JavaBean中，再将JavaBean封装到List集合中                                           比如查询出来一个集合，集合中放得对象    
5. MapHandler            将结果集中第一条记录封装到Map集合中，key就是字段名，value就是字段值
6. ScalarHandler        用于封装单个数据，如select count(*) from 表





## 3.数据库批处理		常用在批量添加中

批处理就是一次执行多条语句		相比于一次一次执行效率高



常用的方法	Statement和PrepareStatement都支持批处理操作

通过addBatch方法把要执行的多条SQL添加好，再调用executeBatch()方法去统一执行



void  addBatch()		将给定的SQL命令添加到Statement对象中	批量添加			

int[] executeBatch()方法		统一执行列表中的SQL



## 4.数据库元数据

### 查询结果信息：	update或delete语句		受影响的记录数

数据库和数据表的信息：	包含了数据库及数据表的结构信息 

mysql服务器相关信息：	包含了数据库服务器的当前状态、版本号等



### 元数据的相关命令

select version();		获取mysql服务器的版本

show status;			查询数据库的的状态



### Java中访问元数据

元数据类		

* DataBaseMetaData		描述数据库的元数据对象
* ResultSetMetaData         描述结果集的元数据对象



getMetaData()	获取元数据对象

* Connection 连接对象，调用getMetaData()方法，获取的是DataBaseMetadata	数据库元数据对象
* PrepareStatement 预处理对象调用getMetaData方法， 获取的是 ResultSetMetaData   结果集元数据对象



* DatabaseMetaData方法

getURL()	获取数据库URL		getUserName()	获取数据库用户名..



* ResultSetMetadata方法

  getColumnCount();		当前结果集共有多少列

  getColumnName(int i );		获取指定列号的列名

  getColumnTypeName(int i);   获取指定列号的列的类型



