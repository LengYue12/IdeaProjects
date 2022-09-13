<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现if标签的使用</title>
</head>
<body>
<%-- 设置一个变量以及对应的数值 --%>
<c:set var="age" value="17" scope="page"></c:set>
<c:out value="${age}"></c:out>
<hr/>

<%-- 判断该年龄是否成年，若成年则提示已经成年了 --%>
<c:if test="${age>=18}">
    <c:out value="已经成年了！"></c:out>
</c:if>
</body>
</html>
