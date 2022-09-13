# 任务一：Springmvc基本应用 学习笔记

# 一、Springmvc简介

## 1.1 MVC模式

​	MVC是软件工程中的一种软件架构模式，它是一种分离业务逻辑与显示界面的开发思想

```markdown
* M(model)模型：处理业务逻辑，封装实体

* V(view)视图：展示内容

* C(controller)控制器：负责调度分发(1.接收请求、2.调用模型、3.转发视图)
```

## 1.2 Springmvc概述

​	**Springmvc 是一种基于java的实现MVC设计模式的轻量级Web框架，表现层框架**

​	Springmvc 是最主流的mvc框架之一，全面超越了Struts2，成为最优秀的MVC框架。它通过一套注解，让一个简单的Java类成为处理请求的控制器，而无须实现任何接口。同时它还**支持RESTful 编程风格**的请求。



浏览器发送请求给Tomcat服务器，Tomcat内核负责接收请求，创建并封装Request对象，创建Response对象，调用请求资源，把请求具体发送给Servlet。再通过Springmvc的前端控制器把Servlet的一些共有行为干完再去干特有行为



**总结：**

​	**Springmvc的框架就是封装了原来Servlet中的公有行为；例如：参数封装， 视图转发等**

所以使用Springmvc就可以进行简化开发，**提高效率**。因为已经把一些公有行为进行了封装，不需要自己写了。



## 1.3 Springmvc快速入门

**需求：**

​	客户端发起请求，服务器接收请求，执行逻辑并进行视图跳转

就是借助Springmvc编写一个具体的controller，当客户端发起请求后，服务器接收到请求后，会把该请求转发到controller控制器上，controller方法包含具体要执行的业务逻辑，执行完业务逻辑后进行视图跳转，进行页面展示

**步骤分析：**

```markdown
1. 创建web项目，导入Springmvc相关坐标

2. 配置Springmvc前端控制器 DispathcerServlet

3. 编写Controller类（业务逻辑）和视图页面

4. 使用注解配置Controller类中业务方法的映射地址		通过注解配置该方法的访问路径
		这样根据地址就可以访问到方法了
5. 配置Springmvc核心配置文件，spring-mvc.xml
```



### 1）创建web项目，导入springmvc相关依赖

```xml
<!--  设置为web工程    -->
    <packaging>war</packaging>

    <dependencies>
<!--springMVC坐标-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
<!--Servlet坐标-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
<!--jsp坐标-->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.1.2</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```



### 2）配置Springmvc前端控制器DispathcerServlet

```xml
<!--    配置springmvc的前端控制器：DispatcherServlet-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--     DispatcherServlet实例化后，在执行初始化方法时，就会去解析初始化参数，根据名称拿到配置文件路径，加载解析 spring-mvc.xml   -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
<!--        在应用启动时，就完成servlet的实例化及初始化操作
                配置load-on-startup 标签，那么在应用启动时，该servlet就会被实例化并且执行初始化方法，
                    获取到初始化参数，根据初始化参数得到配置文件路径，进行解析加载 spring-mvc.xml
-->
        <load-on-startup>2</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <!-- 配置上/ 后，当页面向后台发送请求时，首先被 DispatcherServlet 拦截，完成一些共有行为 -->
<!--        /   会匹配到所有的访问路径，但是不会匹配到像*.jsp这样的方法url
                完全匹配，可以拦截到  /login  /add    /update 这样的路径
                /a.jsp      有后缀名的url不能拦截
-->

<!--        /和/*的区别
                / 可以匹配到所有的访问路径，但是不会匹配到 *.jsp 这样后缀名的url
                /* 所有url路径 都可以匹配到，不管有没有后缀名
    -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

笔试题：**/ 和 /* 的区别**

/ 能够匹配到所有的访问路径，但是不会匹配到 像 *.jsp 这样有后缀名的 路径

/* 就是所有路径都可以匹配到，不管带不带后缀名

---



### 3）编写Controller类和视图页面

UserController.java

```java
public class UserController {
 
  public String quick() {
    // 业务逻辑
    System.out.println("springmvc入门成功...");
      // 视图跳转     写要跳转的路径，执行完quick方法后，需要跳转到success.jsp 页面     		是请求转发，地址栏路径没变
    return "/WEB-INF/pages/success.jsp";
 }
}
```

/WEB-INF/pages/ success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>success...</h3>
</body>
</html>
```



### 4）使用注解配置Controller类中业务方法的映射地址

```java
// 生成该类的实例对象访问方法
@Controller     // 应用在web层的注解，生成该类的实例对象存到容器中
public class UserController {

    // @RequestMapping 注解就是： 用于建立 URL 和 处理请求方法之间的对应关系
    // 里面写访问该方法的访问地址
    @RequestMapping("/quick")
    public String quick(){
        // 业务逻辑
        System.out.println("springmvc入门成功...");
        // 视图跳转     写要跳转的路径，执行完quick方法后，需要跳转到success.jsp 页面      返回逻辑视图名         请求转发
        // return "success"     返回给了前端控制器，前端控制器再调用视图解析器
        // 调用的就是显式配置的视图解析器
        return "/WEB-INF/pages/success.jsp";
    }
}
```



