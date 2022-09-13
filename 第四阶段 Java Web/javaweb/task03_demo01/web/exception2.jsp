<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/6
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发生另外一种异常的页面</title>
</head>
<body>
<%
    int[] arr = new int[5];
    System.out.println(arr[5]); // 数组下标越界异常
%>
</body>
</html>
