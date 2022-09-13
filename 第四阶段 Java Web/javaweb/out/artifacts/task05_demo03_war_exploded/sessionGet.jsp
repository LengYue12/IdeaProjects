<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/19
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现session中数据的获取</title>
</head>
<body>
<%
    Object student = session.getAttribute("student");
    System.out.println("获取到的数据为：" + student);
%>
</body>
</html>
