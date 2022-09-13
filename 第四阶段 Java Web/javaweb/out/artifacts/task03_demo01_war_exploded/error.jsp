<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>异常处理的页面</title>
</head>
<body>
<%
    if (exception != null) {
        out.println("异常的错误信息为：" + exception.getMessage());
    }
%>
</body>
</html>
