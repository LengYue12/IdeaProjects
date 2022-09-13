# MySql索引&视图&存储过程

# 1.MySql索引

我们可以通过对数据表中的字段创建索引	来提高查询速度

常见的索引的分类

​	主键索引(primary key) 主键是一个唯一性的索引 每个表中只能有一个主键	当字段成为了主键，就会自带一个主键索引

​	唯一索引(unique) 索引列的所有数据只能出现一次，必须是唯一

​	普通索引(index)	最常见的索引	作用就是提高对数据的访问速度



表对应的索引被保存在一个索引文件中，如果对数据进行增删改操作，那么MySql就需要对索引进行更新



## 1.2.1主键索引

1. 创建表的时候	直接添加主键
2. 创建表之后 添加索引 **使用DDL alter table 表名 add primary key(字段);**



## 1.2.2唯一索引

1. 创建表的时候 直接添加唯一索引

2. 使用create语句创建：在已有表上创建索引 create unique index 索引名 on 表名 (列名(长度)) 添加唯一索引的列，列的所有值只能出现一次

   唯一索引保证了数据的唯一性，同时也提升了查询效率

3. **创建表之后 添加索引 alter table 表名 add unique(列名**)



## 1.2.3普通索引

1. create index 索引名 on 表名(列名(长度))
2. **alter table 表名 add index 索引名(列名)**





## 1.2.4删除索引

alter table 表名 drop index 索引名; 



## 1.4索引优缺点总结

创建索引的原则

​	优先选择为 经常出现在 查询条件或者是排序 分组后面的字段 创建索引

索引的优点

1. 可以大大的提高查询速度
2. 减少查询中分组和排序的时间
3. 通过创建唯一索引保证数据的唯一性



索引的缺点

1. 创建和维护索引需要时间，数据量越大  时间越长
2. 表中的数据进行增删改操作时，索引也需要进行维护，降低了维护的速度
3. 索引文件需要占据磁盘空间



# 2.MySql视图

​	视图是由查询结果形成的一张虚拟的表



视图的作用

​	如果某个查询的结果出现的十分的频繁，并且查询语法比较复杂

​	那么这个时候，就可以根据这条查询语句构建一张视图	方便查询



视图的语法

​	**create view 视图名[字段列表] as select 查询语句;**

​	view 表示视图

​	字段列表 一般跟后面的查询语句 相同

​	as 表示给视图提供数据的 查询语句

操作视图 就相当于操作一张 只读表

视图主要是用来作查询操作的





视图与表的区别

1. 视图是建立在表的基础之上
2. 通过视图 不要进行增删改操作，视图主要就是用来简化查询
3. 删除视图 表不受影响，删除表 视图就不在起作用了



# 3.MySql 存储过程(了解)

存储过程其实就是一堆SQL语句的合并，中间加入了一些逻辑控制



## 3.1存储过程的优缺点

​	优点

​	1.调试完成就可以稳定运行(在业务需求相对稳定情况)

​	2.存储过程可以减少 业务系统与数据库的交互

​	缺点

​	1.互联网项目中 较少使用存储过程，因为 业务需求变化太快

​	2.存储过程的移植十分困难



## 3.2存储过程的创建方式

### 3.2.1方式1	创建简单的存储过程

(1)	delimiter $$ -- 声明语句的结束符号 自定义

​	create procedure	存储过程名称()	-- 声明存储过程

​	begin	-- 开始编写存储过程

​			-- 要执行的SQL

​	end $$	-- 存储过程结束

​	

(2)	需求：编写存储过程，查询所有商品数据

delimiter $$ 

create procedure goods_proc()

begin

​	select * from goods;

end $$



(3) 调用存储过程

call 存储过程名



### 3.2.2方式2

(1)	创建一个接收参数的存储过程

语法格式	表示调用者向存储过程传入值

​	create procedure 存储过程名(in 参数名, 参数类型)

(2)	需求：接受一个商品id，根据id删除数据

delimiter $$

create procedure goods_proc02(in goods_id)

