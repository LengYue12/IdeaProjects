# 任务二：SpringMVC进阶

# 一、ajax异步交互

​	当前台通过ajax发送了一个JSON给后台方法时，后台方法接收到后怎么把JSON转换成对象或集合？

​	后台向前台发送数据，如何转为JSON响应给前台，执行ajax的回调，把对象转为JSON输出到页面

​	SpringMVC默认用MappingJackson2HttpMessageConverter来对JSON数据进行转换，也就是SpringMVC默认支持对JSON数据的转换。

​	需要加入Jackson的包，同时在SpringMVC配置文件中 使用 `<mvc:annotation-driven/>`	标签

```xml
<dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.8</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.8</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.0</version>
        </dependency>
```

```xml
<!--    显式配置处理器映射器-处理器适配器-->
<!--    添加这个标签，等同于配置了处理器映射器和处理器适配器，还进行了功能增强：支持json的读写   直接就可以完成json串和对象的转换
-->
    <mvc:annotation-driven/>
```

导入包和配置好标签后，当前SpringMVC就支持对JSON数据的转换了



## 1.1 @RequestBody

​	该注解用于Controller的方法的形参声明，当使用ajax提交并指定contentType为json形式时，通过HttpMessageConverter接口转换为对应的POJO对象

**把前台传过来的JSON串转为集合或对象，就要用到@RequestBody 这个注解，写在方法的形参上**，@RequestBody这个注解是把JSON串封装成集合或pojo对象

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<%--ajax异步交互--%>
<button id="btn1">ajax异步提交</button>

<script>
    $("#btn1").click(function () {

        let url = '${pageContext.request.contextPath}/user/ajaxRequest';
        let data = '[{"id":1,"username":"蔡徐坤"},{"id":2,"username":"吴亦凡"}]';

        $.ajax({
            type: 'POST',
            url: url,
            data : data,
            contentType : 'application/json;charset=utf-8',
            success: function (resp) {
                // 把对象转为JSON
                alert(JSON.stringify(resp));
            }
        })
    })


</script>

</body>
</html>
```

```java
/*
        ajax异步交互   前台发送的JSON串： [{"id":1,"username":"张三"},{"id":2,"username":"李四"}]
            @RequestBody    把前台传过来的JSON串转为集合，就要用到@RequestBody 这个注解，写在方法的形参上
     */
    @RequestMapping("/ajaxRequest")
    // @RequestBody这个注解是把JSON串封装成集合或pojo对象

    // 在方法的形参上添加了@RequestBody这个注解后，后台接收到前台传过来的JSON串时，就可以封装成List集合了。
    // 前提是引入了jackson 依赖，并且配置了mvc:annotation-driven 这个标签
    public void ajaxRequest(@RequestBody List<User> list){
        System.out.println(list);

    }
```





## 1.2 @ResponseBody

​	该注解用于将Controller方法返回的对象，通过HttpMessageConverter接口转换为指定格式的数据，例如：json，xml等。通过Response响应给客户端

​	**也就是后台把集合或对象转为JSON响应给前台，就可以在当前方法上添加@ResponseBody**，是在响应集合或响应某一个实体时，由SpringMVC把该对象或集合转为JSON串响应给前台的

```java
/*
        ajax异步交互   前台发送的JSON串： [{"id":1,"username":"张三"},{"id":2,"username":"李四"}]
            @RequestBody    把前台传过来的JSON串转为集合，就要用到@RequestBody 这个注解，写在方法的形参上
     */
    @RequestMapping("/ajaxRequest")
    // @RequestBody这个注解是把JSON串封装成集合或pojo对象
    // @ResponseBody这个注解是在响应集合或响应某一个实体时，由SpringMVC把该对象或集合转为JSON串响应给前台的


    // 在方法的形参上添加了@RequestBody这个注解后，后台接收到前台传过来的JSON串时，就可以封装成List集合了。
    // 前提是引入了jackson 依赖，并且配置了mvc:annotation-driven 这个标签

    // 把对象转换为JSON串，响应给前台可以添加@ResponseBody注解
    // 把list返回时，因为添加了@ResponseBody注解，SpringMVC就会把list集合转为JSON串响应给前台
    @ResponseBody
    public List<User> ajaxRequest(@RequestBody List<User> list){
        System.out.println(list);

        return list;
    }
