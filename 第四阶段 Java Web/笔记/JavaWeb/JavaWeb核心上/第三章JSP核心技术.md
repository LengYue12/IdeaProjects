# JSP核心技术	

# 1 JSP的概述（熟悉）

##  1.1JSP的概念

* JSP就是Java 服务的页面，和Servlet一样可以对浏览器做出动态响应的技术
* 与Servlet不同，JSP文件**主要以HTML标记为主**，然后内嵌Java代码。
  * **Servlet里主要以Java代码为主，JSP主要以HTML标签为主**
* JSP主要是用来弥补Servlet不便于发送大量的数据内容的不足

## 1.2 JSP的示例

```jsp
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
 <head>
  <title>Hello Time</title>
 </head>
 <body>
 现在的时间是：<%= new Date()%>
 </body>
</html>
```

## 1.3 JSP与Servlet的关系

浏览器向服务器发送请求，当请求的是JSP页面时并不会立刻作出响应，而是通过转译，编译生成Servlet

由TomCat服务器将JSP转换成Servlet后再对浏览器作出响应



# 2. JSP的语法（熟悉）

## 2.1 JSP的语法结构

* 声明区
* 程序代码区
* 表达式
* 注释
* 指令和动作
* 内置对象

## 2.2 声明区

* 语法：
  * <%! %>
* 可以用来定义**全局**变量、方法、类

## 2.3 程序代码区

* 语法：
  * <% 程序代码区 %>
* 可以定义**局部**变量以及放入任何的Java程序代码

## 2.4 表达式

* 语法：
  * <%= ...%>
* 可以**输出**一个变量或具体的内容
* 不需要以分号结束，因为只有一行



JSP声明变量和输出

```jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试变量的声明和打印</title>
    <%!
        int ia; // 这是一个全局变量

        public void show(){
            System.out.println("这是全局方法！");
        }

        public class MyClass{
            {
                System.out.println("这是一个全局类哦！");
            }
        }
    %>

    <%
        int ib = 20; // 这是一个局部变量

        for (int i = 0; i < 3; i++) {
            System.out.println("随便放入Java程序代码吧！");
        }
    %>
</head>
<body>
<%= ia+1 %> <%-- 1 --%>
<%= ib %>   <%-- 20 --%>
<%= "我就暂时写到这里吧！"%>  <%-- 我就暂时写到这里吧！ --%>
</body>
</html>

```



## 2.5 注释		对代码进行解释说明的

```
格式：
<!-- -->	HTML文件的注释，浏览器可以查看到
<%-- --%>	JSP文件的注释，浏览器看不到
<%// %>		Java语言中的单行注释，浏览器看不到
<%/*   */%>	Java语言中的多行注释，浏览器看不到
注释的内容不会被执行
```

```jsp
<!-- 这是HTML文件中的注释方式，浏览器是可以看到的 -->
<%-- 这是JSP文件中的注释方式，该方式浏览器是看不到的 --%>
<%
    // Java语言中的单行注释 浏览器看不到哦
    /* Java语言中的多行注释 浏览器看不到哦*/
%>
```



## 2.6 指令和动作

* 指令格式：
  * <%@指令 属性="属性值"%>
* 指令的属性可以设定多个
* JSP常用的指令有：page、taglib、include



### （1）page指令

设置一些页面属性

```
import	导包
contentType	 内容的类型，可以设置发送给浏览器的内容的编码文件的类型，以及相关的编码方式
pageEncoding 设置当前页面的编码方式 
language	设置页面所使用的语言
session		设置是否创建HttpSession对象
errorPage	处理异常
isErrorPage	是否是异常的处理页面
```



### （2）taglib指令

引入其他的标签库文件

```jsp
<!-- prefix属性用于指定库前缀 -->
<!-- uri属性用于指定库的标识 -->
<%@taglib uri="tagLibary" prefix="prefix"%>
```



### (3) include指令 	在jsp中引入另外一个jsp时，或者HTML文件时。一种是include指令，一种是include动作		

包含，引入其他的JSP程序或者HTML文件

就是在当前JSP文件中可以引入另外一个JSP文件

```
<%@include file="被包含的文件地址"%>
```



### (4) jsp:include/jsp:param

* jsp:include动作用于引入另一个JSP程序或HTML文件等
* 执行到includ时，被include的文件才会被编译
* 如果include的是jsp文件，那它就不会被转换成Servlet文件

```jsp
<jsp:include page="URLSpec" flush="true"/>
<jsp:include page="URLSpec" flush="true"/>
	<jsp:param name="key" value="value"/>
</jsp:include>
```

### (5)include指令和include动作的区别

* include指令静态包含			其实就是先包含进来再编译
* include动作叫动态包含         就是编译完再包含



### （6）jsp:forward/jsp:param

