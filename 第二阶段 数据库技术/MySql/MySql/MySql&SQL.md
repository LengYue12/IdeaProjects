# MySql&SQL

## 1.数据库

​	1.数据库就是存储和管理数据的仓库

​	2.本质是一个文件系统，以文件的方式将数据保存到电脑上

## 1.2 数据库可以永久存储数据，方便存储和管理



## MySQL的启动和关闭

* 方式一：Windows服务启动 MySQL

* 方式二：DOS命令方式启动

(1)管理员身份运行命令行

（2）启动MySQL

net start mysql57

(3)关闭MySQL

net stop mysql57

## 命令行登录数据库

​	MySQL是一个需要账户密码登录的数据库，默认的root账号，使用安装时设置的密码即可登录

1. mysql -u 用户名 -p密码			

mysql -uroot -p123456

2. mysql -h 主机IP -u用户名 -p密码

mysql -h127.0.0.1 -uroot -p123456

退出命令

exit 或者 quit

## 数据库管理系统

1. MySQL服务器就是一台安装了MySQL软件的电脑

2. MySQL中管理着多个数据库

3. 数据库中包含着多张表
4. 表中存放着多条记录

## 数据库表

* 数据库中以表为组织单位存储数据
* 表类似我们Java中的类，每个字段都有对应的数据类型

类 ----> 表

类中属性 ----> 表中字段

对象 ----> 数据记录

# 3. SQL(重点)

 ## 3.1 SQL的概念

​	结构化查询语言SQL,一种特殊目的编程语言，**操作关系型数据库的一门语言**

	* 是所有关系型数据库的统一查询规范，不同的关系型数据库都支持SQL
	* 所有的关系型数据库都可以使用SQL
	* 不同数据库之间的SQL有一些区别 方言



# 3.2 SQL的通用语法

1) SQL语句可以单行 或者 多行书写，以分号结尾;
2) 可以使用空格和缩进来增加语句的可读性
3) MySQL中使用SQL不区分大小写，一般关键字大写，数据库名 表名列名 小写
4) 注释方式

-- 空格 单行注释

/* */ 多行注释

# #MySQL特有的单行注释

# #show databases; 单行注释

-- show databases; 单行注释

/*

​	多行注释

​	show databases;		

*/

## 3.3 SQL的分类

数据定义语言	DDL	操作数据库和表

数据操作语言	DML	对表中的数据进行增删改操作

数据查询语言	DQL	查询表中的数据

数据控制语言	DCL	定义数据库的访问权限



## 3.4 DDL操作 数据库 CRUD C create 创建 R retrieve 查询 U update 修改 D delete 删除 使用数据库

### 3.4.1 创建数据库

create database 数据库名;	创建指定名称的数据库

create database 数据库名 character set 字符集;	创建指定名称的数据库，并且指定字符集utf8



### 3.4.2 查看/选择数据库

use 数据库	切换数据库

select database();	查看当前正在使用数据库

show databases;	查看MySQL中都有哪些数据库

show create database 数据库名; 查看一个数据库的定义信息



### 3.4.3修改数据库

修改数据库字符集

alter database 数据库名 character set 字符集;	数据库的字符集修改操作	



### 3.4.4 删除数据库

drop database 数据库名 从MySQL中永久的删除某个数据库



## 3.5 DDL 操作数据表

### 3.5.1MySQL中常见的数据类型：

​	int 整型

​	double 浮点型

​	varchar 字符串型

​	date 日期类型 只显示年月日 没有时分秒 yyyy-MM-dd

​	datetime 年月日时分秒 yyyy-MM-dd HH:mm:ss 

​	char 字符串型 

​	MySQL中的char类型和varchar类型都表示字符串类型，区别在于

* char类型是固定长度的：根据定义的字符串长度分配足够的空间

* varchar类型是可变长度的：只使用字符串长度所需的空间

  比如：保存字符串abc

  x char(10)占用10个字节

  y varchar(10)占用3个字节



### 3.5.2 创建表

语法格式：

 create table 表名(

​		字段名称1 字段类型(长度),

​		字段名称2 字段类型		

);

快速创建一个表结构相同的表(复制表结构)

语法格式: create table 新表名 like 旧表名



### 3.5.3查看表

show tables; 查看当前数据库中的所有表名

DESC 表名; 查看数据表的结构

show create table 表名; 查看创建表的SQL语句



### 3.5.4删除表

drop table 表名; 删除表 永久删除

drop table if exists 表名; 判断表是否存在，存在就删除，不存在就不删除



### 3.5.5修改表

rename table 旧表名 to 新表名	修改表名

alter table 表名 character set 字符集	修改表的字符集

alter table 表名 add 字段名称 字段类型	向表中添加列

alter table 表名 modify 字段名称 字段类型	修改表中列的数据类型或长度

alter table 表名 change 旧列名 新列名 类型(长度);	修改列名称

alter table 表名 drop 列名	删除列







## 3.6 DML 操作表中的数据

SQL中的DML用于对表中的数据进行增删改操作



### 3.6.1 插入数据

insert into 表名(字段名1，字段名2...) values(字段值1,字段2...);



向表中添加数据	3种方式

1. 插入全部字段，将所有字段名写出来

insert into student(sid, sname, age, sex, address) values(1, '孙悟空', 20, '男', '花果山');

2. 插入全部字段，不写字段名

insert into student values(2, '孙悟饭', 10, '男', '地球');

3. 插入指定字段值

insert into student(sid, sname) values(3, '孙悟天');



### 3.6.2 更改数据

不带条件的修改

update 表名 set 列名 = 值;

带条件的修改

update 表名 set 列名 = 值 [where 条件表达式: 字段名 = 值]



### 3.6.3删除数据

删除所有数据

delete from 表名;

指定条件 删除数据

delete from 表名 [where 字段名 = 值]

如果要删除表中的所有数据

1.delete from 表名;不推荐

2.truncate table 表名; 推荐





## DQL查询表中的数据

* 查询不对对数据库中的数据进行修改，只是一种显示数据的方式 



select 列名 form 表名



1. 查询表中所有的数据
   select * from 表名;



2. 查询表中所有记录，仅显示id和name字段

​	select eid, ename from 表名;



3. 别名查询

 	select 字段名 as '列名';



4. 查询一共有几个部门

去重

​	select distinct 列名 from 表名;





## 3.7.3条件查询

select 列名 from 表名 where 条件表达式



1）比较运算符

< > <=... 

between...and.. 显示在某一区间的值

in(集合) 集合中表示多个值，使用逗号分隔

like'%张%' 模胡查询

Is null  查询某一列为null的值



2）逻辑运算符

and && 多个条件同时成立

or || 多个条件任一成立

not  不成立，取反



模糊查询 通配符
% 	表示匹配任意多个字符串

_		表示匹配一个字符

