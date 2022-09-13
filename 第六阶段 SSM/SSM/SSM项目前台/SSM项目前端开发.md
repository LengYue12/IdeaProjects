# SSM项目前端开发

# Nginx

轻量级的Web服务器，并发能力强，性能好。支持热部署

## 应用场景

1. **http服务器**: Nginx是一个http服务可以独立提供http服务。可以做网页静态服务器。
2. **虚拟主机**: 可以实现在一台服务器虚拟出多个网站。例如个人网站使用的虚拟主机。
3. **反向代理，负载均衡** :  当网站的访问量达到一定程度后，单台服务器不能满足用户的请求时，需要用多台服务器集群可以使用nginx做反向代理。并且多台服务器可以平均分担负载，不会因为某台服务器负载高宕机而某台服务器闲置的情况。



## Nginx安装

### 安装环境配置

```
需要安装gcc的环境。执行命令: 
yum install gcc-c++

安装命令:
yum install -y pcre pcre-devel

安装命令:
yum install -y zlib zlib-devel

安装命令:
yum install -y openssl openssl-devel
```

### 安装Nginx

把Nginx源码包上传到Linux

解压

```
tar -xvf nginx-1.17.8.tar 
```

进入到解压之后的目录 nginx-1.17.8

执行命令 configure,生成 Mikefile 文件 

```shell
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi
```

创建临时文件目录

```
mkdir /var/temp/nginx/client -p
```

执行make命令,进行编译

```
make
```

安装

```
make install
```

### 启动并访问Nginx

进入到nginx 安装目录

```
cd /usr/local/nginx/
```

进入到 sbin目录,执行 `nginx` 命令

```
./nginx 启动
./nginx -s stop 关闭
ps aux | grep nginx 查看进程
```

通过浏览器进行访问  ,192.168.142.128 默认端口 80  (注意：是否关闭防火墙。)





## 配置虚拟主机

虚拟主机指的是,在一台服务器中,我们使用Nginx,来配置多个网站.

如何区分不同的网站:

1. 端口不同
2. 域名不同 



### 通过端口区分不同的虚拟主机

****

Nginx配置文件的位置

```
cd /usr/local/nginx/conf
nginx.conf 就是Nginx的配置文件
```

Nginx核心配置文件说明

```shell
worker_processes  1; #work的进程数，默认为1
#配置 影响nginx服务器与用户的网络连接
events {
    worker_connections  1024; #单个work 最大并发连接数
}

# http块是配置最频繁的部分 可以嵌套多个server，配置代理，缓存，日志定义等绝大多数功能
http {
	# 引入mime类型定义文件
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65; # 超时时间
	
	#server 配置虚拟主机的相关参数 可以有多个,一个server就是一个虚拟主机
    server {
		# 监听的端口
        listen       80; 
		#监听地址
        server_name  localhost;         

		# 默认请求配置
        location / {
            root   html; # 默认网站根目录
            index  index.html index.htm; # 欢迎页
        }

		# 错误提示页面
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
}
```

##### 使用Notpad,连接Linux

​	使用notepad++来连接linux，好处是使用notepad++来编辑linux中文件的批量文字，会比直接在linux中操作方便快捷很多.

1. Notepad 插件中安装NppFTP



##### 配置nginx.conf

1. 使用Notpad 在nginx.conf 中添加一个 新的server

```shell
http {
    include       mime.types;
    default_type  application/octet-stream;


    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        server_name  localhost;

        location / {
            root   html;
            index  index.html index.htm;
        }
    }
	
	# 配置新的server
	server {
        listen       81; # 修改端口
        server_name  localhost;

        location / {
            root   html81; # 重新制定一个目录
            index  index.html index.htm;
        }
    }

}
```

复制一份 html目录

```
cp -r html html81
```

重新加载配置文件

```
sbin/nginx -s reload
```

访问

```
http://192.168.142.128/ 访问第一个server

http://192.168.142.128:81/ 访问第二个server
```





### 通过域名区分不同的虚拟主机

域名绑定

- 一个域名对应一个ip地址，一个ip地址可以被多个域名绑定。
- 通过 DNS服务器去解析域名

