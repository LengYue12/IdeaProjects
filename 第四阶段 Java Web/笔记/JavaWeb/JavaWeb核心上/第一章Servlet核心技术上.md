# Servlet核心技术上

# 1. 基本概念（常识）

* CS架构，客户端和服务器的架构
* BS架构，浏览器和服务器的架构
* JavaWeb，使用Java语言进行动态Web资源开发技术的统称
* 在互联网上的资源：
  * 静态资源：内容固定不变的资源
  * 动态资源：在浏览的过程中内容可以动态的发生改变的资源，不同时间点访问页面看到的内容各不相同



# 2. HTTP协议（熟悉）

HTTP协议，就是超文本传输协议，就是用来规范**浏览器与Web服务器之间通信的规则**，浏览器与服务器交流用的HTTP协议



## 2.1 HTTP请求格式

* 当通过浏览器向服务器发送HTTP请求时，发送的数据的格式：**请求行，请求头，空白行和请求体**

```
请求行用来说明请求类型和要访问的资源以及所使用的HTTP版本，格式如下：
 请求类型 请求的路径 协议的版本(1.1)
请求头是紧接着请求行（即第一行）之后的部分，用来说明服务器要使用的附加信息，格式
（key:value）如下：
 主机 请求长度 请求的浏览器相关信息
空白行就是请求头部的空行，即使后面的请求数据为空则必须有空行。
请求体也叫请求数据，可以添加任意的其他数据。
```

* 举例

```
POST /task01_demo01/demo1.html HTTP/1.1
Host: localhost:8088
Content-Length: 21
Cache-Control: max-age=0
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64)
name=scott&pwd=123456
```



## 2.2 HTTP响应格式

* 服务器接收并处理客户端发来的请求后返回的HTTP响应格式：**响应行，响应头，空白行，响应体**

```
响应行用来说明HTTP协议版本号和状态码以及状态消息，格式如下：
 协议的版本(1.0 1.1) 状态码 (200 成功 404 路径错误 500 服务错误) 状态信息
响应头用来说明客户端要使用的一些附加信息，格式（key:value）。
空白行就是响应头部的空行，即使后面的请求数据为空则必须有空行。
 
响应体用来服务器返回给客户端的文本信息。 
```

* 举例

```
HTTP/1.1 200 OK 
Content-Type: text/html
Content-Length: 588
Date: Thu, 08 Sep 2021 12:59:54 GMT
<html><head><title>示例1</title></head>
<body><h1>这是一个HTML页面</h1></body>
</html>
```



# 3. Tomcat服务器（重点）

Tomcat服务器是一个开源的轻量级Web应用服务器，（服务器软件），是开发和调试Servlet、JSP程序的首选。



# 4. Servlet的概念和使用（重点）

## 4.1 基本概念

* Servlet就是运行在服务器上的Java类 
* Servlet用来完成BS架构下客户端请求的响应处理，也就是交互式地浏览和生产数据，生产动态Web内容



## 4.2 Servlet的编程步骤

* 建立一个Java Web Application项目并配置Tomcat服务器。
* 自定义类实现Servlet接口或继承 HttpServlet类（推荐） 并重写service方法。
* 将自定义类的信息配置到 web.xml文件并启动项目，配置方式如下：

```jsp
<!-- 配置Servlet -->
<servlet>
  <!-- HelloServlet是Servlet类的别名 -->
<servlet-name> HelloServlet </servlet-name>
  <!-- com.lagou.task01.HelloServlet是包含路径的真实的Servlet类名 -->
<servlet-class> com.lagou.task01.HelloServlet </servlet-class>
</servlet>
<!-- 映射Servlet -->
<servlet-mapping>
  <!-- HelloServlet是Servlet类的别名，与上述名称必须相同 -->
<servlet-name> HelloServlet </servlet-name>
  <!-- /hello是供浏览器使用的地址 -->
<url-pattern> /hello </url-pattern>
</servlet-mapping>
```

* 在浏览器上访问的方式为：

```
http://localhost:8080/工程路径/url-pattern的内容
```



## 4.3 Servlet创建方式

* 实现Servlet接口并重写方法
* 继承GenericServlet类并重写service方法
* 继承**HttpServlet**，必须至少重写一个方法



## 4.4 Servlet的生命周期

* 当第一次请求 Servlet 时会调用一次构造方法实现实例的创建。
* 当创建好 Servlet 实例后立即调用 init 方法实现 Servlet 的初始化。
* 每当有请求时都会调用 service 方法来用于请求的响应。
* 当该 Servlet 实例所在的 Web 应用被卸载前会调用一次 destroy 方法释放资源



## 5 POST和GET请求（重点）

* GET请求	

```
发出GET请求的主要方式：
	（1）在浏览器中输入url
	（2）点击<a>超链接
	（3）点击submit按钮，提交<form method="get">表单
GET请求特点：
	会将请求数据添加到URL地址的后面，只能提交少量的数据，不安全
```

* POST请求

```
发出POST请求的方式：
	点击submit按钮，提交<form method="post">表单
POST请求的特点：
	请求的数据添加到HTTP协议体中，可提交大量数据、安全性好
```



