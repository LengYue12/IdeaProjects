<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/21
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页面</title>
    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <script src="./js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<a href="main.jsp" class="btn-primary">管理学生</a>
<a href="manageClassGrade.jsp" class="btn-default">管理班级</a>
<button class="bg-info" id="a">在线人数：</button>
</body>
</html>
<script type="text/javascript">
    $("#a").on("click", function() {
        // 获取ServletContext对象中属性。在线人数
        alert("在线用户为：" + ${applicationScope.count})
    });
</script>
