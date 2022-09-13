# EL和JSTL核心技术

# 1. EL表达式（熟悉）	是为了简化数据的获取和遍历的机制	${...}		EL表达式用来取代JSP中表达式的机制

* EL表达式就是**为了简化JSP中表达式的写法**。对JSP中表达式<%=%>的写法的简化，可以访问数据和输出

## 1.2 主要功能      EL表达式默认去内置对象里取数据

* 依次访问pageContext、request、session和application作用域对象存储的数据             
* 获取请求参数的值         **前台页面传过来的参数**
* 访问Bean对象的属性值
* 访问集合中的数据
* 输出简单的运算结果

## 1.3 访问内置对象的数据

### （1）访问方式

* <%=request.getAttribute("varName")%>
* 用EL实现：**${varName}**

###  (2) 取数据的执行流程     底层原理

取数据时先去pageContext，有就直接输出，pageContext没有再去request，session，application，如果都没有输出null

## 1.4 访问请求参数的数据		就是前台页面里传过来的数据内容

* 在EL之前使用下列方式访问请求参数的数据

  request.getParameter(name);

  request.getParameterValues(name);

* 在EL中使用下列方式访问请求参数的数据

  param:接收的参数只有一个值

  paramValues：接收的参数有多个值

```jsp
<!-- 获取指定参数的值 -->
${param.name}
<!-- 获取指定参数中指定下标的数值 -->
${paramValues.hobby[0]}
```

## 1.5 访问Bean对象的属性	  实现属性的获取和打印		访问成员变量值

### （1）访问方式

* 方式一：${对象名.属性名},例如：**${user.name}**
* 方式二：${对象名["属性名"]},例如：**${user.["name"]}**

###  (2) 主要区别

* 如果属性名包含一些特殊字符，如：.或,等并非字母或数字的符号，就要使用[]的方式而不是.的方式访问
* 使用[]的方式可以动态的取值，方式如下：

```jsp
<%
	request.setAttribute("prop","age");
%>
<!-- 相当于表达式中写一个变量 -->
${ user[prop] }
```

## 1.6 访问集合中的数据       当把数据放到集合中，把集合再添加到内置对象中，使用EL表达式获取数据

```jsp
<!-- student为ArrayList类型的对象 -->
${student[0].name}
```

## 1.7 常用的内置对象	

| 类别       | 标识符           | 描述                                              |
| ---------- | ---------------- | ------------------------------------------------- |
| JSP        | pageContext      | 处理当前页面                                      |
| 作用域     | pageScope        | 同页面作用域属性名称和值有关的Map类               |
|            | requestScope     | 当前请求                                          |
|            | sessionScope     | 当前会话                                          |
|            | applicationScope | 当前服务器                                        |
| 请求参数   | param            | 接收前端页面传过来的请求参数                      |
|            | paramValues      | 把请求参数的所有值作为一个String数组来存储的Map类 |
| 请求头     | header           | 获取一个请求头的单个值                            |
|            | headerValues     | 请求头的多个值                                    |
| Cookie     | cookie           | 获取请求中附带的cookie信息                        |
| 初始化参数 | initParam        | 获取上下文初始化参数                              |

## 1.8     EL表达式中实现运算        常用的运算符

### （1）算术运算符

* +
* -
* *
* /                 两个整数相除保留小数部分，不取整
* %

### （2）关系运算符    比较运算符

​	== ，!=,>,>=,<,<=

### (3) 逻辑运算符

​	&&，||，！

### （4）条件运算符

```jsp
${条件表达式?语句1:语句2}
```

### （5）验证运算符

```jsp
${empty 表达式}
返回布尔值判断表达式是否为"空"值，null值、无元素的集合或数组，长度为零的String被认为是空值
```



# 2. JSTL标签（熟悉）

* JSTL被称为JSP标准标签库，JSTL是用来取代**JSP中的Java代码**

## 2.1 使用方式

* jar包添加
* 在JSP页面中使用**taglib指定引入jstl标签库**，方式为：

```jsp
<!-- prefix属性用于指定库前缀 -->
<!-- uri属性用于指定库的标识 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
```

## 2.2 常用核心标签

###   (1) 输出标签	out

```jsp
<c:out></c:out>		用来将指定内容输出的标签 
<c:out value="Hello World"></c:out>
```

### （2）设置标签	set		设置属性值，可以设置变量，设置对象	

