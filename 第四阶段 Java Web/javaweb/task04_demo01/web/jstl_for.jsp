<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现循环标签的使用</title>
</head>
<body>
<%
    // 准备一个数组并初始化
    String[] sArr = {"11","22","33","44","55"};
    pageContext.setAttribute("sArr",sArr);
%>

<%-- 使用循环标签遍历数组中的所有元素 --%>
<c:forEach var="ts" items="${sArr}">
    <c:out value="${ts}"></c:out>
</c:forEach>
<hr/>

<%-- 跳跃性遍历 间隔为2 也就是跳过一个遍历一个 --%>
<c:forEach var="ts" items="${sArr}" step="2">
    <c:out value="${ts}"></c:out>
</c:forEach>
<hr/>

<%-- 指定起始和结尾位置 从下标1开始到下标3结束，包含1和3--%>
<c:forEach var="ts" items="${sArr}" begin="1" end="3">
    <c:out value="${ts}"></c:out>
</c:forEach>
</body>
</html>
