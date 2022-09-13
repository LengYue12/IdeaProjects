<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>new jsp</title>
</head>
<body>
    <input type="text" />
    <input type="button" value="JQuery方式发送异步请求" onclick="run()"/>
</body>
<script src="jquery-1.8.3.min.js"></script>
<script>
    function run() {
        // JQuery方式 Ajax方式 发送异步请求
        $.ajax({
            url:"/ajax", // 请求路径
            async:true, // true 表示异步 false 表示同步
            data:{ name: "张飞" },   // 携带的数据
            type:"post", // 请求方式
            dataType:"text", // 返回数据的格式 text 普通文本
            // 回调函数
            success:function (param) {
                alert("响应成功：" + param);
            },
            error:function () {
                alert("响应失败了！");
            }
        });
    }

</script>
</html>
