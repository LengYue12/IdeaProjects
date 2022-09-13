<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>new jsp</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<script>

    /*给用户名输入框元素绑定失去焦点的表单事件
   * 将此函数绑定到输入用户名的input框元素的blur事件*/
    $(function () {

        // 通过id选择器给input框绑定失去焦点事件
        $("#name").blur(function () {

            // 用户输入完用户名，失去焦点后。
            // 获取用户名
           let name = $(this).val();

           // 判断输入的用户名不为空和空串
           if (name != null && name != ""){
               // 向后台发送请求，验证用户名是否可用
               // 使用JQuery get请求方式发送异步请求
               // 响应成功，需要给span标签加内容，设置颜色
               $.get("/checkName2",{userName:name},function (param) {   // url,data数据,响应成功的回调函数，服务端响应的数据类型为JSON
                   // true表示用户名已经存在
                   if (param.flag){
                       // 设置span的内容体
                       // 数据在后台map转换的JSON中，也就是响应回的param
                       $("#span").html("<font color='red'>" + param.msg + "</font>");
                   } else if (!param.flag){
                       // false，取反 表示用户名不存在
                       // 设置span的内容体
                       $("#span").html("<font color='#4b0082'>" + param.msg + "</font>");
                   }  // 服务端响应的数据类型为JSON
               },"json");
           }
        })
    });


</script>
<body>

    <form action="#" method="post">
        用户名：<input type="text" id="name" name="userName" placeholder="请输入用户名">
        <span id="span" style="color: red"></span><br/>
        密码：<input type="text" id="password" name="password" placeholder="请输入密码">
    </form>

</body>
</html>
