# Servlet核心技术下

# 1. Servlet+JDBC应用（重点）

# 2. 重定向和转发（重点）



## 2.1 重定向，就是给浏览器发送一个新的位置，客户端跳转，感觉像在客户端里面完成了两个页面的跳转

* 当使用web浏览器向服务器发送Http请求后，服务器响应一个新的location给浏览器，浏览器再根据新的地址重新向服务器发送Http请求	 
* **实现重定向需要借助javax.servlet.http.HttpServletResponse接口中的以下方法**
  * **void sendRedirect(String  location)          使用指定的重定向位置URL向客户端发送临时重定向响应**
* 重定向的原理
  * **浏览器首先向服务器上的Servlet发送请求，当服务器收到请求后立刻将重新定向的位置发给浏览器，浏览器收到302后根据服务器发给的地址重新发出请求**
* 重定向的特点
  * 重定向后，浏览器地址栏的URL会发生改变
  * 重定向过程中会将前面的Request对象销毁，然后创建一个新的Request对象       (**因为两次请求**)
  * 重定向的URL可以是其它项目工程      (**重定向时可以重定向到其它项目**)



## 2.2 转发，就是让Web组件将任务转交给另外一个Web组件

* **把浏览器给的请求转交给另外一个组件，让另外一个组件去处理**

* 在一个Web组件(Servlet，jsp)**将它没有完成的处理通过容器转交给另外一个Web组件进行继续处理的方式就叫转发**。转发的各个组件会共享Request和Response对象
* **浏览器给服务器发送请求后，服务器将请求直接转交给了另外一个Web组件**

### 转发的实现

* 绑定数据到**Request**对象
  * Object getAttribute(String name)  将指定属性值作为对象返回，若给定名称属性不存在，则返回空值
  * void setAttribute(String  name,Object o)   在此请求中存储属性值
* 获取转发器对象                 **Request对象调getRequestDispatcher   获取一个请求调度器**
  * RequestDispatcher getRequestDispatcher(String path)     返回一个RequestDispatcher对象，该对象充当位于给定路径上的资源的包装器
* 转发操作                              **再用请求调度器调用forward方法          相当于把请求转发给了另外一个Web组件**
  * void forward(ServletRequest request, ServletResponse response)  将请求从一个servlet转发到服务器上的另一个资源（Servlet、JSP文件或HTML文件）
* 转发的特点
  * 转发之后浏览器地址栏的URL不会发生改变
  * 转发过程中共享Request对象
  * 转发的URL不可以是其它项目工程            （**默认在当前的模块找Web组件，得是同一个Web服务器**）



## 重定向和转发的比较

* 重定向：**是让浏览器先发送请求给服务器上的A组件，紧接着A组件会将一个重新指定的位置给浏览器，浏览器再根据服务器发过来的位置重新发送请求，可以访问B组件**
  * 重定向就相当于浏览器的跳转
* 转发：**浏览器首先发送请求给服务器的A组件，A组件收到请求后直接将请求转发给B组件，再由B组件将处理结果响应到浏览器**
  * 转发理解为服务器里面的跳转



# 3. Servlet线程安全（重点）

* 服务器在收到请求之后，会启动一个线程来进行相应的请求处理。
* 默认情况下，服务器为每个Servlet只创建一个对象实例。**当有多个请求同时访问同一个Servlet时**，会有多个线程访问同一个资源（Servlet对象），这个时候就可能发生线程安全的问题，**会造成数据的不一致问题**
* 多线程并发逻辑，需要使用**synchronized**对代码加锁处理，但尽量避免使用。



# 4. 状态管理（重点） 

# 就是把浏览器和服务器之间多次交互的数据作为一个整体保存起来的管理技术	也就是Cookie和Session技术

* 在BS架构中的请求和相应都基于HTTP协议，而HTTP协议是”无状态”的协议，一旦服务器相应完浏览器的请求后，就断开连接，下一次请求又会重新建立连接。	**也就是说并没有保持连接的状态，所以称为无状态协议**
* **希望当浏览器打开后，对服务器的请求和响应的交互作为一个整体，将多次交互涉及到的数据全部保存起来的技术称为状态管理。**
* 在JavaWeb中**实现状态管理的两种技术**：
  * 客户端管理：将状态保存到浏览器，基于**Cookie**技术实现
  * 服务器管理：将状态保存到服务器端，基于**Session**技术实现

**状态管理就是把浏览器和服务器之间的多次交互作为一个整体管理起来，把之间交互的数据也要管理起来**



# 5. Cookie技术（重点）	通行证

## 5.1 基本概念

### 	浏览器给服务器发请求，服务器创建好一个Cookie，把Cookie发给浏览器，浏览器把Cookie保存下来，以后再请求带着Cookie过来找服务器，服务器根据Cookie信息判断出来这些交互是一个整体，一次会话，实现了状态管理



* 在JavaWeb中Cookie代表的是进行状态管理的一种保存在客户端的技术。就是把需要进行管理状态的相关数据保存在客户端的一种技术。 保存方式使用”名-值”的方式	键值对
* **浏览器向服务器发送请求时，服务器将数据以Set-Cookie消息头的方式把Cookie信息以及相关的数据内容响应给浏览器，然后浏览器将这些数据保存起来**
* **当浏览器再次访问服务器时，会将这些数据以Cookie消息头的方式发送给服务器**                   **保证了多次交互之间的整体性**
* **当浏览器没有Cookie信息时，服务器把Cookie对象发给浏览器之后，浏览器接受到Cookie对象后去存储，如果没有就添加，如果有就修改**



