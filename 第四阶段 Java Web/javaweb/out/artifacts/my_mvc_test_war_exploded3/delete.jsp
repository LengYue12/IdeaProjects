<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/11
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除学员信息</title>
</head>
<body>
<form action="delete" method="post">
    学号：<input type="text" name="id" placeholder="请输入要删除的学号"/>
    <span style="color:red;"><c:choose>
        <c:when test="${sessionScope.delete == null}">
            <c:out value=""></c:out>
        </c:when>
        <c:when test="${sessionScope.delete != null}">
            <c:out value="${sessionScope.delete}"></c:out>
        </c:when>
    </c:choose></span>
    <input type="submit" value="删除"/>
</form>
</body>
</html>