### 5）编写配置Springmvc核心配置文件spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/mvc
		http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
">



    <!--    IOC注解扫描
                    只扫描controller包下的类，只负责web层，把web层的实例对象存到由springmvc创建出来的IOC容器中
        -->
<!--  由springmvc创建出的容器，管理web层对象  -->
    <context:component-scan base-package="com.lagou.controller"/>	
```





## 1.4 web工程执行流程

Tomcat服务器接收到客户端发来的请求后，会解析请求资源地址。创建请求对象request，创建响应对象response。调用目标资源

Tomcat服务器把该请求交给DispatherServlet 前端控制器进行处理，前端控制器解析映射地址，找到对应的处理器。调用对应的组件进行解析，匹配到配置了@RequestMapping 注解配置Controller类中的业务方法，从而执行方法

也就是说，当发送请求时，**是先走前端控制器**，**前端控制器再对映射地址进行解析**，找到具体的处理器，进行业务逻辑处理

**其实在执行具体controller方法之前，是先走的前端控制器的，由前端控制器对请求映射地址进行解析，从而确定具体要执行哪个controller方法**



## 1.5 知识小结

```markdown
* Springmvc是对MVC设计模式的一种实现，属于轻量级的web框架

* Springmvc的开发步骤：
	1. 创建web项目，导入Springmvc相关依赖
	2. 配置Springmvc前端控制器	DispathcerServlet
			封装了一些共有行为，同时可以调用组件对映射地址进行解析，从而找到具体要执行的业务逻辑的处理器
	3. 编写Controller类（方法）和视图页面
	4. 使用注解配置Controller类中业务方法的映射地址		@RequestMapping
	5. 配置Springmvc核心配置文件 spring-mvc.xml
```





# 二、SpringMVC组件概述

## 2.1 SpringMVC的执行流程

面试，笔试。	组件的层面SpringMVC的执行流程

```markdown
1. 用户发送请求给前端控制器DispatcherServlet。

2. DispatcherServlet收到请求调用HandlerMapping处理器映射器。

3. 处理器映射器找到具体的处理器（可以根据xml配置、注解进行查询），生成处理器对象及处理器拦截器（如果有则生成）一并返回给DispatcherServlet。

4. DispatcherServlet调用HandlerAdapter处理器适配器。

5. HandlerAdapter经过适配调用具体的处理器（Controller，也叫后端控制器）。

6. Controller执行完成返回ModelAndView。

7. HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet。

8. DispatcherServlet将ModelAndView传给ViewReslover 视图解析器。

9. ViewReslover解析后返回具体的View视图

10. DispatcherServlet根据View进行渲染视图（将模型数据填充到视图中）

11. DispatcherServlet将渲染后的视图响应给用户
```





## 2.2 SpringMVC组件解析

```markdown
1. 前端控制器：DispatcherServlet		调用组件的
	用户请求到达前端控制器，它就相当于MVC模式中的C，DispatcherServlet 是整个流程控制的中心，由它去调用其他组件处理用户的请求，DispatcherServlet 的存在降低了组件之间的耦合性。所有的组件接收请求都是由前端控制器发送，响应也是响应给前端控制器，各个组件之间没有关联。
		
2. 处理器映射器：HandlerMapping		找到处理器的
	HandlerMapping 负责根据用户请求找到 Handler 处理器（Controller），SpringMVC	提供了不同的映射器实现不同的映射方式，例如：配置文件方式，实现接口方式，注解方式等。 把找到的处理器 返回给前端控制器，前端控制器去调用处理器适配器 去执行处理器
	
3. 处理器适配器：HandlerAdapter		执行处理器的
	通过 HandlerAdapter 对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多的处理器进行执行。
	
4. 处理器：Handler【开发者自己写的】		执行业务逻辑的
	它就是我们编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由Handler 对具体的用户请求进行处理
	
5. 视图解析器：ViewResolver		解析逻辑视图名的
	ViewResolver 负责将处理结果生成 View视图，View Resolver 首先根据逻辑视图名解析成物理视图名，即具体的页面地址，再生成 View 视图对象，最后对View 进行渲染将处理结果通过页面展示给用户
	
6. 视图：View【开发者自己写的】		进行数据的页面展示	 
	SpringMVC框架提供了很多的 View 视图类型的支持，最常用的 视图就是 jsp。就可以在jsp中 通过页面标签将模型数据通过页面展示给用户，需要由程序员根据业务需求开发具体的页面。 
