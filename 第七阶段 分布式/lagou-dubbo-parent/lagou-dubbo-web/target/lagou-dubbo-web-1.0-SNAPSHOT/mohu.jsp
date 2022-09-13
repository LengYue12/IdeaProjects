<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户列表</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
  <div class="row">
      <h3 style="text-align: center">用户信息列表</h3>
      <div class="col-lg-2"></div>
      <div class="col-lg-8">

          <table border="1" class="table table-bordered table-hover">
              <tr class="success">
                  <th>编号</th>
                  <th>姓名</th>
                  <th>密码</th>
                  <th>电话</th>
                  <th>时间</th>
              </tr>

<%--              要对模型中的list遍历      var="users"：表示现在遍历的对象--%>
              <c:forEach items="${list1}" var="users">

              <tr>
<%--                  动态获取，当前遍历对象里面的id属性值--%>
                  <td>${users.uid}</td>
                  <td>${users.username}</td>
                  <td>${users.password}</td>
                  <td>${users.phone}</td>
                  <td>${users.createtime}</td>
              </tr>
              </c:forEach>


          </table>
      </div>
      <div class="col-lg-2"></div>
  </div>
</div>
</body>

</html>

