# Filter+Listener核心技术

# 1. Filter过滤器（重点）	就是服务器和浏览器之间的一道过滤网

* Filter过滤内容和数据。**JavaWeb的三大组件：Servlet，Filter，Listener**
* 过滤器是向Web应用程序的请求和响应处理添加功能的Web服务组件



## 1.2 工作方式

* 通过浏览器发送请求，将请求发给过滤器，经过过滤器的操作后再将请求交给Web
* Web作出响应时也是先将响应信息交给过滤器，再通过过滤器交给浏览器显示出来



## 1.3 使用方式

* 自定义类实现Filter接口并重写doFilter方法

```java
public class LoginFilter implements Filter{
    
    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,
        FilterChain chain) throws IOException,ServletException{
     	// 1.实现对用户访问主页面的过滤操作，也就是只有用户登录后才能访问主页面，否则一律拦截
        // 判断session中是否已有用户名信息，若没有则进行拦截，否则放行
        HttpServletRequest request = (HttpServletRquest)servletRequest;
        HttpSession session = request.getSession();
        Object userName = session.getAttribute("userName");
        if(null == userName){
            // 若没有登录，则回到登录页面
            servletRequest.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
            else{
                // 若已经登录，则放行
                chain.doFilter(servletRequest,servletResponse);
            }
        }
    }
}
```

* 在web.xml文件中配置过滤器

```xml
<filter>
	<filter-name>LoginFilter</filter-name>
	<filter-class>com.lagou.demo01.LoginFilter</filter-class>
</filter>

<filter-mapping>
	<filter-name>LoginFilter</filter-name>
	<url-pattern>/main.jsp</url-pattern>
</filter-mapping>
```



## 1.4 Filter接口

* javax.servlet.Filter接口主要用于描述过滤器对象，可以对资源的请求和资源的响应操作进行筛选操作。
* 常用的方法          

| 方法声明                                                     | 功能介绍               |
| ------------------------------------------------------------ | ---------------------- |
| void init(FilterConfig filterConfig)                         | 实现过滤器的初始化操作 |
| void doFilter(ServletRequest request,SerlvetResponse response, FilterChain chain) | 执行过滤操作的功能     |
| void destroy()                                               | 实现过滤器的销毁操作   |

* Filter生命周期：初始化一次，过滤很多次，销毁一次



## 1.5 FilterConfig接口		对过滤器做初始化操作

* javax.servlet.FilterConfig**接口主要用于描述过滤器的配置信息**
* 常用的方法

| 方法声明                                    | 功能介绍                               |
| ------------------------------------------- | -------------------------------------- |
| String getFilterName()                      | 获取过滤器的名字，配置文件里指定的名字 |
| String getInitParameter(String name)        | 获取指定的初始化参数的数值             |
| Enumeration<String> getInitParameterNames() | 获取所有的初始化参数名称               |
| ServletContext  getServletContext()         | 获取ServletContext上下文对象           |

```java
@Override
public void init(FilterConfig filterConfig) {
    System.out.println("获取到的过滤器名称为：" + filterConfig.getFilterName());
    System.out.println("获取到的指定初始化参数的数值为：" + filterConfig.getInitParameter("userName"));
    System.out.println("获取到的上下文对象是：" + filterConfig.getServletContext());
}
```



## 1.6 多个过滤器的使用	  多个过滤器进行多层过滤

* 如果有多个过滤器都满足过滤的条件，**则容器依据映射的先后顺序来调用各个过滤器。**
* 浏览器向服务器发送请求，请求先给第一个过滤器，过滤完再给第二个过滤器，过滤完再给第三个过滤器，过滤完给Web服务器。响应过程是先过第三个过滤器，再过第二个过滤器，再过第一个，最终浏览器得到过滤后的结果



## 1.7 过滤器的优点

* 实现代码的”热插拔”，增加过滤器和去掉过滤器不影响原来的功能
* 可以将各个模块中相同的代码提取出来放到过滤器里，可提高代码的复用性，易于维护



# 2. Listener监听器(重点)	监听事件的发生

* Servlet规范中定义的一种特殊的组件，**用来监听Servlet容器产生的事件并进行相应的处理**
* Web容器产生的事件分类：
  * 生命周期相关的事件，对象的创建和销毁
  * 属性状态相关的事件。增删改属性
  * 存值状态相关的事件。JavaBean对象和Session绑定和解绑状态
* 底层原理是采用接口回调模式实现



## 2.2 监听器基本分类

