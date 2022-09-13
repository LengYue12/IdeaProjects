<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Student student = (Student) session.getAttribute("student"); %>
<%@ page import="com.lagou.demo.entity.Student" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/11
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询学员信息</title>
</head>
<body>
<form action="query" method="post">
    学号:<input type="text" name="id" placeholder="请输入要查询的学员的学号"/>

    <c:if test="${sessionScope.student != null}">
        <c:out value="学员学号为：${sessionScope.student.id}"></c:out>
        <c:out value="姓名为：${sessionScope.student.name}"></c:out>
        <c:out value="性别为：${sessionScope.student.sex}"></c:out>
        <c:out value="生日为：${sessionScope.student.date}"></c:out>
        <c:out value="邮箱为：${sessionScope.student.email}"></c:out>
        <c:out value="备注为：${sessionScope.student.remarks}"></c:out>
        <c:out value="班级编号为：${sessionScope.student.classGradeId}"></c:out>
    </c:if>
    <%--<% if (student != null) {%>
    <h1>查询成功
        学员学号为：<%= ((Student)session.getAttribute("student")).getId()%><br/>
        姓名为：<%= ((Student)session.getAttribute("student")).getName()%><br/>
        性别为：<%= ((Student)session.getAttribute("student")).getSex()%><br/>
        日期为：<%= ((Student)session.getAttribute("student")).getDate()%><br/>
        邮箱为：<%= ((Student)session.getAttribute("student")).getEmail()%><br/>
        备注为：<%= ((Student)session.getAttribute("student")).getRemarks()%><br/>
    </h1>
    <% } %>--%>
    <div style="color: red"><c:choose>
        <c:when test="${requestScope.error == null}">
            <c:out value=""></c:out>
        </c:when>
        <c:when test="${requestScope.error != null}">
            <c:out value="${requestScope.error}"></c:out>
        </c:when>
    </c:choose></div><br/>
    <input type="submit" value="查询"/>
    <a href="main.jsp">返回</a>
</form>
</body>
</html>