* ServletRequest接口主要用于放浏览器向服务器发送请求的数据，**向Servlet提供客户端请求信息，就是把浏览器发送请求的所有相关信息打包发给服务器**，传给Servlet，可以从站获取到任何请求信息

* Servlet容器创建一个ServletRequest对象，作为参数传递给Servlet的service方法，而service方法里面又根据请求方式不同去调doGet和doPost方法

* 父接口ServletRequest 的常用的方法 获取请求信息的各种参数

  * String getParameter(String name) 返回请求参数的值
  * String[] getParameterValues(String name) 根据参数指定的请求参数名来获取对应的数值
  * Enumeration<String> getParameterNames()  把所有请求参数的名称取出
  * Map<String,Stirng[]> getParameterMap()   获取请求参数的键值对，请求参数的名称和对应值组成的键值对
  * String getRemoteAddr()       获取发送请求的客户端的IP地址
  * int getRemotePort()              获取客户端的端口号

* 子接口 HttpServletRequest接口的 常用的方法

  * String getRequestURI()        返回此请求的资源路径信息      指的是项目名+资源路径
  * StringBuffer getRequestURL()       返回此请求的完整路径信息
  * String getMethod()                      返回此请求的请求方式
  * String getQueryString()             返回路径后面请求中附带的参数
  * String getServletPath()              返回此请求中调用Servlet的路径部分

  

  

* ServletResponse接口用来将 **服务器发送给客户端进行响应的相关数据的打包**，将响应的相关信息打包        服务器向浏览器发回的数据内容

  * 常用的方法	
  * PrintWriter getWriter()                   获取PrintWriter对象，可以向浏览器发送文本数据
  * String getCharacterEncoding()        获取响应内容的编码方式
  * void setContentType(String type)    设置文本类型和字符集

* HttpServletResponse接口

  * void sendRedirect(String location)    



# 6. Servlet接收乱码的处理方式（重点）

前后台进行通信时，数据的请求和相应时要注意中文乱码问题

* 解决POST接收乱码

```
接收之前设置编码方式：
	request.setCharacterEncoding("utf-8")
必须在调用request.getParameter("name")之前设置
```

* 解决GET接收乱码

```
将接收到的中文乱码重新编码:
 // 接收到get请求的中文字符串
 String name = request.getParameter("name");
 // 将中文字符重新编码，默认编码为ISO-8859-1
 String userName = new String(name.getBytes(“ISO-8859-1”),“utf-8");
```



# 7. ServletConfig接口(熟悉)

就是用于描述Servlet本身的相关配置信息，可以描述在创建Servlet时的一些配置或初始化信息，**获取Servlet初始化配置信息**

* 配置方式

```
<!-- 在web.xml中配置ServletConfig初始化参数 -->
<servlet>
  <servlet-name>actionservlet</servlet-name>
  <servlet-class>com.lagou.demo01.ActionServlet</servlet-class>
  <!-- 配置 Serlvet 的初始化参数 -->
  <init-param>
    <!-- 参数名 -->
    <param-name>config</param-name>
    <!-- 参数值 -->
    <param-value>struts.xml</param-value>
  </init-param>
</servlet>
```

* 常用的方法
  * String getServletName()  **返回Servlet的别名**
  * String getInitParameter(String name)             **获取初始化参数信息**           返回包含初始化参数值的字符串，如果该参数不存在，则返回null
  * Enumeration   getInitParameterNames()   将servlet的初始化参数的名称作为字符串对象的枚举返回，如果
    servlet没有初始化参数，则返回空枚举
  * ServletContext   getServletContext()       返回对调用方正在其中执行的**ServletContext的引用**



# 8. ServletContext接口（熟悉）

服务器容器在启动时会为每个项目创建唯一的一个ServletContext对象，用于实现多个Servlet之间
的信息共享和通信

在Servlet中通过this.getServletContext()方法可以获得ServletContext对象。

* 配置方式    不隶属于某个Servlet， 多个Servlet之间共享的信息

```xml
<!--在web.xml中配置ServletContext初始化参数 -->
<context-param>
	<param-name>username</param-name>
	<param-value>scott</param-value>
<context-param>
<context-param>
	<param-name>password</param-name>
	<param-value>tiger</param-value>
<context-param>
```

* 常用的方法
  * String getInitParameter(String name)        **根据参数名获取对应的初始化参数值**
  * Enumeration getInitParameterNames()   **获取所有的初始化参数名**
  * String getRealPath(String path)              根据虚拟路径返回实际路径         **获取到的是部署工程路径信息    对应  当前工程中的web目录**
  * String getContextPath()                            返回上下文关联的主路径          **本质上就是获取工程路径**     **/工程名**
  * InputStream  getResourceAsStream(String  path)           根据路径得到输入流，读取相关的数据内容
  * void setAttribute(String name, Object object)                 将name和Object组成一个键值对放到当前对象中       **就是存放键值对信息**
  * Object getAttribute(String name)                                     根据name对应的Object对象取出来
  * void removeAttribute(String name)                                 删除指定的属性名信息