```

​	





# 二、RESTful

## 2.1 什么是RESTful

​	Restful是一种软件架构风格，设计风格，而不是标准，只是提供了一组设计原则和约束条件。主要用于客户端与服务器交互的软件，基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存。

Restful风格的请求是使用"**url+请求方式**"表示一次请求目的的，**HTTP 协议里面四个表示操作方式的动词**：

* GET：读取（Read）
* POST：新建（Create）
* PUT：更新（Update）
* DELETE：删除（Delete）

| 客户端请求 | 原来风格URL地址     | RESTful风格URL地址        **url+请求方式**                   |
| ---------- | ------------------- | ------------------------------------------------------------ |
| 查询所有   | /user/findAll       | GET       /user                     注解@GetMapping("/user") |
| 根据ID查询 | /user/findById?id=1 | GET       /user{1}               注解@GetMapping("/user/{id}") |
| 新增       | /user/save          | POST     /user                   注解@PostMapping("/user")   |
| 修改       | /user/update        | PUT       /user                   注解@PutMapping("/user")   |
| 删除       | /user/delete?id=1   | DELETE      /user{1}          注解@DeleteMapping("/user/{id}") |



## 2.2 代码实现

**@PathVariable**

​	用来接收RESTful风格请求地址中占位符的值

**@RequestController**，组合注解：组合了@Controller + @ResponseBody

​	RESTful风格多用于前后端分离项目开发，前端通过ajax与服务器进行异步交互，我们处理器通常返回的是json数据所以使用@RequestController来替代@Controller和@ResponseBody两个注解

* **@GetMapping("/user/{id}")**查
* **@PostMapping("/user")增**
* **@PutMapping("/user")改**
* **@DeleteMapping("/user/{id}")删**

****

```java
/*
    Restful编程风格
 */
@RestController     // 组合注解：组合了@Controller + @ResponseBody
//@Controller // 生成当前类的实例存到容器中
@RequestMapping("/restful")
public class RestfulController {


    /*
        根据ID进行查询
        Localhost:8080/项目名/user/2 + get请求方式   404 findById:2     就可以定位到这个方法
     */
    // 占位符的值要和参数名保持一致
    //@RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    //@ResponseBody       // 如果当前方法返回的是对象或集合，那么该注解会转为JSON串响应给前台
    // 但如果返回的是字符串，借助这个注解可以直接进行数据响应，响应给浏览器，不会走视图解析器了

    // 设置了value的值，设置了请求方式get。 可以替换掉@RequestMapping这个注解
    @GetMapping("/user/{id}")       // 相当于 @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    // 借助这个注解 @PathVariable 获取到了占位符的值，并且赋值给了参数
    public String findById(@PathVariable Integer id){
        // 调用service方法完成对id为2的这条记录的查询
        // findById方法中怎么才能获取到restful编程风格中url里面占位符的值？
        // 因为前台传递过来的请求参数的值就赋值给了占位符

        return "findById:" + id;
    }



    /*
        新增方法
     */
    // 指定该方法请求方式是post，post请求+/user  路径 就定位到了这个方法
    @PostMapping("/user")   // 相当于 @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String post(){

        // 新增

        // 因为在当前类上已经用到了@RestController 这个注解，相当于组合了@Controller + @ResponseBody
        // 所以就等同于在这个方法上添加了 @ResponseBody 这个注解。那么直接return post 是不会进行页面跳转的  直接把post输出在浏览器了
        return "post";
    }


    /*
        更新方法
     */
    @PutMapping("/user")
    public String put(){
        // 更新操作
        return "put";
    }


    /*
        根据ID删除方法
     */
    @DeleteMapping("/user/{id}")
    // @PathVariable :获取占位符里id的值，赋值给参数。参数名和占位符名保持一致
    public String delete(@PathVariable Integer id){

        return "delete" + id;
    }

}
```





# 三、文件上传

## 3.1 文件上传三要素		笔试	前台表单三要素

* 表单项 type="file"            要有用于提交文件的input框
* 表单的提交方式 method="POST"                 上传的文件要在请求体中
* 表单的enctype属性是**多部分表单形式** enctype="multipart/form-data"

```html
<form action="${pageContext.request.contextPath}/fileupload" method="post" enctype="multipart/form-data">
    名称:<input type="text" name="username"/><br/>
    文件:<input type="file" name="filePic"/><br/>
    <input type="submit" value="单文件上传"/>
