<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现choose标签的使用</title>
</head>
<body>
<%-- 设置一个变量代表考试成绩并指定数值 --%>
<c:set var="score" value="59" scope="page"></c:set>
<c:out value="${score}"></c:out>
<hr/>

<%-- 进行多条件判断和处理 --%>
<c:choose>
    <c:when test="${score>60}">
        <c:out value="成绩不错，继续加油哦！"></c:out>
    </c:when>
    <c:when test="${score == 60}">
        <c:out value="60分万岁，多一分浪费！"></c:out>
    </c:when>
    <c:otherwise>
        <c:out value="革命尚未成功，同志仍需努力！"></c:out>
    </c:otherwise>
</c:choose>
</body>
</html>