```

**笔试题：SpringMVC中的三大组件是什么？**

处理器映射器：HandlerMapping，处理器适配器：HandlerAdapter和视图解析器：ViewResolver

四大组件：再加一个前端控制器 DispatcherServlet

---

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/mvc
		http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
">



    <!--    IOC注解扫描
                    只扫描controller包下的类，只负责web层，把web层的实例对象存到由springmvc创建出来的IOC容器中
        -->
<!--  由springmvc创建出的容器，管理web层对象  -->
    <context:component-scan base-package="com.lagou.controller"/>


<!--    显式配置处理器映射器-处理器适配器-->
<!--    添加这个标签，等同于配置了处理器映射器和处理器适配器，还进行了功能增强：支持json的读写   直接就可以完成json串和对象的转换
            conversion-service="conversionService"：使自定义类型转换器生效
-->
    <mvc:annotation-driven conversion-service="conversionService"/>

<!--    配置视图解析器：ViewResolver        可以帮助我们完成前缀和后缀的配置，在编写返回值时，省去了前缀和后缀，提高开发效率
            InternalResourceViewResolver：具体视图解析器的组件类
-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
<!--        success：解析逻辑视图名，解析成具体的物理文件地址
                就是进行前缀和后缀的拼接
                    /WEB-INF/pages/success.jsp
                        通过这个地址可以定位到页面
-->


<!--        属性注入-->
<!--     配置视图解析器   前缀-->
        <property name="prefix" value="/WEB-INF/pages/"/>
<!--        后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```





## 2.3 SpringMVC注解解析

### @Controller

​	SpringMVC基于Spring容器，所以在进行SpringMVC操作时，需要将Controller存储到Spring容器中。就要在Controller类上添加@Controller注解，生成该类对象存到IOC容器中

```xml
<!--    IOC注解扫描
                    只扫描controller包下的类，只负责web层，把web层的实例对象存到由springmvc创建出来的IOC容器中
        -->
<!--  由springmvc创建出的容器，管理web层对象  -->
    <context:component-scan base-package="com.lagou.controller"/>
```

```xml
<!--    IOC注解扫描
                对于Dao层和Service层实例对象的管理交给Spring管理
     -->
<!--    由Spring容器管理service对象和dao对象-->
    <context:component-scan base-package="com.lagou.service"/>
    <context:component-scan base-package="com.lagou.dao"/>
```



### @RequestMapping

```markdown
* 作用：
	用于建立请求 URL 和处理请求方法之间的对应关系。根据映射地址，找到对应的方法，从而执行方法	
	
* 位置：
	1.类上：请求URL的第一级访问目录。此处不写的话，就相当于应用的根目录。写的话需要以/开头。
		它出现的目的是为了使我们的URL可以按照模块化管理：
			用户模块
				/user/add
				/user/update
				/user/delete
				...
			账户模块
				/account/add
				/account/update
				/account/delete
				...
				
	2. 方法上：请求URL的第二级访问目录，和一级目录组成一个完整的 URL路径。
	
	
	
* 常用属性：
	1.value：用于指定请求的URL。它和path属性的作用是一样的。
	2.method：用来限定请求的方式
	3.params：用来限定请求参数的条件
		例如：params={"accountName"}	表示请求参数中必须有accountName
			 params={"money!100"}	  表示请求参数中money不能是100
```

```java
@Controller     // 应用在web层的注解，生成该类的实例对象存到容器中
@RequestMapping("/user")        // 添加在类上，表示一级访问目录       就是为了使我们的url可以按照模块化管理
public class UserController {

    // @RequestMapping 注解就是： 用于建立 URL 和 处理请求方法之间的对应关系
    // 当类上和方法上都进行了注解配置，那么访问地址就是     /一级访问目录/二级访问目录
    // http://localhost:8080/springmvc_quickstart/user/quick        /一级访问目录/二级访问目录
    

    /*      常用属性：
        path：作用等同于value，同样是设置方法的映射地址的
        method：用来限定请求的方式    RequestMethod.POST：只能以post的请求方式访问该方法，其他请求方式会报错
        params：用来限定请求参数的条件  params={"accountName"} 表示请求参数中必须有accountName    不携带会报错
            对当前这个quick方法进行请求的话，那么必须要携带请求参数，请求参数必须有accountName。
            就是请求中必须携带accountName 这个参数，才能成功访问quick方法
     */
    @RequestMapping(path = "/quick",method = RequestMethod.GET,params = {"accountName"})   // 添加在方法上，表示二级访问目录
    public String quick(){
        // 业务逻辑
        System.out.println("springmvc入门成功...");
        // 视图跳转     写要跳转的路径，执行完quick方法后，需要跳转到success.jsp 页面      返回逻辑视图名         请求转发
        // return "success"     返回给了前端控制器，前端控制器再调用视图解析器
        // 调用的就是显式配置的视图解析器
        return "success";
    }
}
```





## 2.4 知识小结