| 监听器类型                                       | 功能介绍                            |
| ------------------------------------------------ | ----------------------------------- |
| javax.servlet.ServletRequestListener             | 监听request作用域的对象的创建和销毁 |
| javax.servlet.ServletRequestAttributeListener    | 监听request作用域的属性状态的改变   |
| javax.servlet.http.HttpSessionListener           | 监听session作用域的创建和销毁       |
| javax.servlet.http.HttpSessionAttributeListener  | 监听session作用域的属性状态变化     |
| javax.servlet.ServletContextListener             | 监听application作用域的创建和销毁   |
| javax.servlet.ServletContextAttributeListener    | 监听application作用域的属性状态变化 |
| javax.servlet.http.HttpSessionBindingListener    | 监听对象与session的绑定和解除       |
| javax.servlet.http.HttpSessionActivationListener | 监听session数值的钝化和活化         |



## 2.3 监听器详解

#### (1) ServletRequestListener监听器

* **监听ServletRequest请求对象的创建和关闭**，会通知ServletRequestListener监听器。
* 通知监听器后会采用回调模式调用监听器里面的方法：

| 方法声明                                         | 功能介绍                       |
| ------------------------------------------------ | ------------------------------ |
| void requestInitialized(ServletRequestEvent sre) | 实现ServletRequest对象的初始化 |
| void requestDestroyed(ServletRequestEvent sre)   | 实现ServletRequest对象的销毁   |

#### （2）ServletRequestAttributeListener监听器		监听Request请求里面的属性的变化

* **当向ServletRequest请求中增删改属性时**，就会通知ServletRequestAttributeListener监听器
* 常用方法

| 方法声明                                                     | 功能介绍       |
| ------------------------------------------------------------ | -------------- |
| void  attributeAdded(ServletRequestAttributeEvent  srae)     | 增加属性时调用 |
| void  attributeReplaceed(ServletRequestAttributeEvent  srae) | 修改属性时触发 |
| void attributeRemoved((ServletRequestAttributeEvent  srae))  | 删除属性时触发 |

#### （3）HttpSessionListener监听器		监听Session会话的创建和销毁

* **只要Session出现了创建或销毁动作**，就会通知HttpSessionHttpSessionListener监听器
* 回调的方法：

| 方法声明                                   | 功能介绍                                            |
| ------------------------------------------ | --------------------------------------------------- |
| void sessionCreated(HttpSessionEvent se)   | 当一个HttpSession对象被创建时会调用这个方法         |
| void sessionDestroyed(HttpSessionEvent se) | 当一个HttpSession对象超时或销毁时，将会调用这个方法 |

#### (4) HttpSessionAttributeListener监听器			监听Session中属性状态的改变

* **监听Session中属性的增删改变化**
* 方法：

| 方法声明                                           | 功能介绍                                   |
| -------------------------------------------------- | ------------------------------------------ |
| void attributeAdded(HttpSessionBindingEvent  se)   | 当往会话中加入一个属性的时候会调用这个方法 |
| void attributeRemoved(HttpSessionBindingEvent se)  | 从会话中删除属性时调用                     |
| void attributeReplaced(HttpSessionBindingEvent se) | 改变会话中的属性时调用这个方法             |

#### (5) ServletContextListener监听器			服务器会为每个项目创建一个唯一的ServletContext对象，可以实现多个Servlet之间数据的共享和通信

* **当ServletContext对象在创建和关闭的时候**，产生事件，通知ServletContextListener监听器
* 引发方法的调用：

| 方法声明                                         | 功能介绍                                                     |
| ------------------------------------------------ | ------------------------------------------------------------ |
| void contextInitialized(ServletContextEvent sce) | 当ServletContext创建的时候，将会调用这个方法                 |
| void contextDestroyed(ServletContextEvent sce)   | 当ServletContext销毁的时候，将会调用这个方法，ServletContext对象会随着服务器的创建而创建，随着服务器的关闭而关闭，对应的是整个Web |

#### (6) ServletContextAttributeListener监听器

* **关于ServletContext对象中属性的增删改状态的监听**
* 方法：

| 方法声明                                                  | 功能介绍                                     |
| --------------------------------------------------------- | -------------------------------------------- |
| void attributeAdded(ServletContextAttributeEvent scae)    | 往ServletContext中加入一个属性的时候触发     |
| void attributeRemoved(ServletContextAttributeEvent scae)  | 从ServletContext对象中删除一个属性的时候触发 |
| void attributeReplaced(ServletContextAttributeEvent scae) | 改变ServletContext中的属性时触发             |

#### (7) HttpSessionBindingListener监听器		当有JavaBean对象和Session建立绑定关联时会触发监听器			