##### 配置域名映射

1. 本地测试可以修改hosts文件。修改window的hosts文件：（C:\Windows\System32\drivers\etc）

- 可以配置域名和ip的映射关系，如果hosts文件中配置了域名和ip的对应关系，不需要走dns服务器。

```
配置一下nginx的映射
192.168.142.128 www.ng.com
```



使用SwitchHosts,修改hosts

配置IP与域名的映射 

```
# nginx
192.168.142.128 www.t1.com
192.168.142.128 www.t2.com
```

配置nginx.conf

```
#一个server就代表了一个虚拟主机 ，可以配置多个
    server {
        listen       80;
        server_name  www.t1.com;

        location / {
            root   html-t1;
            index  index.html index.htm;
        }
	}
	
	
	#设置域名区分 不同的虚拟主机
	server {
        listen       80;
        server_name  www.t2.com;

        location / {
            root   html-t2;
            index  index.html index.htm;
        }
	}
```

创建  html-t1和 html-t2 目录

```
cp -r html html-t1
cp -r html html-t2
```

修改一下index.html 中,刷新

```
sbin/nginx -s reload
```

访问



**虽然只有一台服务器,但是这台服务器上运行着多个网站,访问不同的域名 就可访问到不同的网站内容**





## 反向代理

代理其实就是一个中介，A和B本来可以直连，中间插入一个C，C就是中介。刚开始的时候，代理多数是帮助内网`client`访问外网`server`用的.



### 正向代理

**正向代理代理的是客户端,代理服务器和客户端是一体的。 服务端不知道实际发起请求的客户端.**



### 反向代理

反向代理和正向代理的区别就是：**正向代理代理客户端，反向代理代理服务器。**

​	反向代理是指用代理服务器接收客户端的请求，然后将请求转发给网站内部应用服务器，并将从服务器上得到的结果返回给客户端.

**反向代理代理服务器，代理服务器和后台应用服务器是一体的**



## Nginx实现反向代理

Nginx作为反向代理服务器安装在服务端，Nginx的功能就是把请求转发给后面的应用服务器.



* 配置步骤

  1. 第一步：简单的使用2个tomcat实例模拟两台http服务器，分别将tomcat的端口改为8080和8081

  2. 第二步：启动两个tomcat。

  3. ```
     ./bin/startup.sh 
     访问两个tomcat
     http://192.168.142.128:8080/
     http://192.168.142.128:8081/ 
     ```

  4. 第三步：反向代理服务器的配置

  5. ```shell
     	upstream lagou1{
        		# 用server定义http的地址
        		server 192.168.114.128:8080;
        	}
        	
        	#反向代理的配置
        	server {
             listen       80;
             server_name  www.niubi.com;
        
             location / {
        		#可以将我们的请求代理到 对应的upstream
                 proxy_pass http://lagou1;		# 转发的地址
                 index  index.html index.htm;
             }
        	}
     ```
  
  
     	upstream lagou2{
     		# 用server定义http的地址
     		server 192.168.114.128:8081;
     	}
     	
     	#反向代理的配置
     	server {
             listen       80;
             server_name  www.niubi2.com;
  
             location / {
     		#可以将我们的请求代理到 对应的upstream
                 proxy_pass http://lagou2;		# 转发的地址
                 index  index.html index.htm;
             }
   	}
   ```
  
     ```
  
6. 第四步：nginx重新加载配置文件
  
  7. ```
   nginx -s reload
     ```
  
  8. 第五步：配置域名, 在hosts文件中添加域名和ip的映射关系
  
9. ```
     192.168.142.128 www.lagou1.com
     192.168.142.128 www.lagou2.com
     ```
  
     > 通过浏览器输入域名, 访问Nginx代理服务器, Nginx根据域名将请求转发给对应的目标服务器,作为用户我们看到的是服务器的响应结果页面,在整个过程中目标服务器相对于客户端是不可见的,服务端向外暴露的就是Nginx的地址.

**发送的请求是经过反向代理nginx服务器 转发到后台tomcat的**



## 负载均衡