```markdown
* SpringMVC的三大组件
	处理器映射器：HandlerMapping
	处理器适配器：HandlerAdapter
	视图解析器：View Resolver
	
* 开发者编写的：
	处理器：Handler
	视图：View
	
	一般都会在SpringMVC核心配置文件中显示配置组件
	也可以把三大组件进行显式配置	
		<mvc:annotation-driven/>	会对处理器映射器，处理器适配器进行功能增强
			支持JSON的读写
		视图解析器	可以帮助我们完成解析逻辑视图名，进行前缀和后缀的配置。这样的话在编写处理器的返回值时，就省去编写前缀和后缀了，提高开发效率
```







# 三、SpringMVC的请求

## 3.1 请求参数类型介绍

**如何借助SpringMVC的配置来获取到前台发送到服务器端的请求参数**

客户端请求参数的格式是：`name=value&name=value...`，向前台发送的参数的类型都是String

服务器要获取请求的参数时，要进行类型转换，还需要进行数据的封装成实体

SpringMVC可以接收如下类型的参数：

* 基本类型参数
* 对象类型参数
* 数组类型参数
* 集合类型参数



## 3.2 获取基本类型参数

​	Controllerl中的**业务方法的参数名称要与请求参数的name**一致，参数值**会自动映射匹配**。**并且能自动类型转换**；自动的类型转换**是指从String向其他类型的转换**。

```jsp
<%--${pageContext.request.contextPath}动态的来获取当前项目路径  springmvc_quickstart  a标签的请求方式：get请求
    	get请求所发送的中文乱码问题：当前Tomcat是8.5版本，Tomcat内部已经解决了get请求方式所带来的中文乱码问题。
    --%>
    <a href="${pageContext.request.contextPath}/user/simpleParam?id=1&username=杰克">
        基本类型参数
    </a>
```

```java
/*
        获取基本类型请求参数
     */
    // 添加注解，映射地址为二级目录，要和前台请求的二级访问目录一致
    @RequestMapping("/simpleParam")
    // 参数名要和前台请求参数名保持一致
    public String simpleParam(Integer id,String username){
        System.out.println(id);
        System.out.println(username);

        // 跳转页面
        return "success";
    }
```





## 3.3 获取对象类型参数

​	Controller中的业务方法参数的**POJO属性名与请求参数的name**保持一致，这样的话，请求参数的值就会自动映射匹配封装。

> ​	当想接收到前台传递过来的请求参数并且封装成某一个实体对象时，只需要在方法参数声明该实体类型的参数，该实体类型里面的属性值要请求参数的name值保持一致。这样就能完成自动映射封装。

```jsp
<%--form表单  该表单提交的请求方式为post方式
    	Tomcat无法解决post提交方式所产生的中文乱码问题
    --%>
<form action="${pageContext.request.contextPath}/user/pojoParam" method="post">
    编号：<input type="text" name="id"/><br/>
    用户名：<input type="text" name="username"/><br/>
    <input type="submit" value="对象类型参数"/>
</form>
```

```java
/*
    User实体类
 */
public class User {

    // 属性值要和请求参数的name值保持一致，就可以完成自动映射封装
    private Integer id;
    private String username;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    // springmvc就是通过调用set方法完成值的注入
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

```java
/*
        获取对象类型请求参数
     */
    // 设置该方法的映射地址
    @RequestMapping("/pojoParam")
    public String pojoParam(User user){

        System.out.println(user);
        // 跳转到success页面
        return "success";
    }
```





## 3.4 中文乱码过滤器解决POST提交中文乱码问题

​	当post请求时，数据会出现乱码，我们可以在web.xml 中设置一个**过滤器**来进行编码的过滤

```xml
<!--    借助中文乱码过滤器 解决POST方式提交的中文乱码问题-->
<!--    配置中文乱码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
<!--        初始化参数，设置编码来解决中文乱码问题-->
        <init-param>
<!--            以UTF-8的形式进行编码-->
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
<!--        拦截所有请求，进行中文乱码过滤-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```



## 3.5 获取数组类型参数

​	Controller中的**业务方法数组名称与请求参数的name**一致，参数值会自动映射匹配。

```jsp
<!-- 请求参数的name值相同，但value值不同。后台在接受参数时，就可以使用数组类型接受 -->
<%--form表单  演示获取数组类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/arrayParam" method="post">
<%--    向后台传递的参数值为 value的值--%>
    编号：<input type="checkbox" name="ids" value="1"/>1<br/>
    编号：<input type="checkbox" name="ids" value="2"/>2<br/>
    编号：<input type="checkbox" name="ids" value="3"/>3<br/>
    编号：<input type="checkbox" name="ids" value="4"/>4<br/>

    <input type="submit" value="数组类型参数"/>
</form>
```

```java
/*
        获取数组类型请求参数
     */
    @RequestMapping("/arrayParam")
    // 参数名要和前台的请求参数的name值 保持一致
    public String arrayParam(Integer[] ids){

        System.out.println(Arrays.toString(ids));
        // 跳转到成功页面
        return "success";
    }