begin

​	delete from goods where gid = goods_id;

end $$

(3)	调用存储过程 传递参数

call goods_proc02(1);



### 3.3.3方式3	获取存储过程的返回值

(1) 变量的赋值

​	set @变量名 = 值

(2)OUT	输出参数：表示存储过程向调用者传出值

​	OUT 变量名 数据类型

(3)创建存储过程

需求：向订单表插入一条数据，返回1，表示插入成功

delimiter $$

create procedure orders_proc(in o_oid int, in o_gid int, in o_price int, OUT out_num int)

begin

​	-- 执行插入操作

​	insert into orders values(o_oid, o_gid, o_price);

​	-- 设置 num的值为1

​	set @out_num = 1;

​	-- 返回out_num 的值

​	select @out_num;

end $$

(4) 调用存储过程

call orders_proc(1, 2, 30, @out_num);



# 4.MySql触发器(了解)

## 4.1触发器是由事件来触发的

**当我们执行一条SQL语句的时候，这条SQL语句的执行会自动触发执行其他的SQL语句**



## 4.2触发器创建的四个要素

1. 监视地点(table)	就是监视哪一张表
2. 监视事件(insert/update/delete)   监视对这张表做了哪些操作 增删改
3. 触发时间(before/after)   是在执行SQL之前去执行触发器还是在之后执行触发器的内容
4. 触发事件(insert/update/delete)   一旦触发器被触发之后要执行什么操作



## 4.3创建触发器

语法结构

​	delimiter $$   -- 自定义结束符号

create trigger 触发器名

after/before(insert/update/delete)	-- 触发的时机	和监视的事件

on	tableName	-- 触发器所在的表

for each row	-- 固定写法	表示行触发器

begin

​	-- 被触发的事件

end $$



# 5.DCL(数据控制语言)

​	MySql中默认使用root用户，root用户拥有全部权限。我们可以通过DCL语言定义一些权限较小的用户，去管理维护数据库



## 5.1 DCL创建用户

语法结构

​	create user '用户名'@'主机名'	identified	by	'密码';

用户名	创建的新用户，登录名称

主机名	指定该用户在哪个主机上可以登录，本地用户可用localhost，如果想让该用户可以从任意远程主机登录，可以使用通配符%

密码	登录密码	



(1)创建admin1用户，只能在localhost这个服务器登录MySql服务器，密码为123456

create user 'admin1'@'localhost' identified by '123456';



用户创建完保存到mysql数据库中的user表中



(2) 创建admin2用户可以在任何电脑上登录mysql服务器，密码为123456

create user 'admin2'@'%' identified by '123456';		-- %表示用户可以在任意电脑登录mysql服务器



##  5.2用户授权

创建好的用户，需要进行授权

语法格式

​	grant	权限1，权限2	...	on 数据库名.表名	to	'用户名'@'主机名';



权限	授予用户的权限，如create	alter 	select 	insert 	update等	如果要授予所有的权限则使用all

on	用来指定权限针对哪些库和表

to	表示将权限赋予某个用户



1. 给admin1用户分配对db4数据库中products表的操作权限	查询

   grant select  on db4.products to 'admin1'@'localhost';

2. 给admin2用户分配所有权限，对所有数据库的所有表

   grant all on * . * to 'admin2'@'%';



## 5.3查看用户权限

语法格式

​	show grants for '用户名'@'主机名';

查看root用户权限

show grants for 'root'@'localhost';



## 5.4删除用户

语法格式

​	drop user '用户名'@'主机名';

删除admin1用户

drop user 'admin1'@'localhost';



## 5.5查询用户

选择名为 mysql的数据库，直接查询user表

select * from user;





# 6.数据库备份&还原

1. Sqlyog方式

   选中要备份的数据库，右键 备份导出-->选择 备份数据库

2. 命令行方式

​	语法格式

​		备份	mysql dump -u用户名 -p密码 数据库名 > 文件路径

​	还原时需要创建一个数据库

​		还原	source	sql文件地址