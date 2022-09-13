<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现set标签的使用</title>
</head>
<body>
<%-- 表示设置一个名字为name的属性，对应的数值为zhangfei，有效范围为：page --%>
<%-- pageContext.setAttribute("name","zhangfei") --%>
<c:set var="name" value="zhangfei" scope="page"></c:set>
<%-- 使用out标签打印出来 --%>
<c:out value="${name}"></c:out>
<hr/>

<%-- 设置一个对象的属性值并打印出来 --%>
<jsp:useBean id="person" class="com.lagou.demo01.Person" scope="page"></jsp:useBean>
<c:set property="name" value="guanyu" target="${person}"></c:set>
<c:set property="age" value="35" target="${person}"></c:set>
<c:out value="${person.name}"></c:out>
<c:out value="${person.age}"></c:out>
</body>
</html>