</form>
```



## 3.2 文件上传原理

* 当form表单修改为多部件提交时，request.getParameter()将失效
* 当form表单的enctype取值为 `application/x-www-form-urlencoded` 默认值时，
  * form表单的正文内容格式是：`name=value&name=value`
  * 所以在调用request.getParameter()时，是可以根据name值取到对应的value值的
* 当form表单的enctype取值为 `multipart/form-data`，请求正文内容就变成了多部件形式
  * 主要就是把以前url编码的形式修改成多部件形式，而且多部件形式包括了上传文件的名称和上传文件的内容



## 3.3 单文件上传

**步骤分析：**

```markdown
1. 导入fileupload和io依赖    
		基于SpringMVC实现文件上传时，底层用的就是fileupload
2. 配置文件上传解析器
		当在前台传文件时，上传的文件会经过文件解析器，解析并封装到Controller方法参数中
3. 编写文件上传代码
		前台表单，后台Controller，编写文件上传的方法
```

### 1）导入fileupload和io依赖

```xml
<!--导入文件上传对应的依赖-->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.3</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>
```



### 2）配置文件上传解析器

```xml
<!--  配置文件上传解析器
        可以完成一些属性值的设置，进行一些参数设定
-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
<!--  maxUploadSize：设置文件上传的最大值
        设定文件上传的最大值为5MB，5*1024*1024
 -->
    <property name="maxUploadSize" value="5242880"></property>
<!--   maxInMemorySize：设置文件上传时写入内存的最大值，如果小于这个参数值不会生成临时文件，默认为10240 -->
    <property name="maxInMemorySize" value="40960"></property>
</bean>
```



### 3）编写文件上传代码

前台表单

```html
<%--单文件上传前台代码--%>

<%--编写满足文件上传三要素的表单
        1. 表单的提交方式为post提交，这样才能有请求体
        2. 表单的enctype属性要修改为 multipart/form-data
        3. 表单中要有文件上传项   input 框的type 属性 为file

--%>
<form action="${pageContext.request.contextPath}/fileupload" method="post" enctype="multipart/form-data">
    名称:<input type="text" name="username"/><br/>
<%--    input框 的type 为file，表示为文件上传项
            后台controller里的方法获取参数要用到name值
--%>
    文件:<input type="file" name="filePic"/><br/>
    <input type="submit" value="单文件上传"/>
</form>
```

后台Controller方法

```java
/*
    文件上传后台代码
 */
@Controller // 生成实例存到IOC
public class FileUploadController {


    /*
        基于SpringMVC实现 单文件上传
     */
    @RequestMapping("/fileupload")  // 该方法的映射地址
    // 前台的文件上传项input框 里的name值和参数名filePic保持一致         MultipartFile 文件上传对应的类型，使用该类型的参数来接收前台所上传的文件内容
    // 当在前台选好了文件进行上传时，所上传的文件就会经过配置的文件上传解析器，解析该文件
    // 并把解析过后的内容封装到 MultipartFile 这个对象里，MultipartFile 这就是获取到的所上传的文件对象
    public String fileUpload(String username, MultipartFile filePic) throws IOException {

        // 获取表单的提交参数，完成文件上传
        System.out.println(username);

        // a.txt文件名   abc
        // 获取原始的文件上传名
        String originalFilename = filePic.getOriginalFilename();
        // 把当前接收到的上传文件存到哪个目录下       加上原始文件名
        filePic.transferTo(new File("F:/upload/" + originalFilename));


        // 跳转成功页面
        return "success";
    }
}
```





## 3.4 多文件上传

```jsp
<%--实现多文件上传
        在表单中添加文件上传项