## 5.2 相关的方法

* 使用javax.servlet.http.Cookie类的构造方法实现Cookie的创建		**在服务器里创建Cookie**
  * Cookie(String name, String value)	根据参数指定数值构造对象
* 准备好Cookie对象就要使用javax.servlet.http.HttpServletResponse接口的成员方法实现Cookie的添加
  * void addCookie(Cookie cookie)		把Cookie信息添加到响应信息里，一同发给浏览器
* 获取Cookie信息使用javax.servlet.http.HttpServletRequest接口的成员方法实现Cookie对象的获取
  * Cookie[] getCookies()			获取此请求中包含的所有Cookie对象
* 获取Cookie对象中属性和修改适应javax.servlet.http.Cookie类的方法
  * String getName()		返回此Cookie对象中的名字
  * String getValue()        返回此Cookie对象中的数值
  * void setValue(String newValue)              修改Cookie的数值



## 5.3 Cookie的生命周期

* 默认情况下，Cookie的生命周期保存在内存中，只要浏览器关闭，Cookie信息就会消失
* 如果希望关闭浏览器后Cookie信息仍然有效，可以通过Cookie类的成员方法实现
  * int getMaxAge()                 返回cookie的最长使用期限（以秒为单位）
  * void setMaxAge(int expiry)                      设置cookie的最长保留时间（秒）         **正数表示在指定的秒数后失效         负数表示浏览器关闭后失效         0表示马上失效**



## 5.4 Cookie的路径问题      把Cookie进行过滤

* 对Cookie的路径和服务器的请求路径进行匹配，也就是**对发给服务器的Cookie信息进行过滤的**。满足路径时再发给服务器
* 默认和模块路径一样，**只有Cookie的路径和请求的路径匹配时，才会把Cookie信息发给服务器**
* **访问的请求地址必须符合Cookie的路径或者其子路径时，浏览器才会发送Cookie信息**
  * void setPath(String url)         设置Cookie的路径信息



## 5.5 Cookie的特点

* Cookie技术不适合存储所有数据
  * 将状态数据保存在浏览器端，不安全
  * 保存的数据量有限制，大约4KB左右
  * 只能保存字符串信息
  * 可以通过浏览器设置为禁止使用



# 6. Session技术（重点）       用户信息表放在服务器端（理发卡，登记表）

## 6.1 基本概念  	当浏览器向服务器发出请求时，服务器会创建或获取已有的Session以Cookie的方式发给浏览器，在响应头

* Session 会话，	是用来维护客户端和服务器关联的一种技术。将状态保存到服务器端
* 当浏览器向服务器进行请求时，服务器会为每一个浏览器在服务器端的内存中分配一个空间。用于创建一个Session对象，该对象有一个id属性且该值唯一，我们称为**SessionId**，服务器会将这个SessionId以Cookie方式发送给浏览存储
* 浏览器再次访问服务器时会将SessionId发送给服务器，服务器可以根据SessionId查找到相对应的Session对象



* **当浏览器第一次访问服务器时，服务器会创建一个Session对象，把Session对象的Id以Cookie的方式发给浏览器，浏览器保存，再次访问时，根据Id找到已有的Session对象。通过SessionId实现状态管理**



## 6.2 相关的方法

* 使用javax.servlet.http.HttpServletRequest接口的	成员方法实现Session的获取
  * HttpSession getSession()		**获取Session对象，如果有返回，如果没有就创建一个**
* 使用javax.servlet.http.HttpSession接口的成员方法实现判断和获取
  * boolean isNew()		判断是否为新创建的Session
  * String getId()             获取Session的编号
* 使用javax.servlet.http.HttpSession接口的成员方法实现属性的管理                 **属性的设置和获取**
  * Object  getAttribute(String name)		     **根据name来获取对应的value**            返回此会话中用指定名称绑定的对象，如果没有对象在该名称下绑定，则返回空值
  * void setAttribute(String name, Object value)     **设置name和value**    使用指定的名称将对象绑定到此会话
  * void  removeAttribute(String name)                    **根据name删除对应的value**            从此会话中删除与指定名称绑定的对象



## 6.3 Session的生命周期

* 为了节省服务器内存空间资源，服务器会将空闲时间过长的Session对象自动清除掉，默认超时限制一般是30分钟
  * 使用javax.servlet.http.HttpSession接口的成员方法实现失效时间的获取和修改
    * int getMaxInactiveInterval()	 获取失效时间
    * void setMaxInactiveInterval(int interval)           设置失效时间            **秒**
* 可以配置web.xml文件修改失效时间          **分钟**

```xml
<session-config>
	<session-timeout>30</session-timeout>
</session-config>
```



## 6.4 Session的特点

* 数据比较安全
* 能够保存的数据类型丰富，而Cookie只能保存字符串
* 能够保存更多的数据，而Cookie大约保存4KB
* 数据保存在服务器端占用服务器的内存，如果信息过多，会影响服务器的性能





# **Cookie和Session都是为了实现状态管理，浏览器的客户跟踪**
