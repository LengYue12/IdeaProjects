<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/19
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现会话中属性状态的改变</title>
</head>
<body>
<%
    // 增加属性
    session.setAttribute("name","caocao");
    // 修改属性
    session.setAttribute("name","caoren");
    // 删除属性
    session.removeAttribute("name");
%>
</body>
</html>
