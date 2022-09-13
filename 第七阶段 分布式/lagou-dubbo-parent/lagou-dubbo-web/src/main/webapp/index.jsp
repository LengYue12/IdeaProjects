<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>首页</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    </script>
  </head>
  <body>
  <div align="center">

    <form id="zhu">
      <p>姓名: <input name="username" type="text" placeholder="请输入姓名"/></p>
      <p>密码: <input name="password" type="password" placeholder="请输入密码"></p>
      <p>电话: <input name="phone" type="text" placeholder="请输入电话"></p>
      <p><input type="button" id="btn" value="register"></p>
    </form>
  	<a
            href="${pageContext.request.contextPath}/findAll" style="text-decoration:none;font-size:33px">查询用户信息列表
	</a>

    <form action="${pageContext.request.contextPath}/findByUsername" method="post">
      <p>模糊查询用户：<input  type="text" name="username" placeholder="请输入姓名关键字"/></p>
      <p><button class="btn btn-primary" type="submit">查询</button></p>
    </form>
  </div>
  </body>
  <script>
    $("#btn").on("click",function(){
      $.post("/register", $("#zhu").serialize() ,function(){},"json");
    });

  </script>
</html>