```jsp
<c:set></c:set>		用来设置属性范围值的标签，向指定范围的内置对象中设置属性
<%-- 表示设置一个名字为name的属性，对应的数值为zhangfei  有效范围为：page --%>
<%-- pageContext.setAttribute("name","zhangfei") --%>
<c:set var="name" value="zhangfei" scope="page"></c:set>
<%-- 使用out标签打印出来 --%>
<c:out value="${name}"></c:out>

<%-- 设置一个对象的属性值并打印出来 --%>
<jsp:useBean id="person" class="com.lagou.demo01.Person" scope="page"></jsp:useBean>
<c:set property="name" value="gaunyu" target="${person}"></c:set>
<c:out value="${person.name}"></c:out>
```

### （3）删除标签	remove	删除属性	

```jsp
<c:remove></c:remove>	用来删除指定数据的标签
<%-- 设置一个属性值并打印 --%>
<c:set var="name" value="liubei"></c:set>
<c:out value="${name}"></c:out>
<%-- 删除这个属性值后再次打印 --%>
<c:remove var="name"></c:remove>
<c:out value="${name}" defalut="无名"></c:out>	<%-- 默认值为无名 --%>
```

### （4）单条件判断标签	if	满足条件就执行，否则跳过

```jsp
<c:if test="EL条件表达式">
	满足条件执行
</c:if>

<%-- 设置一个变量以及对应的数值 --%>
<c:set var="age" value="20" scope="page"></c:set>
<c:out value="${age}"></c:out>

<%-- 判断该年龄是否成年，若成年则提示已经成年了 --%>
<c:if test="${age>=18}">
	<c:out value="已经成年了！"></c:out>
</c:if>
```

###   (5) 多条件判断标签	choose选择	 多种选择

```jsp
<c:choose>
	<c:when test="EL表达式">
    	满足条件执行
    </c:when>
    ...
    <c:otherwise>
    	不满足上述when条件时执行
    </c:otherwise>
</c:choose>



<%-- 设置一个变量代表考试的成绩并指定数值 --%>
<c:set var="score" value="60"></c:set>
<c:out value="${score}"></c:out>

<%-- 进行多条件判断和处理 --%>
<c:choose>
	<c:when test="${score>60}">
    	<c:out value="成绩不错，继续加油哦！"></c:out>
    </c:when>
    <c:when test="${score==60}">
    	<c:out value="60分万岁，多一分浪费！"></c:out>
    </c:when>
    <c:otherwise>
    	<c:out value="革命尚未成功，同志仍需努力！"></c:out>
    </c:otherwise>
</c:choose>
```

### (6) 循环标签	循环结构	多个元素的遍历	forEach

```jsp
<c:forEach var="循环变量"  items="集合">
	...
</c:forEach>

<%
	// 准备一个数组并初始化
	String[] sArr = {"11","22","33","44","55"};
	pageContext.setAttribute("sArr",sArr);
%>

<%-- 使用循环标签遍历数组中的所有元素 --%>
<c:forEach var="ts" items="${sArr}">
	<c:out value="${ts}"></c:out>
</c:forEach>

<%-- 跳跃性遍历 间隔为2  也就是跳过一个遍历一个 --%>
<c:forEach var="ts" items="${sArr}" step="2">
	<c:out value="${ts}"></c:out>
</c:forEach>

<%-- 指定起始和结尾位置  从下标1开始到3结束，包含1和3 --%>
<c:forEach var="ts" items="${sArr}" begin="1" end="3">
	<c:out value="${ts}"></c:out>
</c:forEach>
```



## 2.3 常用的函数标签	 大量打包的具体功能的方法	${fn:...}

```jsp
<%-- 导入 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
```



## 2.4  常用的格式化标签	

```jsp
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	// 获取当前系统时间
	Date date = new Date();
	pageContext.setAttribute("date",date);
%>

当前系统时间为：${date}
<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
```



## 2.5 自定义标签		自己定义标签库

* 编写标签类继承SimpleTagSupport类或TagSupport类并重写doTag方法或doStartTag方法

```java
public class HelloTag extends SimpleTagSupport {
    private String name;
    
    ...
        
    @Override
    public void doTag() throws JspException,IOException{
        // 获取输出流
        JspWriter out = this.getJspContext().getOut();
        // 写入数据到浏览器
        out.write("自定义标签哦！" + name);
        // 关闭流对象
        out.close();
    }
}
```

* 定义标签库文件(tld标签库文件)并配置到WEB-INF下：

```xml
...
<tlib-version>1.0</tlib-version>
<short-name>my</short-name>			前缀
<uri>http://lagou.com</uri>			标识

<tag>
	<name>hello</name>				标签名
    <tag-class>com.lagou.demo01.HelloTag</tag-class>
    <body-content>empty</body-content>
    <attribute>
    	<name>name</name>
        <required>true</required>
    </attribute>
</tag>
```

* 在JSP中用taglib指令引入标签库

```jsp
<%@ taglib prefix="my" uri="http://lagou.com" %>
<my:hello name="zhangfei"></my:hello>
```