--%>
<form action="${pageContext.request.contextPath}/filesupload" method="post" enctype="multipart/form-data">
    名称:<input type="text" name="username"/><br/>
    文件1:<input type="file" name="filePic"/><br/>
    文件2:<input type="file" name="filePic"/><br/>
    <input type="submit" value="多文件上传"/>
</form>
```

```java
/*
        多文件上传
            在方法中，接收到多个文件时，所用到的类型就是 MultipartFile[]  数组  表示接收多个文件
     */
    @RequestMapping("/filesupload")
    public String filesUpload(String username,MultipartFile[] filePic) throws IOException {

        // 获取表单的提交参数，完成文件上传
        System.out.println(username);

        // 对文件数组进行遍历
        // 取出每一个multipartFile 对象，进行操作
        // 获取到对应文件的原始文件名，再通过multipartFile 对象调用transferTo 方法保存到服务器 F:/upload 目录下
        for (MultipartFile multipartFile : filePic) {
            String originalFilename = multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File("F:/upload/" + originalFilename));
        }

        return "success";
    }
```







# 四、异常处理

## 4.1 异常处理的思路

在Java中，对于异常的处理一般有两种方式：

* 一种是当前方法捕获（try-catch），这种处理方式会造成业务代码和异常处理点的耦合
* 另一种是自己不处理，而是抛给调用这处理（throws），调用者再抛给它的调用者，也就是一直向上抛。在这种方法的基础上，衍生了SpringMVC的异常处理机制

异常处理机制：系统的dao、service、controller出现异常都通过throws Exception向上抛，最后由SpringMVC前端控制器交由**HandlerExceptionResolver 异常处理器**进行异常处理

---

**可以自定义异常处理器来代替SpringMVC默认的异常处理器，也就是让我们自定义的异常处理器生效**





## 4.2 自定义异常处理器

HandlerExceptionResolver 是一个接口，实际干活的是实现类。我们除了可以使用默认的实现类以外，也可以自定义一个异常处理器类，并实现HandlerExceptionResolver 接口，也就是实现自定义异常处理器。如果再有异常抛出给前端控制器，前端控制器调用的就是我们自定义出来的异常处理器类，因为该类实现了HandlerExceptionResolver 接口。所以前端控制器调用异常处理器进行异常处理时，实际执行的就是我们自定义的异常处理器类



**步骤分析：**

```markdown
1. 创建异常处理器类实现 HandlerExceptionResolver 接口，重写方法
2. 在SpringMVC.xml 中 配置异常处理器
3. 编写异常页面          产生异常跳转的页面
4. 测试异常跳转
```

### 1）创建异常处理器类实现HandlerExceptionResolver

```java
/*
    自定义异常处理类
 */
public class GlobalExceptionResolve implements HandlerExceptionResolver {

    /*
        Exception e：实际抛出的异常对象
            通过e 获取到实际抛出的异常对象，通过异常对象获取到对应的异常信息
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {

        // 具体的异常处理  产生异常后，跳转到一个最终的异常页面
        // 一旦执行到当前方法，那就意味着程序出现了异常
        // 借助ModelAndView 进行视图跳转
        ModelAndView modelAndView = new ModelAndView();
        // 向模型中设置值      取出当前的异常信息设置在模型中     获取到对应的异常信息
        modelAndView.addObject("error",e.getMessage());
        // 进行视图跳转       逻辑视图名
        modelAndView.setViewName("error");

        // 当返回modelAndView时，会经过视图解析器，而在视图解析器配置了前缀和后缀，所以可以定位 到 WEB-INF/pages/error.jsp 这个页面
        return modelAndView;
    }
}
```



### 2）配置异常处理器	 就是生成实例对象存到容器中

```java
@Component
public class GlobalExecptionResovler implements HandlerExceptionResolver {}
```

```xml
<!--    配置自定义的异常处理器-->
<bean id="globalExceptionResolve" class="com.lagou.exception.GlobalExceptionResolve"></bean>
```



### 3）编写异常页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    这是一个最终异常的显示页面
<%--    借助el表达式取出error信息--%>
    ${error}
</body>
</html>
```



### 4）测试异常跳转

```java
@Controller
public class ExceptionController {

    /*
        通过访问Controller里的方法产生异常，最终跳转到异常处理器里面的方法进行处理，并跳转页面
     */
    // 进行访问，出现异常    就会走到自定义异常类 GlobalExceptionResolve 类 里的resolveException 方法进行异常处理
    // 向模型中添加异常信息，最终跳转到error页面，进行异常展示
    @RequestMapping("/testException")
    public String testException(){


        int i = 1/0;

        return "success";
    }
}
```





## 4.3 Web的异常处理机制	

**可以在web.xml中，通过配置error-page 标签，来处理500状态码或404状态码的异常**

```xml
<!--    通过配置error-page 标签
            处理500状态码或404状态码的异常
