<%@ page import="com.lagou.demo03.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/10
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页面</title>
</head>
<body>
<h1>登录成功，欢迎<%= ((User)session.getAttribute("user")).getUserName() %>使用！</h1>
</body>
</html>
