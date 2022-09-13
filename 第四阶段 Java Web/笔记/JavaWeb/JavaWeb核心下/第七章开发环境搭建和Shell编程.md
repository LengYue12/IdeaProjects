# 开发环境搭建和Shell编程

# 1. 在Linux系统中的开发环境的搭建(掌握)

## 1.1 Xshell和Xftp工具下载和安装			访问远程服务器

* Xshell远程终端软件，在本地访问远程服务器
  * Xshell远程终端连接服务器，	**只需要服务器的IP地址，用户名和密码**	
* Xftp进行本地主机和服务器之间的文件传输
  * Xftp向远程服务器传输文件
  * 建立当前主机和远程服务器之间的文件传输通道，从主机上传文件到服务器，从服务器下载文件到主机



## （3）网络模式设置

* 设置网络连接模式为NAT模式



## （4）修改配置文件

* 使用root用户打开/etc/sysconfig/network-scripts/ifcfg-eno16777736文件，添加内容如下:

```shell
BOOTPROTO=static
ONBOOT=yes			       重启生效
IPADDR=192.168.72.128		IP地址
GATEWAY=192.168.72.2        网关信息
NETMASK=255.255.255.0		子网掩码
DNS1=114.114.114.114  		DNS
```



## (5) 配置文件生效		重启网络服务

```
使用命令使得配置文件生效：service network restart
```



##  1.2 在Linux系统中JDK的下载和安装

## （1）下载和安装方式		

## 把JDK安装到远程服务器上，就得先把文件通过Xftp工具传输到远程服务器中，再解压就行

* 下载Linux版本JDK
* 安装方式：**将下载好的jdk安装包通过Xftp工具传输到CentOS系统中，使用tar命令解压即可**
  * 将jdk放到usr目录下，放软件的目录usr

## （2）配置环境变量

* 使用root用户打开配置文件/etc/profile，向文件末尾追加内容：

```shell
export JAVA_HOME=/usr/javajdk
export PATH=$JAVA_HOME/bin:$PATH
```

* 保存退出后让文件生效并验证是否配置成功

```shell
source /etc/profile		让配置文件生效
javac -version			查看javac版本
```



## 1.3 在Linux系统中实现Tomcat的下载和安装

## （1）下载和安装方式

* 去Tomcat官网下载Linux版本
* 安装方式：**将下载好的Tomcat安装包通过Xftp工具传输到CentOS系统中，使用tar命令解压即可**
  * 将jdk放到usr目录下，放软件的目录usr

## （2）启动和关闭方式

```
startup.sh
shutdown.sh
```

## （3）要想在本地访问服务器需要开放防火墙端口

```
/sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT 开启8080端口（暂时开通）
```

## （4）配置环境变量					

## 在Linux系统下不做环境变量配置，为了搭建集群

* 使用root用户打开配置文件/etc/profile，向文件末尾追加内容

```shell
export CATALINA_HOME=/usr/tomcat
export PATH=$CATALINA_HOME/bin:$PATH
```

* 保存退出后让文件生效并验证是否配置成功

```shell
source /etc/profile
startup.sh
```

## （5）发布Web项目

* 将Web项目打成war包，通过Xftp工具将war包放在tomcat/webapps目录并启动



## 1.4 MySQL的下载和安装		  	在线方式安装，全自动

## （1）下载MySQL的repo源			（下载快照文件）

```shell
wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm
```

## （2）安装rpm包				（解析快照文件,安装步骤）

```shell
rpm -ivh mysql57-community-release-el7-8.noarch.rpm
```

## （3）安装MySQL

```shell
yum install mysql-server
```

* 安装mysql时提示Failing package is: mysql-community-client-5.7.38-1.el7.x86_64
   GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
* 原因是mysql的GPG升级了，需要重新获取，使用以下命令即可：

```shell
rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
```

* 再执行

```shell
yum install mysql-server
```

* 查看信息

```shell
rpm -qa | grep mysql
```

## （4）启动服务

* ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' (2)
  * 没有启动服务
* 启动服务：

```shell
service mysqld start
```

* ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)
  * 没有指定密码
* **找到默认生成的随机密码**

```shell
grep password /var/log/mysqld.log
```

## （5）查看服务状态

```shell
systemctl status mysqld
```

## （6）使用root用户登录

```shell
mysql -u root
```

## （7）修改临时密码

```shell
alter user 'root'@'localhost' identified by 'QiDian@666';
```



## 1.5 使用图形化界面访问数据库

## （1）使用SQLyog工具

* **启动图形化界面工具SQLyog连接虚拟机中MySQL数据库**
* 若出现错误1130

## （2）解决方案

* 使用root权限登录数据库后选择mysql数据库

```shell
mysql -u root -p
use mysql;
```

* 查看mysql数据库中的user表的host值后修改为通配符%

```shell
select host from user where user='root';
update user set host='%' where user='root';
flush privileges;
```

* 查看修改结果并重新测试

```shell
select user,host from user;
```

* 若出现错误号码2003
* 关闭Linux防火墙
* 运行命令：

```shell
service firewalld stop
```





# 2 Shell编程（熟悉）

## 2.1 基本概念

* Shell就是一个命令解释器，可以接收应用程序或用户命令，然后访问操作系统内核。执行命令，执行完毕后把执行结果显示
* Shell是一个功能强大的编程语言
* 注释是#号
* Shell程序中每一行没分号
* **$取变量数值**

## 2.2 编写第一个程序

