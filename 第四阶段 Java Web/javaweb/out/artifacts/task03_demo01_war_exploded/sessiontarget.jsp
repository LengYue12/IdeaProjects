<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取session内置对象中的数据</title>
</head>
<body>
<%="获取到的属性值为：" + session.getAttribute("name")%>
</body>
</html>