```



## 3.6 获取集合（复杂）类型参数

​	获取集合参数时，要**将集合参数包装到一个POJO中**才可以。

```jsp
<%--form表单  演示获取集合类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/queryParam" method="post">
<%--    输入的值封装到QueryVo对象里面 的keyword属性上
            name的值和QueryVo里的属性名保持一致，这样才能调用set方法进行值的注入
--%>
    搜索关键字:
        <input type="text" name="keyword"/><br/>
<%--把请求参数封装到QueryVo对象里面的User对象，就是给User对象里面的id属性和username属性进行赋值--%>
    user对象:
        <input type="text" name="user.id" placeholder="编号"/>
        <input type="text" name="user.username" placeholder="姓名"/><br/>

<%--    封装List集合
            当前文本框要去封装到QueryVo对象里面的userList属性上
                而userList属性是集合类型
                    name="userList[0]"：表示要给userList集合里面的第一个元素进行赋值,就是User对象的id和username
             相当于给userList中第一个User元素对象进行请求参数封装
--%>
    list集合:
        第一个元素：
        <input type="text" name="userList[0].id" placeholder="编号"/>
        <input type="text" name="userList[0].username" placeholder="姓名"/><br/>

        第二个元素：
        <input type="text" name="userList[1].id" placeholder="编号"/>
        <input type="text" name="userList[1].username" placeholder="姓名"/><br/>


<%--  name="userMap"：表示当前input框的值要封装到QueryVo对象里面的userMap集合中
            表示设置map集合中的第一个元素，key是u1，根据从userMap中取出的第一个元素的value值就是user对象
                所以要把当前文本框的值赋值给user对象里面的id属性和username属性
  --%>
    Map集合:
        第一个元素:
        <input type="text" name="userMap['u1'].id" placeholder="编号"/>
        <input type="text" name="userMap['u1'].username" placeholder="姓名"/><br/>
        第二个元素:
        <input type="text" name="userMap['u2'].id" placeholder="编号"/>
        <input type="text" name="userMap['u2'].username" placeholder="姓名"/><br/>

    <input type="submit" value="复杂类型"/>
</form>
```

```java
public class QueryVo {

    private String keyword;
    private User user;
    private List<User> userList;
    private Map<String,User> userMap;

    @Override
    public String toString() {
        return "QueryVo{" +
                "keyword='" + keyword + '\'' +
                ", user=" + user +
                ", userList=" + userList +
                ", userMap=" + userMap +
                '}';
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }
}
```

```java
/*
        获取集合（复杂）类型请求参数
     */
    // 映射地址是前台表单action 中的二级 url目录  queryParam
    @RequestMapping("/queryParam")
    // 前台的请求参数最终要封装到QueryVo这个对象里面的属性上，所以参数写QueryVo
    public String queryParam(QueryVo queryVo){

        System.out.println(queryVo);
        // 返回视图
        return "success";
    }
```



## 3.7 自定义类型转换器

​	SpringMVC 中默认提供了一些常用的类型转换器；例如：服务端接受客户端提交的字符串转换成int类型进行参数设置。日期格式类型要求为：yyyy/MM/dd 不然的话会报错。

对于特有的行为，SpringMVC提供了自定义类型转换器方便开发者自定义处理。

```jsp
<%--form表单  演示自定义类型转换器：错误的产生    当前前台传递的日期类型不是 2012/12/12 这样的类型的话，在用Date类型接收时会报错
            前台日期格式为 / 开头的话，Springmvc内置的类型转换器可以完成String类型到日期类型的转换，但是年月日之间得要以 / 开头
                若想要别的日期格式，就要自定义类型转换器，不再使用Springmvc提供的日期类型转换器了，在日期类型转换时走自己定义的类型转换器
--%>
<form action="${pageContext.request.contextPath}/user/converterParam" method="post">

<%--    前台向后台传递的参数是日期类型--%>
    生日:<input type="text" name="birthday" placeholder="生日"/>

    <input type="submit" value="自定义类型转换器"/>
</form>
```

```java
/**
 * 当前台传递过来的参数是日期类型的字符串
 * 就会经过自定义类型转换器，把日期类型的字符串转换成日期对象并返回
 */
// 表示接收到的String类型转换为Date类型
public class DateConverter implements Converter<String, Date> {