### 什么是负载均衡

将请求合理地分发给不同的服务器就是负载均衡，nginx不仅是反向代理服务器，也是负载均衡器

**将大量的请求合理的发送给不同的服务器**

### 为什么用负载均衡

当系统面临大量用户访问，负载过高的时候,通常会使用增加服务器数量来进行横向扩展, 负载均衡主要是为了分担访问量，将请求合理分发给不同的服务器, 避免临时的网络堵塞

**分担访问量**

### 负载均衡策略

如果客户端有大量请求到nginx，如何让nginx将这些请求平均地分发到后台的应用服务器中？**就要配置负载均衡策略**

##### 轮询发，平均分配

* 访问的时候，后台有两个tomcat处理请求

- 默认策略, 每个请求按照时间顺序逐一分配到不同的服务器,如果某一个服务器下线,能自动剔除

```
#配置负载均衡，结合反向代理
	upstream dalij{
		# 用server定义http的地址
		#相当于有两台服务器去处理请求了
		#当请求过来时，Nginx就会将请求平均的分发给两台服务器
		server 192.168.114.128:8080;
		server 192.168.114.128:8081;
	}
	
	#反向代理的配置
	server {
        listen       80;
        server_name  www.dalij.com;

        location / {
		#可以将我们的请求代理到 对应的upstream
            proxy_pass http://dalij;		# 转发的地址
            index  index.html index.htm;
        }
	}
```



#####  weight 权重

- **可以根据服务器的实际情况调整服务器权重。权重越高分配的请求越多，权重越低，请求越少。默认是都是1**.

```
#配置负载均衡，结合反向代理
	upstream dalij{
		# 用server定义http的地址
		#相当于有两台服务器去处理请求了
		#当请求过来时，Nginx就会将请求平均的分发给两台服务器
		server 192.168.114.128:8080 weight=1;
		server 192.168.114.128:8081 weight=10;
	}
	
	#反向代理的配置
	server {
        listen       80;
        server_name  www.dalij.com;

        location / {
		#可以将我们的请求代理到 对应的upstream
            proxy_pass http://dalij;		# 转发的地址
            index  index.html index.htm;
        }
	}	
```





# 项目部署与发布

## 后台项目部署

### Linux环境

1）需要安装的软件

| 软件   | 版本   |
| ------ | ------ |
| JDK    | 11     |
| Tomcat | 8.5    |
| MySql  | 5.7    |
| Nginx  | 1.17.8 |

2）关闭防火墙

3）使用SQLYog连接Linux上的MySQL, 导入SQL脚本 创建项目所需的数据库



### 后台项目打包发布

在平常开发的过程中，不同的环境中项目的相关配置也会有相关的不同，我们在不同的环境中部署就要手动修改为对应环境的配置，这样太麻烦了以及这样也会很容易出错。 

接下来我们就通过maven的相关配置来在打包时指定各个环境对应配置文件

#### 修改ssm-dao子模块

在项目的src/main/resources 下面创建**filter**目录, 再创建 **development.properties** ,  **product.properties** 两个文件

* development是开发配置内容

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///ssm_lagou_edu?characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
```

* product是正式配置内容	

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://192.168.142.128:3306/ssm_lagou_edu?characterEncoding=UTF-8
jdbc.username=LengYue
jdbc.password=LengYue@520
```

**配置jdbc.properties 文件**

jdbc.properties中的内容不再写死,而是从上面两个文件中获取

```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=${jdbc.url}
jdbc.username=${jdbc.username}
jdbc.password=${jdbc.password}
```

> 注意：${jdbc.url} 直接对应上面配置的development.properties或product.properties文件中的名称。

**配置dao模块的的 pom.xml文件**

