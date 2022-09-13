<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/6/21
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--${pageContext.request.contextPath}动态的来获取当前项目路径  springmvc_quickstart  a标签的请求方式：get请求--%>
    <a href="${pageContext.request.contextPath}/user/simpleParam?id=1&username=杰克">
        基本类型参数
    </a>

<%--form表单  该表单提交的请求方式为post方式--%>
<form action="${pageContext.request.contextPath}/user/pojoParam" method="post">
    编号：<input type="text" name="id"/><br/>
    用户名：<input type="text" name="username"/><br/>
    <input type="submit" value="对象类型参数"/>
</form>


<%--form表单  演示获取数组类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/arrayParam" method="post">
<%--    向后台传递的参数值为 value的值--%>
    编号：<input type="checkbox" name="ids" value="1"/>1<br/>
    编号：<input type="checkbox" name="ids" value="2"/>2<br/>
    编号：<input type="checkbox" name="ids" value="3"/>3<br/>
    编号：<input type="checkbox" name="ids" value="4"/>4<br/>

    <input type="submit" value="数组类型参数"/>
</form>


<%--form表单  演示获取集合类型请求参数--%>
<form action="${pageContext.request.contextPath}/user/queryParam" method="post">
<%--    输入的值封装到QueryVo对象里面 的keyword属性上
            name的值和QueryVo里的属性名保持一致，这样才能调用set方法进行值的注入
--%>
    搜索关键字:
        <input type="text" name="keyword"/><br/>
<%--把请求参数封装到QueryVo对象里面的User对象，就是给User对象里面的id属性和username属性进行赋值--%>
    user对象:
        <input type="text" name="user.id" placeholder="编号"/>
        <input type="text" name="user.username" placeholder="姓名"/><br/>

<%--    封装List集合
            当前文本框要去封装到QueryVo对象里面的userList属性上
                而userList属性是集合类型
                    name="userList[0]"：表示要给userList集合里面的第一个元素进行赋值,就是User对象的id和username
             相当于给userList中第一个User元素对象进行请求参数封装
--%>
    list集合:
        第一个元素：
        <input type="text" name="userList[0].id" placeholder="编号"/>
        <input type="text" name="userList[0].username" placeholder="姓名"/><br/>

        第二个元素：
        <input type="text" name="userList[1].id" placeholder="编号"/>
        <input type="text" name="userList[1].username" placeholder="姓名"/><br/>


<%--  name="userMap"：表示当前input框的值要封装到QueryVo对象里面的userMap集合中
            表示设置map集合中的第一个元素，key是u1，根据从userMap中取出的第一个元素的value值就是user对象
                所以要把当前文本框的值赋值给user对象里面的id属性和username属性
  --%>
    Map集合:
        第一个元素:
        <input type="text" name="userMap['u1'].id" placeholder="编号"/>
        <input type="text" name="userMap['u1'].username" placeholder="姓名"/><br/>
        第二个元素:
        <input type="text" name="userMap['u2'].id" placeholder="编号"/>
        <input type="text" name="userMap['u2'].username" placeholder="姓名"/><br/>

    <input type="submit" value="复杂类型"/>
</form>


<%--form表单  演示自定义类型转换器：错误的产生    当前前台传递的日期类型不是 2012/12/12 这样的类型的话，在用Date类型接收时会报错
            前台日期格式为 / 开头的话，Springmvc内置的类型转换器可以完成String类型到日期类型的转换，但是年月日之间得要以 / 开头
                若想要别的日期格式，就要自定义类型转换器，不再使用Springmvc提供的日期类型转换器了，在日期类型转换时走自己定义的类型转换器
--%>
<form action="${pageContext.request.contextPath}/user/converterParam" method="post">

<%--    前台向后台传递的参数是日期类型--%>
    生日:<input type="text" name="birthday" placeholder="生日"/>

    <input type="submit" value="自定义类型转换器"/>
</form>



<%--演示@RequestParam注解--%>
<a href="${pageContext.request.contextPath}/user/findByPage?pageNo=2">
    分页查询
</a>



</body>
<%--引入jquery.js--%>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
</html>
