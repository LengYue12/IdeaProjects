<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>request内置对象的使用</title>
</head>
<body>
<%
    System.out.println("获取到的服务器名称为：" + request.getServerName());
    System.out.println("获取到的服务器端口号为：" + request.getServerPort());
    // 通过内置对象设置属性信息，也就是存储数据
    request.setAttribute("name","guanyu");
%>
<%-- 实现转发效果，也就是服务器跳转 --%>
<jsp:forward page="requestTarget.jsp"></jsp:forward>
</body>
</html>
