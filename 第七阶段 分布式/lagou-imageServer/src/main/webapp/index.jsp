<%--
  Created by IntelliJ IDEA.
  User: lengy
  Date: 2022/7/30
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传图片</title>
</head>
<body>

<%--    上传图片，文件与文字相比较，属于内容较大，必须使用post方式提交--%>
<%--        上传文件和普通文本有区别，action接收参数也会区别对待，所以声明带文件提交的表单为多部件表单--%>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <p><input type="file" name="fname"/></p>
        <button>提交</button>
    </form>

</body>
</html>
