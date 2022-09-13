<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现常用函数标签的使用</title>
</head>
<body>
<%
    pageContext.setAttribute("var","Hello World!");
%>
原始字符串为:${var}<br/>  <%-- Hello World! --%>
判断该字符串是否包含指定字符串的结果为：${fn:contains(var,"Hello")}<br/>    <%-- true --%>
将字符串中所有字符转换为大写的结果为：${fn:toUpperCase(var)}<br/>          <%-- HELLO WORLD! --%>
将字符串中所有字符转换为小写的结果为：${fn:toLowerCase(var)}<br/>          <%-- hello world! --%>

</body>
</html>