    // s参数就是表单传递过来的请求参数 前台页面写的 2012-12-12 就会赋值给s
    @Override
    public Date convert(String s) {

        // 将日期字符串转换成日期对象并返回
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
```

```xml
<!--    显式配置处理器映射器-处理器适配器-->
<!--    添加这个标签，等同于配置了处理器映射器和处理器适配器，还进行了功能增强：支持json的读写   直接就可以完成json串和对象的转换
            conversion-service="conversionService"：使自定义类型转换器生效
-->
    <mvc:annotation-driven conversion-service="conversionService"/>


<!--  自定义类型转换器的配置

            当前台请求参数为日期类型的字符串，就会走到这个自定义类型转换器，把日期类型字符串转换成日期对象进行返回
-->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">

        <property name="converters">
            <set>
                <bean class="com.lagou.converter.DateConverter"></bean>
            </set>
        </property>
    </bean>
```

```java
/*
        获取日期类型参数：（自定义类型转换器）
     */
    @RequestMapping("/converterParam")
    // 前台传递的是日期，参数名和表单中的name值 保持一致
    public String converterParam(Date birthday){

        System.out.println(birthday);
        return "success";
    }
```



## 3.8 相关注解

### @RequestParam

​	**当请求的参数name名称与Controller的业务方法参数名称不一致时**，**就需要通过@RequestParam注解显示的绑定**，解决了绑定不上的问题

```jsp
<%--演示@RequestParam注解--%>
<a href="${pageContext.request.contextPath}/user/findByPage?pageNo=2">
    分页查询
</a>
```

```java
/*
        演示@RequestParam注解
            借助@RequestParam注解 去解决当前台请求参数name名称和业务方法参数名称不一致时的问题
            通过@RequestParam注解 进行显示的绑定，从而解决了绑定不上的问题
     */
    /*
        @RequestParam	属性：
            name：匹配页面传递参数的名称
            defaultValue：设置参数的默认值
                也就是前台在传递请求参数时，没传pageNo这个参数，那就让pageNum 这个值默认值为1
                前台传了的话，就把pageNo对应的值赋值给pageNum
            required：设置是否必须传递该参数，默认值为true，如果设置了默认值，值自动改为false
                就是只要配置了@RequestParam 这个注解，并且设置了name属性，如果前台请求参数，不携带pageNo的话 就会报错
                就是设置了defaultValue的话，required的值会改为false。前台传不传pageNo 都无所谓了
                因为设置了默认值，不传递走默认值，传递走传过来的值
     */
    @RequestMapping("findByPage")
    public String findByPage(@RequestParam(name = "pageNo",defaultValue = "1",required = false)Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){

        System.out.println(pageNum);    // 2，虽然配置了defaultValue = "1"，但是前台请求参数传递了pageNo，那么以前台传递过来的请求参数值为准
        System.out.println(pageSize);   // 5，因为前台没有传递pageSize，所以走默认值 defaultValue = "5"
        return "success";
    }
```



### @RequestHeader

​	获取请求头的数据

```java
/*
        @RequestHeader注解的使用
            获取请求头数据
     */
    @RequestMapping("/requestHeader")
    // 当请求过来时，会携带cookie，所以可以来获取cookie这个请求头对应的数据
    // 借助该注解，获取cookie请求头对应的信息 赋值给 cookie参数
    public String requestHeader(@RequestHeader("cookie") String cookie){
        System.out.println(cookie);
        return "success";
    }
```



### @CookieValue

​	获取cookie中的数据

```java
/*
        @cookieValue 注解的使用
            获取cookie中的数据
     */
    @RequestMapping("/cookieValue")
    public String cookieValue(@CookieValue("JSESSIONID") String jsessionId){

        System.out.println(jsessionId);
        // 返回逻辑视图
        return "success";
    }
```





## 3.9 获取Servlet相关API

​	**SpringMVC支持使用原始Servlet API对象作为控制器方法的参数进行注入，常用的对象直接在方法参数中进行声明注入就ok**

```java
/*
       小知识点：如何在controller方法中获取到关于Servlet的API
        原始servletAPI的获取
            如果在controller方法中 想用到request，response，session这样的Servlet API对象的话，可以直接在方法参数注入，声明一下。springmvc就会直接注入这些对象
     */
    @RequestMapping("/servletAPI")
    public String servletAPI(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);

        // 请求转发：一次请求，且地址栏不会发送改变
        return "success";
    }
```









# 四、SpringMVC的响应

## 4.1 SpringMVC响应方式介绍   页面跳转和返回数据

**页面跳转**

1. 返回字符串逻辑视图
2. void原始ServletAPI
3. ModelAndView

**返回数据**

1. 直接返回字符串数据
2. 将对象返回集合转为json返回给前台（任务二）



## 4.2 返回字符串逻辑视图

​	直接返回字符串：此种方式会**将返回的字符串与视图解析器的前后缀拼接后跳转到指定页面**			**这种方式其实是请求转发，一次请求，且地址栏不会发生改变的**

```java
@RequestMapping("/returnString")
public String returnString() {
    // 逻辑视图，会被前端控制器拿到。前端控制器去调用视图解析器，对逻辑视图进行解析，拼接上前缀和后缀		/WEB-INF/pages/success.jsp	定位到页面，进行跳转
  return "success";
}
```



## 4.3 返回值void 借助原始Servlet API 进行页面跳转

​	我们可以通过request、response对象实现响应		**不常用**

```java
/*
        通过原始servletAPI进行页面跳转
     */
    @RequestMapping("/returnVoid")
    // request对象和response对象由springmvc注入
    public void returnVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   

        // 借助request对象完成请求转发，一次请求，通过服务器内部进行请求转发
        //request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);

