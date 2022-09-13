<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP页面的转发实现</title>
</head>
<body>
<jsp:forward page="target.jsp">
    <jsp:param name="name" value="zhanfei"/>
</jsp:forward>
</body>
</html>
