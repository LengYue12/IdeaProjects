<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>pageContext内置对象属性的获取</title>
</head>
<body>
<%="获取到的pageContext内置对象中的属性值为：" + pageContext.getAttribute("name")%>    <%--null--%>
</body>
</html>