-->

<!--    web的处理异常机制-->
<!--    处理404异常-->
    <error-page>
<!--    若异常状态码是404，跳转到404.jsp
    error-code：异常错误码
    location：配置位置    -->
        <error-code>404</error-code>
        <location>/404.jsp</location>
    </error-page>

<!--    若异常状态码是500，跳转到500.jsp-->
<!--    处理500异常-->
    <error-page>
        <error-code>500</error-code>
        <location>/500.jsp</location>
    </error-page>
```





# 五、拦截器

## 5.1 拦截器（interceptor）的作用	

​	SpringMVC的**拦截器**类似于 Servlet 开发中的过滤器Filter，用于对处理器进行**预处理**和**后处理**，借助拦截器可以在目标方法执行之前和执行之后进行一些逻辑处理

​	将拦截器按一定的顺序连结成一条链，这条链被称为**拦截器链（InterceptorChain）**。在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前定义的顺序被调用。拦截器也是AOP思想的具体实现。因为在不修改目标方法的源码的情况下，来进行前置方法，后置方法的增强。





## 5.2 拦截器和过滤器的区别		笔试

关于interceptor和filter的区别：

| 区别         | 过滤器                                                     | 拦截器                                                       |
| ------------ | ---------------------------------------------------------- | ------------------------------------------------------------ |
| **使用范围** | 是 Servlet规范中的一部分，任何的 Java Web工程都可以使用    | 是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能用   |
| **拦截范围** | 在url-pattern 中配置了 /* 之后，可以对所有要访问的资源拦截 | 只会拦截访问的控制器方法，如果访问的是jsp，html，css，image或者js这些静态资源 时是不会进行拦截的 |



## 5.3 快速入门

**需求**：借助拦截器对Controller里面的目标方法进行拦截，在目标方法执行之前和执行之后都要通过拦截器进行拦截，实现前置增强和后置增强



**步骤分析：**

```markdown
1. 创建拦截器类实现HandlerInterceptor接口，重写方法
2. 在SpringMVC.xml 中配置拦截器，同时表明对哪些方法拦截
3. 测试拦截器的拦截效果，关注拦截器方法的执行顺序
```



### 1）创建拦截器实现HandlerInterceptor接口

```java
/*
    借助拦截器实现对目标方法的拦截增强
        拦截器拦截Controller里面的方法
 */
public class MyInterceptor1 implements HandlerInterceptor {