forward动作用于在JSP中实现转发，将请求转发到另一个JSP文件或者Servlet中处理

```jsp
<jsp:forward page="urlSpec" flush="true"/>
<jsp:forward page="urlSpec">
	<!-- 用于指定参数和其对应的值 -->
	<jsp:param name="key" value="value"/>
</jsp:forward>
```





# 3. JSP内置对象（重点）

## 3.1 基本概念

* JSP里面本身就支持的对象，本身就准备好的对象。有9个内置对象，可以不需要定义，本来就有



## 3.2 对象名称

| 对象变量    | 对象类型            | 作用             |
| ----------- | ------------------- | ---------------- |
| out         | JSPWriter           | 输出流           |
| request     | HttpServletRequest  | 请求信息         |
| response    | HttpServletResponse | 响应信息         |
| session     | HttpSession         | 会话             |
| application | ServletContext      | 全局的上下文对象 |
| pageContext | PageContext         | JSP页面的上下文  |
| page        | Object              | JSP页面本身      |
| config      | ServletConfig       | Servlet配置对象  |
| exception   | Throwable           | 捕获网页异常     |



## 3.3 out内置对象

用来给浏览器输出信息

| 方法                   | 功能介绍                                 |
| ---------------------- | ---------------------------------------- |
| void println(String x) | 向客户端输出字符串                       |
| void newLine()         | 输出一个换行符                           |
| void close()           | 关闭流                                   |
| int getBufferSize()    | 返回缓冲区的大小                         |
| int getRemaining()     | 获取未使用的字节数                       |
| void flush()           | 输出缓冲区里的数据   刷新                |
| void clearBuffer()     | 清除缓冲区的数据，同时把数据输出到客户端 |
| void clear()           | 清除数据                                 |

```jsp
<%
	out.println("<h1>");
    out.println("Hello World!");
    out.prinlt("</h1>");
	
	int bufferSize = out.getBufferSize();
	System.out.println("缓冲区的总大小是：" + bufferSize);
	int remaining = out.getRemaining();
	System.out.println("缓冲区的剩余字节数为：" + remaining);
	System.out.println("已经使用的字节数为：" + bufferSize - remaining);
	
	out.clear();	// 清除缓冲区	数据不会输出
	remaining = out.getRemaining();
	System.out.println("缓冲区的剩余字节数为：" + remaining);	// 总大小
    out.close();
%>
```



### 3.4 request内置对象		中存储的数据只在一个请求中好使，换了别的请求就不行

* request对象打包所有的请求信息发送给服务器，是HttpServletRequest接口的实例
* 设置request对象里的属性后只在同一个请求中能用，必须是一个请求才可以           **该对象的属性值只在一个请求中保存**
* 方法

| 方法                                     | 功能介绍             |
| ---------------------------------------- | -------------------- |
| String getMethod()                       | 获取请求方式         |
| String getParameter(String name)         | 获取请求的参数值     |
| String[] getParameterValues(String name) | 获取指定参数的所有值 |
| String getRequestURI()                   | 获取请求路径         |
| String getRemoteAddr()                   | 获取客户端的IP地址   |
| int getRemotePort()                      | 获取客户端的端口号   |
| String getServerName()                   | 获取服务器的名字     |
| int getServerPort()                      | 获取服务器的端口     |
| void setAttribute(Stirng name, Object o) | 设置属性             |
| Object getAttribute(String name)         | 获取属性             |



## 3.5 response内置对象

* response对象是打包给浏览器的响应信息，是HttpServletResponse接口的实例
* 方法

| 方法                                     | 功能介绍               |
| ---------------------------------------- | ---------------------- |
| void addCookie(Cookie cookie)            | 添加Cookie对象         |
| void addHeader(String name,String value) | 添加响应头信息         |
| boolean containsHeader(String name)      | 判断是否包含响应头信息 |
| void sendRedirect(String location)       | 重定向                 |
| void setContentType(String type)         | 设置编码方式           |



## 3.6 session内置对象		只要浏览器不关闭，半个小时都可以使用，多个请求中可以共享数据

* session对象表示浏览器和服务器之间的一次会话，一次会话中可以包含多次请求，多次请求之间可以借用session存储。session是HttpServletSession类型的实例
* 该对象的属性值在一次会话范围中保存，保存在服务器端，只要不关闭浏览器，默认半个小时内都可以访问，浏览器关闭后会话就断开
* 方法

| 方法                                         | 功能介绍   |
| -------------------------------------------- | ---------- |
| void setAttribute(String name, Object value) | 设置属性值 |
| Object getAttribute(String name)             | 获取属性值 |



## 3.7 application内置对象	

* application对象是一个web程序的全局变量，application是ServletContext类型的实例
* 在整个服务器上保存，所有用户共享，多个浏览器之间可以共享

