<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/5/14
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script>

        // 原生JS方式发送 Ajax请求
        function run() {
            //1.创建 核心对象
            var xmlhttp;

            //2.判断浏览器类型
            if (window.XMLHttpRequest) {
                xmlhttp=new XMLHttpRequest();
            } else {
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }



            //3.建立连接
            /**
             * 三个参数:
             *      1.请求方式 get post
             *      2.请求资源的路径
             *      3.是否为异步 是 or 否
             */
            xmlhttp.open("GET","/login?username=tom",true);

            //4.发送请求
            xmlhttp.send();

            //5.获取响应结果
            /**
             * 什么时候获取响应数据?
             *      在服务器响应成功后获取
             */
            //监听readyState状态改变
            xmlhttp.onreadystatechange=function() {
                //readyState==4 响应已经就绪, 200 访问成功
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    //获取响应数据
                    var text = xmlhttp.responseText;
                    alert("响应结果：" + text);
                }
            }
        }

        // JQuery GET请求方式
        function run2() {

            // 1.准备url
            var url = "/login";

            // 2.数据
            var data = {username:"xiaoming"};

            // 3.使用JQuery发送GET请求
            $.get(url,data,function (param) {

                alert("GET异步请求 响应成功:" + param)
            },"text");
        }


        // JQuery POST请求方式
        function run3() {

            // 1.准备url
            var url = "/login";

            // 2.数据
            var data = {username:"tangxin"};

            // 3.使用JQuery发送GET请求
            $.post(url,data,function (param) {

                alert("POST异步请求 响应成功:" + param)
            },"text");
        }


        // JQuery Ajax请求方式
        function run4() {

            $.ajax({
                url:"/hello_maven/login",
                async:true, // 是否异步,true是异步
                data:{username:"ikun"}, // 请求参数
                type:"POST", // 请求的方式
                dataType:"text", // 返回数据的数据类型   text 普通文本

                // 响应成功的函数
                success:function (param) {
                    alert("响应成功！" + param);
                },
                // 响应失败的函数
                error:function () {
                    alert("响应失败了！")
                }
            });

        }
        
    </script>
</head>
<body>
    <input type="button" value="原生JS发送异步请求" onclick="run()"/><br/>
    局部刷新 <input type="text"><br/>

    <input type="button" value="JQuery Get方式发送异步请求" onclick="run2()"/><br/>

    <input type="button" value="JQuery Post方式发送异步请求" onclick="run3()"/><br/>

    <input type="button" value="JQuery Ajax方式发送异步请求" onclick="run4()"/><br/>
</body>
</html>
