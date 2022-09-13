<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现一个简单页面负责向JSP页面传递参数</title>
</head>
<body>
<form action="el_param.jsp" method="post">
    姓名：<input type="text" name="name"/><br/>
    爱好：<input type="checkbox" name="hobby" value="唱歌"/>唱歌<br/>
         <input type="checkbox" name="hobby" value="跳舞"/>跳舞<br/>
         <input type="checkbox" name="hobby" value="学习"/>学习<br/>
    <input type="submit" value="提交"/><br/>
</form>
</body>
</html>
