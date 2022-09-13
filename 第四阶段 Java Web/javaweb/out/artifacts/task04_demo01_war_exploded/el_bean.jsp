<%@ page import="com.lagou.demo01.Person" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现Bean对象中属性的获取和打印</title>
</head>
<body>
<%-- 使用JSP语法规则中的原始方式实现对象的创建和设置以及输出 --%>
<%
    Person person = new Person();
    person.setName("zhangfei");
    person.setAge(30);
    pageContext.setAttribute("person",person);

    pageContext.setAttribute("var","age");
%>

<%--<%= "获取到的姓名为：" + person.getName()%><br/>--%>
<%--<%= "获取到的年龄为：" + person.getAge()%><br/>--%>
<%-- 使用EL表达式实现属性的获取和打印 --%>
<%--获取到的姓名是:${person.name}<br/>--%>
<%--获取到的年龄是：${person.age}<br/>--%>
<%-- 另外一种写法 --%>
<%--${person["name"]}<br/>--%>
<%--${person["age"]}<br/>--%>
<%-- 测试一下动态取值的效果 --%>
动态取值的结果为：${person[var]}
</body>
</html>