* 使用vi工具创建xxx.sh的文件
* **以#!/bin/bash开头并编写代码后保存**

## 2.3 执行Shell程序的方式

* 方式一：./文件名，此方式需要执行权限
* 方式二：/bin/bash 文件名，此方式不需要执行权限
  * bash 文件名
  * sh 文件名          都可以

## 2.4 变量的定义

## （1）语法格式			在Shell中定义变量时=两边不能有空格

* 定义变量：变量=值
* 撤销变量：unset  变量

## （2）定义规则

* 变量名称可以由字母、数字和下划线组成，但是数字不能开头，环境变量名建议大写
* 不能使用bash里的关键字
* 中间不能有空格，可以有下划线
* 在bash中，变量默认类型都是字符串类型，无法直接进行数值运算
* 变量的值如果有空格，需要使用双引号或单引号括起来，但双引号中可以取出变量的数值
* **用双引号括起来的变量值可以在里面取别的变量的值，单引号则不行**

## 2.5 常用的运算符

## （1）算术运算符		expr要求算术运算符两边必须有空格，并且 要用`包裹

| 运算符 | 说明                                 | 举例                     |
| ------ | ------------------------------------ | ------------------------ |
| +      | 加法                                 | expr $a + $b             |
| -      | 减法                                 | expr $a - $b             |
| *      | 乘法                                 | expr $a \* $b            |
| /      | 除法                                 | expr $a / $b             |
| %      | 取余                                 | expr $a % $b             |
| =      | 赋值                                 | a=$b   将变量b的值赋给a  |
| ==     | 相等，用于比较两个数字，相同返回true | [ $a == $b ]  返回 false |
| !=     | 不相等，不相同返回true               | [ $a != $b ]             |

```shell
#!/bin/bash

#定义两个变量
ia=5
ib=2

#打印两个变量的数值
echo "ia=$ia"
echo "ib=$ib"

#三种方式
#实现加法运算，要求+号两边必须有空格
echo `expr $ia + $ib`
#使用其它方式实现加法运算
ic=$[$ia+$ib]
echo $ic
id=$(($ia+$ib))
echo $id
```



## （2）关系运算符

| 运算符 | 说明                             | 英文                      | 举例          |
| ------ | -------------------------------- | ------------------------- | ------------- |
| -eq    | 检测两个数是否相等，相等返回true | equal                     | [ $a -eq $b ] |
| -ne    | 是否不相等，不相等返回true       | not equla                 | [ $a -ne $b ] |
| -gt    | 大于                             | greater  than             | [ $a -gt $b ] |
| -lt    | 小于                             | less   than               | [ $a -lt $b ] |
| -ge    | 大于等于                         | Greater  than or equal to | [ $a -ge $b ] |
| -le    | 小于等于                         | Less than or equal to     | [ $a -le $b ] |

## 2.6 流程控制语句

## (1) if判断

```shell
if[ 条件判断式 ]
 then
 	程序
fi
```

```shell
#!/bin/bash

#定义一个变量用于描述考试成绩
score=60
echo $score

#使用if判断给出及格或者不及格的提示信息
if [ $score -gt 60 ]
then
     echo "恭喜您考试通过了！"
elif [ $score -eq 60 ]
then
     echo "60分万岁，多一分浪费！"
else
     echo "下学期来补考吧！"
fi
```

## (2) case语句

```shell
case $变量名 in
 "值1")
 	如果变量的值等于值1，则执行程序1
 	;;
 "值2")
 	如果变量的值等于值2，则执行程序2
 	;;
 ..省略其他分支..
 *)
 	如果变量的值都不是以上的值，则执行此程序
 	;;
esac
```

```shell
#!/bin/bash

#提示用户输入1~4之间的整数并记录到变量中
echo "请输入1~4之间的整数:"
read num        #表示读取一个整数放到变量num中

#使用case语句进行匹配和打印
case $num in
    1) echo "你选择了1号业务！"
    ;;
    2) echo "你选择了2号业务！"
    ;;
    3) echo "你选择了3号业务！"
    ;;
    4) echo "你选择了4号业务！"
    ;;
    *) echo "你没有选择1~4号之间的业务，你想干吗？"
esac
```

## (3) for循环

```shell
for((初始值;循环控制条件;变量变化))
do
程序
done
```

```shell
#!/bin/bash

#定义一个变量负责记录累加和
sum=0

#使用for循环计算1~100之间的累加和并记录到上述变量中
for(( i=1;i<=100;i++ ))
do
   sum=$[$sum+$i]
done
#打印变量的数值
echo $sum
```

## (4) while循环

```shell
while [ 条件判断式 ]
do
  程序
done
```

```shell
#!/bin/bash

#使用while循环实现1~100之间的累加和
#定义两个变量分别记录累加和与初始值
sum=0
i=1

#使用while循环
while [ $i -le 100 ]
do
   sum=$[$sum+$i]
   i=$[$i+1]
done

#打印结果
echo $sum
```



## 2.7 函数			就是在Shell中定义一个方法

中括号的内容表示可选

```shell
[ function ] funname[()]
{
   Action;							方法体
   [return int;]
}
funname								调用函数
```

```shell
#!/bin/bash

#定义一个函数负责计算两个输入数据的和并打印
function sum()
{
    s=$[$1+$2]
    echo $s
}

#提示用户从键盘输入两个数据
read -p "input num1:" num1
read -p "input num2:" num2

#调用函数
sum $num1 $num2
```

