<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>new jsp</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<script>

    // JQuery Get请求方式 实现异步
    function run2() {

        // 1.准备url
        let url = "/login2";

        // 2.准备携带的数据
        let data = {name:"吴亦凡"};

        // 3.发送Get请求
        // url，请求的数据data，回调函数的参数param是服务端响应回来的参数，type 响应数据的类型
        $.get(url,data,function (param) {

            alert("GET异步请求 响应成功：" + param);
        },"text");
    }

    // JQuery Post请求方式 实现异步
    function run3() {

        // 1.准备url
        let url = "/login2";

        // 2.准备携带的数据
        let data = {name:"蔡徐坤"};

        // 3.发送Post请求
        // url，请求的数据data，回调函数的参数param是服务端响应回来的参数，type 响应数据的类型
        $.post(url,data,function (param) {

            alert("post异步请求 响应成功：" + param);
        },"text");
    }

    // JQuery Ajax请求方式 实现异步
    function run4() {

        $.ajax({
            url:"/login2",
            async:true, // 是否异步，true 异步
            data:{name:"孙笑川"},    // 传递的请求参数
            type:"POST",                 // 请求方式 post get
            dataType:"text",                 // 返回数据的数据类型
            // 响应成功的回调函数
            success:function (param) {
                alert("响应成功！" + param);
            },// 响应失败
            error:function () {
                alert("响应失败了！");
            }
        });
    }


</script>
<body>

    局部刷新 <input type="text" /><br/>

    <input type="button" value="JQuery Get方式发送异步请求" onclick="run2()"/><br/>

    <input type="button" value="JQuery Post方式发送异步请求" onclick="run3()"/><br/>

    <input type="button" value="JQuery Ajax方式发送异步请求" onclick="run4()"/><br/>

</body>
</html>