```xml
 <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <!-- 测试环境 -->
                <env>development</env>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <!-- 正式环境 -->
                <env>product</env>
            </properties>
        </profile>
    </profiles>

    <build>
        <finalName>web</finalName>
        <filters>
            <filter>src/main/resources/filter/${env}.properties</filter>
        </filters>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <excludes>
                    <exclude>filter/*.properties</exclude>

                </excludes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

#####  profile说明

- profile可以让我们定义一系列的配置信息，然后指定其激活条件。这样我们就可以定义多个profile，然后每个profile对应不同的激活条件和配置信息，从而达到不同环境使用不同配置信息的效果
- 默认启用的是dev环境配置:

```xml
 <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <!-- 测试环境 -->
                <env>development</env>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <!-- 正式环境 -->
                <env>product</env>
            </properties>
        </profile>
    </profiles>
```

指定数据库配置文件路径，此路径可以自定义:

```xml
<filters>
    <!--            动态地获取properties文件-->
	<filter>src/main/resources/filter/${env}.properties</filter>
</filters>
```

指定资源目录路径

```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <!-- 资源根目录排除各环境的配置 -->
        <excludes>
        	<exclude>filter/*.properties</exclude>
        </excludes>
        <filtering>true</filtering>
    </resource>
</resources>
```

####  打包

- 命令打包

```
打本地包 mvn -Pdev install 或者mvn install(因为本例activeByDefault配的为true)
打产品包 mvn -Pprod install

结果：src/main/resources/config/jdbc.properties根据 mvn -P 参数决定值
```

* 使用idea打包

选择打包方式，dev开发环境配置文件，prod 生产环境配置文件

选择父工程，执行package就可以完成打包

**发布**

修改项目名

上传





## 前台项目部署

### 修改配置文件

生产环境配置文件,配置后台URL

```
VUE_APP_NAME = Edu Boss
VUE_APP_TITLE = Lagou Edu Boss (Dev)

VUE_APP_STORAGE_PREFIX = lagou_edu_boss_dev

#VUE_APP_API_FAKE = /front
VUE_APP_API_FAKE = http://192.168.142.128:8080/ssm-web

#VUE_APP_API_BASE = /boss
VUE_APP_API_BASE = http://192.168.142.128:8080/ssm-web
```

自定义配置文件,配置打包相关信息

vue.config.js

```js
module.exports = {
  publicPath: process.env.NODE_ENV === "production" ? "/edu-boss/" : "/",
  indexPath: "index.html",
  assetsDir: "static",
  lintOnSave: process.env.NODE_ENV !== "production",
  productionSourceMap: false,
  devServer: {
    open: true,
    port: 8081
  }
};
```



### 打包

npm run build

在项目下会生成一个 dist 目录.





### 发布项目

解压一个新的tomcat, 修改端口号

```
#解压
tar xvf apache-tomcat-8.5.50.tar 
#改名
mv apache-tomcat-8.5.50 ui-tomcat
```

```
#修改端口号
cd ui-tomcat/conf/
vim server.xml 
```

上传前端项目到 webapps

```
//上传 edu-boss.zip ,并解压
unzip edu-boss.zip 

//删除edu-boss.zip
rm -rf edu-boss.zip
```

运行前端项目,并访问

```
./bin/startup.sh 

//动态查看日志
tail -f logs/catalina.out 

//访问
http://192.168.142.128:8081/edu-boss/
```





## 修改Tomcat默认访问项目

使用notpad打开前端tomcat的配置文件 server.xml, 找到 `Host` 标签

在Host标签内加入

```xml
<Context path="" docBase="edu-boss" reloadable="true" debug="0" privileged="true">
</Context>
```

重新启动 并访问前端项目,这个时候只需要直接访问 8081即可，直接**访问/ 就访问到了项目**

```
http://192.168.114.128:8081/
```



## 配置反向代理

使用notpad打开nginx的配置文件 nginx.conf

配置反向代理

```
#配置ssm项目 反向代理
	upstream lagouedu{
	#将请求转发到部署了前端项目的tomcat 8081端口
		server 192.168.114.128:8081;
	}
	
	server {
        listen       80;
		#默认监听地址
        server_name  www.edu-boss.com;

        location / {
			
            proxy_pass http://lagouedu;  #转发的地址
            index  index.html index.htm;
        }
    }
```

修改本地hosts, 配置域名映射

```
192.168.114.128 www.edu-boss.com
```

直接访问 www.edu-boss.com 域名