#### 对象和Session绑定的监听器不需要配置，因为JavaBean实现了这个接口，而绑定到Session的就是这个JavaBean对象，所以这个监听器就会触发

* **监听关于Session中对象的绑定和解绑的动作**，就会通知HttpSessionBindingListener监听器
* 方法：

| 方法声明                                         | 功能介绍                   |
| ------------------------------------------------ | -------------------------- |
| void valueBound(HttpSessionBindingEvent event)   | 有对象绑定调用该方法       |
| void valueUnbound(HttpSessionBindingEvent event) | 有对象解除绑定时触发该方法 |

#### (8) HttpSessionActivationListener监听器		监听Session的钝化(序列化操作)和活化(反序列化)		保证了session中数据的持久性

#### 钝化操作：将session里面的数值直接写入到硬盘中，希望服务器关闭后session信息还在，就需要把session信息由内存转到硬盘里存储

#### 活化操作：从硬盘里把session信息读出来放到内存中	

* **当有session数值的钝化和活化操作时**，将会通知HttpSessionActivationListener监听器。
* 方法：

| 方法声明                                        | 功能介绍               |
| ----------------------------------------------- | ---------------------- |
| void  sessionWillPassivate(HttpSessionEvent se) | 有钝化操作时调用该方法 |
| void  sessionDidActivate(HttpSessionEvent se)   | 活化操作               |

* 要想实现活化操作，得配置context.xml文件



### 通过活化和钝化操作可以实现将数据保存到session中，再保存到硬盘里，再从硬盘读到内存，还可以把session里面的数据读取出来	

* **主要价值在于当服务器关闭时，session的数据依然可以保存到硬盘里，下次启动服务器时，还可以继续访问session中的数据**
* **涉及到需要把session中的数据保存到硬盘中，由内存和硬盘之间进行session数据的读写时就需要用到钝化和活化操作，也可以通过HttpSessionActivationListener监听器来监听钝化和活化操作**



## 2.4 监听器的案列		统计当前在线的用户数量

* 当前在线的用户数量
* 自定义类实现监听器接口并重写方法。
* 实现HttpSessionListener和ServletContextListener监听器
  * 监听session的创建与销毁，**当浏览器向服务器发送请求时，服务器就会创建一个session对象，把sessionId发给浏览器保存。只要有一个浏览器访问服务器时就会创建一个session。就是有多少个浏览器去访问服务器时，就会创建多少个session，而每一个浏览器代表一个用户，那么多少个session就代表多少个用户**
  * 所以监听session的创建和销毁，每当创建一个session后，相当于多了一个在线用户，每当销毁一个session后，相当于少了一个在线用户
* 无论在线用户加一还是减一最终都要保存到ServletContext对象中，因为ServletContext对象代表一个Web项目，是唯一的，所以记录的人数也是唯一的

```java
public class OnlineUser implements HttpSessionListener,ServletContextListener {
    // 声明一个ServletContext类型的引用负责作为全局对象来记录当前在线用户的数量，通过属性记录
  private ServletContext ctx = null;
 
  // 初始化ServletContext
  public void contextInitialized(ServletContextEvent e) {
      // 得到全局对象，记录数据内容
    ctx = e.getServletContext();
    }
  // 销毁ServletContext
  public void contextDestroyed(ServletContextEvent e) {
    //将ServletContext设置成null;
      ctx = null;
 }
// 当新创建一个HttpSession对象时
  public void sessionCreated(HttpSessionEvent e) {
      System.out.println("有新用户上线啦...");
    //将当前的在线人数加上1，并且保存到ServletContext(application)中
     Object count =  ctx.getAttribute("count");
      // 若当前用户为第一个用户时，则将全局对象中的属性值设置为1即可
      if(null == count){
          ctx.setAttribute("count",1);
      }
      // 若当前用户不是第一个用户，则将全局对象中原有的数据取出来加1后再设置进去
      else{
          Integer integer = (Integer)count;
          integet++;
          ctx.setAttribute("count",integer);
      }
      System.out.println("当前在线用户数量为：" + ctx.getAttribute("count"));
 }
  // 当一个HttpSession被销毁时（过期或者调用了invalidate()方法）
  public void sessionDestroyed(HttpSessionEvent e) {
   		System.out.println("有用户已下线...");
      //将当前人数减去1，并且保存到ServletContext(application)中 
      
 }
}
```

* 在Web.xml中配置监听器

```xml
<listener>
	<listener-class>com.lagou.demo04.OnlineUser</listener-class>
</listener>
```



