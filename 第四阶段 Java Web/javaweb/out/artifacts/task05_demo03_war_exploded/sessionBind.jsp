<%@ page import="com.lagou.demo03.Person" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/19
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现session中对象的绑定和解除</title>
</head>
<body>
<%
    // 准备一个Person类型的对象
    Person person = new Person();
    person.setName("zhangfei");
    person.setAge(30);
    // 将对象与session对象进行绑定
    session.setAttribute("person",person);
    // 解除绑定
    session.removeAttribute("person");
%>
</body>
</html>
