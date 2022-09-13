<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/6/21
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--从模型中借助el表达式把对应的value值取出来--%>
    <h3>success...${username}</h3>

<% System.out.println("视图执行了..."); %>
</body>
</html>
