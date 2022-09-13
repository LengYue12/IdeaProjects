<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/19
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现请求中属性状态的改变</title>
</head>
<body>
<%
    // 实现属性的添加
    request.setAttribute("name","zhangfei");
    // 实现属性的修改
    request.setAttribute("name","guanyu");
    // 实现属性的删除操作
    request.removeAttribute("name");
%>
</body>
</html>