        // 借助response对象完成重定向    两次请求，外部请求不能直接访问WEB-INF 这个目录下的页面的
        // WEB-INF：安全目录：不允许外部请求直接访问该目录资源，只可以进行服务器内部转发
        // 第二次请求不允许直接访问到安全目录下对应的页面
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
```





## 4.4 转发和重定向使用的关键字

​	我们一般使用返回**字符串逻辑视图**实现页面的跳转，这种方式其实就是**请求转发**。

**我们也可以写成：forward转发**

​	如果用了forward：则路径必须写实际视图url，不能写逻辑视图了，相当于：

```java
request.getRequestDispatcher("url").forward(request,response)
```

​	使用请求转发，既可以转发到jsp，也可以转发到其他的控制器方法

```java
/*
        演示forward 关键字进行请求转发
        当访问到forward方法时，就是向model中设置了username和value值，同时请求转发到 success.jsp

            forward:要写全路径
            如果想在模型中设置值，就可以注入Model对象，通过Model 调用addAttribute 来向模型中设置值
            若想取出模型中的值，那么在进行页面跳转时 要使用转发，才能取出该模型中的值
     */
    @RequestMapping("/forward")
    // 在模型中设置值时，就在参数中设置 Model 对象，然后调用 addAttribute方法 进行值的设置
    public String forward(Model model){

        // 还想在模型中设置一些值怎么做？
        // 在访问该方法时，向模型中设置了值
        // 当访问forward方法，跳转到success.jsp 时
        // 就可以根据 username 这个key，来取出对应的value值 拉勾招聘 显示在success.jsp 页面中
        model.addAttribute("username","拉勾招聘");



        // 其实就是要往 product controller里面的findAll方法    一级目录/二级目录
        // 相当于执行完了当前方法的业务逻辑后，还要再去调用product 这个controller里面的findAll方法
        //return "forward:/product/findAll";
        // 使用forward 请求转发：既可以转发到jsp，也可以转发到其他的控制器方法
        return "forward:/WEB-INF/pages/success.jsp";
    }
```



**Redirect重定向**

​	我们可以不写虚拟目录，SpringMVC框架会自动拼接，并且将Model中的数据拼接到url地址上

```java
/*
        演示Redirect关键字进行重定向
            当写了redirect或者forward关键字，是不会再走视图解析器的，也就不会进行前缀和后缀拼接了
     */
    @RequestMapping("/redirect")
    public String redirect(Model model){
        // 底层使用的还是request.setAttribute("username","拉勾教育")
        // request域范围是一次请求，而重定向是两次请求已经超过域范围了，所以在index.jsp 中取不出模型中的值
        // model的范围是一次请求，重定向时两次请求，大过域范围了，所以取不出
        model.addAttribute("username","拉勾教育");

        // 重定向 ，springmvc会自动拼接前面的目录
        return "redirect:/index.jsp";
    }
```





## 4.5 借助ModelAndView完成页面跳转

### 4.4.1 方式一

​	**在Controller中方法中手动创建并返回ModelAndView对象，并且设置视图名称**

```java
/*
        modelAndView进行页面跳转：方式一
     */

    @RequestMapping("/returnModelAndView")
    public ModelAndView returnModelAndView(){

        /*
            model：模型：作用：封装存放数据的
            view:视图：作用：用来展示数据的      也就是要用到哪个页面来进行数据的展示，那就在view上设置上对应的页面路径
         */
        ModelAndView modelAndView = new ModelAndView();
        // 设置模型数据
        modelAndView.addObject("username","modelAndView方式一");

        // 设置视图名称       跳转的视图名称，直接写逻辑视图名
        // 视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        // 所以直接给逻辑视图名，再由视图解析器拼接上前缀和后缀，最终进行页面跳转
        modelAndView.setViewName("success");

        // 把modelAndView 这个对象返回给处理器适配器，而处理器适配器再modelAndView交给前端控制器
        // 前端控制器拿到modelAndView 对象后，找视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        return modelAndView;
    }
```



### 4.4.2 方式二		常用

​	在Controller中方法形参上直接声明ModelAndView，无需在方法中自己创建，在方法中直接使用该对象设置视图，同样可以跳转页面

```java
/*
        modelAndView进行页面跳转：方式二      常用
            ModelAndView  既可以设置模型数据，同时也可以设置视图名称
     */

