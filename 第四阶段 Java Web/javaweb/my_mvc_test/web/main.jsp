<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/11
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生信息管理系统</title>
</head>
<body>
<table border="1" width="400px" align="center" cellspacing="0">
    <tr align="center">
        <td colspan="3">学生信息管理系统</td>
    </tr>
    <tr>
        <td rowspan="3">各种功能</td>
        <td><a href="add.jsp">添加学员信息  <span style="color:red;">
            <c:choose>
                <c:when test="${sessionScope.add == null}">
                    <c:out value=""></c:out>
                </c:when>
                <c:when test="${sessionScope.add != null}">
                    <c:out value="${sessionScope.add}"></c:out>
                </c:when>
            </c:choose>
        </span></a></td>
        <td><a href="update.jsp">修改学员信息  <span style="color:red;">
            <c:choose>
                <c:when test="${sessionScope.update == null}">
                    <c:out value=""></c:out>
                </c:when>
                <c:when test="${sessionScope.update != null}">
                    <c:out value="${sessionScope.update}"></c:out>
                </c:when>
            </c:choose>
        </span></a></td>
    </tr>
    <tr>
        <td><a href="delete.jsp">删除学员信息  <span style="color:red;">
            <c:choose>
                <c:when test="${sessionScope.delete == null}">
                    <c:out value=""></c:out>
                </c:when>
                <c:when test="${sessionScope.delete != null}">
                    <c:out value="${sessionScope.delete}"></c:out>
                </c:when>
            </c:choose>
        </span></a></td>
        <td><a href="query.jsp">查询学员信息</a></td>
    </tr>
    <tr>
        <td><a href="display.jsp">显示学员信息</a></td>
        <td><a href="mainPage.jsp">返回主界面</a></td>
    </tr>
</table>
</body>
</html>