    /*
       preHandle：在目标方法执行之前  进行拦截
                return false:不放行，终止方法    也就是执行到preHandle 方法，如果return false，那后面的代码就不执行了
                                        也就是不会再去执行对应的目标方法，也不会再去执行后续的拦截方法了
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle1...");

        return true;    // 表示执行完对应的拦截方法后，放行。接着再去执行目标方法，以及后续的拦截方法
    }


    /*
        postHandle：在目标方法执行之后，视图对象返回之前，执行的方法
             执行时机：   就是在Controller里对应的方法代码执行完毕后，所执行的方法，然后再去视图跳转
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle1...");
    }



    /*
        afterCompletion：在流程都执行完成之后，执行的方法
            就是当Controller里对应的方法执行完成后，postHandle 这些方法执行完毕后，最终也跳转了页面后，流程都走完了
            最终执行的方法
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("afterCompletion1...");
    }
}
```



### 2）配置拦截器

```xml
<!--    配置拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
<!--   借助mvc:mapping，配置一下对应的该interceptor 所拦截的路径
            path=/**        表示对访问路径一级以及多级目录都进行拦截
   -->
        <mvc:mapping path="/**"/>   <!--对所有controller类里面的所有方法都进行拦截-->
<!--        当前就会采用MyInterceptor1 这个拦截器 对所有的请求资源路径 都进行拦截-->
        <bean class="com.lagou.interceptor.MyInterceptor1"></bean>
    </mvc:interceptor>
</mvc:interceptors>
```



### 3）测试

编写Controller，发请求到controller，跳转页面

```java
@Controller     // 生成该类实例存到容器中
public class TargetController {


    @RequestMapping("/target")
    public String targetMethod(){

        System.out.println("目标方法执行了...");

        // 跳转到success页面
        return "success";
    }
}
```

编写jsp页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--从模型中借助el表达式把对应的value值取出来--%>
    <h3>success...${username}</h3>

<% System.out.println("视图执行了..."); %>
</body>
</html>
```





## 5.4 拦截器链

​	开发中拦截器可以单独使用，也可以同时使用多个拦截器形成一条拦截器链。开发步骤和单个拦截器是一样的，只不过注册的时候注册多个拦截器， 注意**这里注册的顺序就代表拦截器执行的顺序**。

​	同上，再编写一个MyHandlerInterceptor2操作，测试执行顺序：

```xml
<!--    配置拦截器-->
<!--    拦截器链，配置多个拦截器，通过配置多个拦截器所形成拦截器链
           进行拦截器的配置注册
            配置注册的顺序就是执行顺序
                在目标方法执行之前，其实是要经过MyInterceptor1 和 MyInterceptor2 这两个拦截器的
                    配置拦截器注册顺序是先注册的 MyInterceptor1，再去注册 MyInterceptor2
                        所以在拦截时，就先由MyInterceptor1对目标方法进行拦截
        preHandle1...
        preHandle2...
        目标方法执行了...
        postHandle2...
        postHandle1...
        视图执行了...
        afterCompletion2...
        afterCompletion1...


        所以对于后置增强方法和最终增强方法的执行顺序就和配置注册拦截器顺序相反
        只有前置增强方法是对应配置注册顺序的
-->
<mvc:interceptors>
    <mvc:interceptor>
<!--   借助mvc:mapping，配置一下对应的该interceptor 所拦截的路径
            path=/**        表示对访问路径一级以及多级目录都进行拦截
   -->
        <mvc:mapping path="/**"/>   <!--对所有controller类里面的所有方法都进行拦截-->
<!--        当前就会采用MyInterceptor1 这个拦截器 对所有的请求资源路径 都进行拦截-->
        <bean class="com.lagou.interceptor.MyInterceptor1"></bean>
    </mvc:interceptor>
    
    <mvc:interceptor>
        <!-- 拦截器路径配置 -->
        <mvc:mapping path="/**"/>
        <!-- 自定义拦截器类 -->
        <bean class="com.lagou.interceptor.MyInterceptor2"></bean>
    </mvc:interceptor>
</mvc:interceptors>
```





## 5.5 拦截器知识小结

拦截器中的方法说明：

| 方法名            | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| preHandle()       | 方法在请求处理之前调用，方法的返回值是布尔，当它返回false时，表示请求结束，后续的Interceptor 和 Controller都不会再执行，当返回值为true时就会继续调用下一个Interceptor 的preHandle方法，没有下一个Interceptor的话，执行目标方法 |
| postHandle()      | 方法是在当前请求进行处理之后被调用，前提是preHandle方法的返回值为true时才能被调用，会在DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller 处理之后的**ModelAndView 对象进行操作** |
| afterCompletion() | 方法在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行，前提是preHandle 方法的返回值为 true时 才能被调用。也就是说所有的流程都走完后，该方法最终执行 |

