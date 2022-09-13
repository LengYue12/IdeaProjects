<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现请求参数数值的获取</title>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
%>
<%-- 使用JSP语法中的原始方式获取请求参数值 --%>
<%--<%= "姓名是：" + request.getParameter("name")%><br/>
<%= "爱好是：" + Arrays.toString(request.getParameterValues("hobby"))%><br/>--%>

<%-- 使用EL表达式中的方式获取请求参数值 --%>
姓名是：${param.name}<br/>
爱好是：${paramValues.hobby[0]}<br/>
</body>
</html>
