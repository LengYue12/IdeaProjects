<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lagou.demo.entity.Student" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/11
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示学员信息</title>
    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
</head>
<style>
    table{
        border: 5px black solid;
    }
</style>
<body>
<a href="display">显示</a>
<table class="table-striped table-bordered table-hover">
<%-- 判断session中的数据list集合是否为空 --%>
<c:if test="${sessionScope.list != null}">
<%--    遍历集合到表格--%>
    <c:forEach var="sd" items="${sessionScope.list}">
        <tr class="success">
            <td><c:out value="学员学号为：${sd.id}"></c:out></td>
            <td><c:out value="学员姓名为：${sd.name}"></c:out></td>
            <td><c:out value="学员性别为：${sd.sex}"></c:out></td>
            <td><c:out value="学员生日为：${sd.date}"></c:out></td>
            <td><c:out value="学员邮箱为：${sd.email}"></c:out></td>
            <td><c:out value="学员备注为：${sd.remarks}"></c:out></td>
            <td><c:out value="学员班级编号为：${sd.classGradeId}"></c:out></td>
        </tr>
    </c:forEach>
</c:if>
</table>
<%--<c:forEach var="sd" items="${sessionScope.list}">
    <c:out value="学员学号为：${sd.id}"></c:out><br/>
    <c:out value="学员姓名为：${sd.name}"></c:out><br/>
    <c:out value="学员性别为：${sd.sex}"></c:out><br/>
    <c:out value="学员生日为：${sd.date}"></c:out><br/>
    <c:out value="学员邮箱为：${sd.email}"></c:out><br/>
    <c:out value="学员备注为：${sd.remarks}"></c:out><br/>
    <c:out value="学员班级编号为：${sd.classGradeId}"></c:out><br/>
    <hr/>
</c:forEach>--%>
<%--<%
    List<Student> list = (List<Student>) session.getAttribute("list");
    if (list != null) {
        for (Student student : list) {
            %>
<p><h1><%= "学员学号为："+ student.getId() + " ，学员姓名为："+ student.getName() + " ，学员性别为："+ student.getSex()+
        " ，学员日期为："+ student.getDate() + " ，学员邮箱为："+ student.getEmail() +
        " ，学员备注为："+ student.getRemarks() %></h1></p>
<%
        }

    }
%>--%>
<a href="main.jsp">返回</a>
</body>
</html>
