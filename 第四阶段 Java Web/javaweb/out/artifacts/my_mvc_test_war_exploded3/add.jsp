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
    <title>增加学员信息</title>
</head>
<body>
<form action="add" method="post">
    姓名:<input type="text" name="name"/><br/>
    性别:<input type="text" name="sex"/><br/>
    日期:<input type="text" name="date"/><br/>
    邮箱:<input type="text" name="email"/><br/>
    备注:<input type="text" name="remarks"/><br/>
    班级编号:<input type="text" name="classGradeId"/><br/>
    <span style="color:red;"><c:choose>
        <c:when test="${sessionScope.add == null}">
            <c:out value=""></c:out>
        </c:when>
        <c:when test="${sessionScope.add != null}">
            <c:out value="${sessionScope.add}"></c:out>
        </c:when>
    </c:choose></span>
    <input type="submit" value="添加"/>
</form>
</body>
</html>