    @RequestMapping("/returnModelAndView2")
    // 在当前方法参数中声明 ModelAndView 对象，在方法体中直接用到 注入进来的 ModelAndView 这个对象
    public ModelAndView returnModelAndView2(ModelAndView modelAndView){

        /*
            model：模型：作用：封装存放数据的
            view:视图：作用：用来展示数据的      也就是要用到哪个页面来进行数据的展示，那就在view上设置上对应的页面路径
         */

        // 设置模型数据
        modelAndView.addObject("username","modelAndView方式二");

        // 设置视图名称       跳转的视图名称，直接写逻辑视图名
        // 视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        // 所以直接给逻辑视图名，再由视图解析器拼接上前缀和后缀，最终进行页面跳转
        modelAndView.setViewName("success");

        // 把modelAndView 这个对象返回给处理器适配器，而处理器适配器再modelAndView交给前端控制器
        // 前端控制器拿到modelAndView 对象后，找视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        return modelAndView;
    }
```

**ModelAndView  既可以设置模型数据，同时也可以设置视图名称**







## 4.6 返回数据

​	直接返回字符串数据

```java
/*
        通过原始servletAPI进行页面跳转
     */
    @RequestMapping("/returnVoid")
    // request对象和response对象由springmvc注入
    public void returnVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*直接返回数据*/
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("拉勾网");



        // 借助request对象完成请求转发，一次请求，通过服务器内部进行请求转发
        //request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);

        // 借助response对象完成重定向    两次请求，外部请求不能直接访问WEB-INF 这个目录下的页面的
        // WEB-INF：安全目录：不允许外部请求直接访问该目录资源，只可以进行服务器内部转发
        // 第二次请求不允许直接访问到安全目录下对应的页面
        //response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
```





## 4.7 @SessionAttributes注解

​	**如果在多个请求之间共享数据**，则可以在控制器类上标注一个 @SessionAttributes 注解，配置需要在session中存放的数据范围。SpringMVC将存放在model中对应的数据暂存到HttpSession中。	**Session域是一次会话，代表在多个请求之间可以共用数据了**

**注意：@SessionAttributes只能定义在类上**

```java
@Controller     // 应用在web层的注解，生成该类的实例对象存到容器中
@RequestMapping("/user")        // 添加在类上，表示一级访问目录       就是为了使我们的url可以按照模块化管理
@SessionAttributes("username")  // 向request域中（model）中存入key为username时，会同步到session域中
public class UserController {

    
    @RequestMapping("/forward")   
    public String forward(Model model){
   
        // model底层就是request域，作用范围就是一次请求
        // 当前向model中存入的值 对应的key就是 username，所以当在当前类上添加了@SessionAttributes("username")这个注解后，就会把存入的值同步到session域中
        // session域是一次会话，所以多个请求之间可以共享这个值
        model.addAttribute("username","拉勾招聘");
       
        return "forward:/WEB-INF/pages/success.jsp";
    }


    /*
        @SessionAttributes的效果：多个请求之间共享数据
            想实现在访问returnString  这个方法时
            跳转到success 页面同样可以取到 拉勾招聘 这个值，那就要在当前类上添加 @SessionAttributes 这个注解

            在forward方法中，向model中存入的值 对应的 key就是 username
            所以在当前类上添加了 @SessionAttributes("username") 这个注解后
            就会把 存入的值 同步到session域中，而session域是一次会话，所以再去访问 returnString 这个方法时
            就会取到username所对应的value值 也就是 拉勾招聘  显示在success页面中
     */
    @RequestMapping("/returnString")
    public String returnString(){

        return "success";
    }
}
```





## 4.8 SpringMVC的响应部分  知识小结

```markdown
* 页面跳转时采用返回字符串逻辑视图，进行页面跳转，常用
	1.forward转发
		可以通过Model向request域中设置数据
	2.redirect重定向
		直接写资源路径即可，虚拟目录SpringMVC框架自动完成拼接
		

* 数据存储到model中，等同于存到request域中。所以作用范围是一次请求
	Model model
		model.addAttribute("username","蔡徐坤");
```







# 五、静态资源访问的开启

​	当请求页面，如果有静态资源需要加载时，比如jquery文件，通过谷歌开发者工具抓包发现，没有加载到jquery文件。原因是SpringMVC的前端控制器DispatcherServlet的url-pattern配置的是/(缺省)，代表对所有的静态资源都进行处理操作，这样就不会执行Tomcat内置的DefaultServlet处理。是交给了前端控制器处理，而前端控制器把这个请求路径当成了对于Controller方法的请求了。所以加载不到报404。

我们可以通过以下两种方式指定放行静态资源：

**方式一**

```xml
<!--采用方式一完成了指定静态资源的放行-->
<!--    方式一：放行指定的静态资源       局部化
            mapping="/js/**"：放行的映射路径        表示以js开头的请求路径，后面不管层级，都进行放行
            location="/js/"：静态资源所在的目录       当前的jquery-3.5.1.js  就在js目录下，所以就写 /js/
-->
<mvc:resources mapping="/js/**" location="/js/"/>
<mvc:resources mapping="/css/**" location="/css/"/>
<mvc:resources mapping="/img/**" location="/img/"/>
```

**方式二**

```xml
<!--    方式二：放行全部的静态资源       全部的
            在springmvc配置文件中开启了DefaultServlet处理静态资源
             也就是说现在对于静态资源的处理最终都要由tomcat内置的DefaultServlet来把静态资源进行处理
-->
<mvc:default-servlet-handler/>
```

