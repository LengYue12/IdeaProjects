<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/5/12
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- 文件上传的表单提交的特点
    表单的提交方式为post
    表单的enctype属性：必须设置为  multipart/form-data
    表单中必须有文件上传项，input框的type类型为：file，要有name属性
 --%>
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
        <input type="file" name="upload"/>
        <br/>
        <input type="text" name="name"/>
        <input type="text" name="password"/>

        <input type="submit" value="文件上传"/>
    </form>

    <img src="/upload/774e18fb92c9432b82d869c7a7e76982_冰冰.jpg">
</body>
</html>
