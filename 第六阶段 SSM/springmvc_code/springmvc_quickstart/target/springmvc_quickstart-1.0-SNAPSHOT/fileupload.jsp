<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/6/23
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--单文件上传前台代码--%>

<%--编写满足文件上传三要素的表单
        1. 表单的提交方式为post提交，这样才能有请求体
        2. 表单的enctype属性要修改为 multipart/form-data
        3. 表单中要有文件上传项   input 框的type 属性 为file

--%>
<form action="${pageContext.request.contextPath}/fileupload" method="post" enctype="multipart/form-data">
    名称:<input type="text" name="username"/><br/>
<%--    input框 的type 为file，表示为文件上传项
            后台controller里的方法获取参数要用到name值
--%>
    文件:<input type="file" name="filePic"/><br/>
    <input type="submit" value="单文件上传"/>
</form>






<%--实现多文件上传
        在表单中添加文件上传项
--%>
<form action="${pageContext.request.contextPath}/filesupload" method="post" enctype="multipart/form-data">
    名称:<input type="text" name="username"/><br/>
    文件1:<input type="file" name="filePic"/><br/>
    文件2:<input type="file" name="filePic"/><br/>
    <input type="submit" value="多文件上传"/>
</form>
</body>
</html>
