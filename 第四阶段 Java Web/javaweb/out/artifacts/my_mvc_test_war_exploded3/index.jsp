<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/11
  Time: 0:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>登录页面</title>
</head>
<body>
<form action="login" method="post">
  账号：<p><input type="text" name="userName"/></p>
  密码：<p><input type="password" name="pwd"/></p>
  <span style="color: red"><c:choose>
    <c:when test="${requestScope.error == null}">
      <c:out value=""></c:out>
    </c:when>
    <c:when test="${requestScope.error != null}">
      <c:out value="${requestScope.error}"></c:out>
    </c:when>
  </c:choose></span>
  <p><input type="submit" value="登录"/></p>
</form>
</body>
</html>