| 方法                                          | 功能介绍   |
| --------------------------------------------- | ---------- |
| void setAttribute(String name, Object object) | 设置属性值 |
| Object getAttribute(Stirng name)              | 获取属性值 |



## 3.8 pageContext内置对象

* pageContext对象是PageContext类型的对象，可以使用这个内置对象来管理其他的对象
* 存储的数据只在当前页面中有效，一个页面

| 方法                                                    | 功能介绍     |
| ------------------------------------------------------- | ------------ |
| void setAttribute(String name, Object value, int scope) | 设置属性值   |
| Object getAttribute(String name,int scope)              | 获取属性值   |
| ServletRequest  getRequest()                            | 获取请求对象 |
| ServletResponse  getResponse()                          | 获取响应对象 |
| HttpSession  getSession()                               | 获取会话对象 |
| ServletConfig  getServletConfig()                       | 获取配置对象 |
| JspWriter  getOut()                                     | 获取输出对象 |
| Object  getPage()                                       | 获取页面对象 |
| Exception getException()                                | 获取异常对象 |

**数据的存储范围：pageContext最小，request，session，application**



## 3.9 exception内置对象

* exception对象是Throwable的实例，表示JSP的异常信息
* 如果要使用它，必须将对应页面page指令的isErrorPage属性设置成true
* 单个页面的处理方式

```jsp
<%@page errorPage="error.jsp" %>
```

* 在web.xml中配置统一的异常处理页面。 **如果希望以后所有的JSP文件只要出现异常的统一交给error.jsp去处理就需要在配置文件中做一个统一的配置**

```xml
<error-page>
	<exception-type>java.lang.Throwable</exception-type>
	<location>/error.jsp</location>
</error-page>
```



# 4. JavaBean组件（熟悉）	JavaBean就是把JavaBean实例当做一个属性放进去

## （1）基本概念

* 使用JavaBean可以减少重复的代码，使开发更加简洁

* JavaBean本质就是一个Java类，要求：
  * 属性：全部私有化，通过get和set方法进行访问
  * 方法：必须实public修饰
  * 构造器：必须有无参构造器

## （2）使用方式

* 使用jsp:useBean的方式**创建**javaBean实例

```jsp
<jsp:useBean id=“对象名” scope=“保存范围 class=“包名.类名” />
保存范围有：page|request|sessin|application，默认为page范围。
<%-- 表示创建Student类型的对象由student引用变量负责记录	有效范围是当前页面 --%>
<jsp:useBean id="student" scope="page" class="com.lagou.demo02.Student"/>
```

*  使用jsp:setProperty的方式**设置**javaBean的属性值		param为了和前端传过来的参数值进行匹配

```jsp
<jsp:setProperty name="对象名" property="属性名" value="属性值" param="参数名"/>
<%-- 表示将student对象中名字为id的属性值设置为1002 --%>
<jsp:setProperty name="student" property="id" value="1002"/> 
```

* 使用jsp:getProperty的方式**获取**javaBean的属性值			实现JavaBean成员变量的获取并打印

```jsp
<jsp:getProperty name="对象名" property="属性名"/>
学号是：<jsp:getProperty name="student" property="id"/><br/>
姓名是：<jsp:getProperty name="student" property="name"/><br/>
```

## （3）保存范围

* JavaBean的数据内容保存的范围有page、request、session以及application(范围从小到大)，默认是page范围

##  (4) 删除方式	当做普通的属性删掉

```jsp
<%
	内置对象.removeAttribute("JavaBean的名字");
%>

<%
	// 表示从session内置对象中删除名字为student的属性
	session.removeAttribute("student");
%>
```



### 当涉及到在JSP页面中实现对象的创建和属性的设置属性的获取这些操作时，就可以用JavaBean组件



# 5.MVC设计模式（重点）

## 5.1 概念

* MVC是模型(Model)和视图(View)以及控制器(Controller)的简写，就是**将数据，界面和业务逻辑进行分离的设计理念**
* M:数据内容封装(Bean)，业务逻辑处理（Service）及DAO
* V：主要负责数据的收集和展现，**用户输入的数据，显示给用户的数据**，JSP文件
* C：主要负责流程控制和页面跳转，Servlet文件



##  5.2 整个MVC的设计流程

**通过浏览器发送请求给Servlet，Servlet通过Service层以及JavaBean封装类对数据库进行读写，将最终结果拿到Servlet，再通过JSP展现给浏览器**



## 5.3 MVC的基本模型

MVC的理念是：

​	**在Servlet和数据库之间加了一层Service（解耦），在Servlet和浏览器之间又加了一层JSP，主要是为了更好的进行数据展示**，浏览器访问服务器，服务器访问数据库，再返回结果

