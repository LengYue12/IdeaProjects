<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现常用运算符的使用</title>
</head>
<body>
<%
    // 通过内置对象设置属性的方式来准备操作数
    request.setAttribute("ia",5);
    request.setAttribute("ib",2);
    request.setAttribute("b1",true);
    request.setAttribute("b2",false);
%>

<%-- 实现上述所有操作数的获取和打印 --%>
ia的数值为:${ia}<br/>   <%-- 5 --%>
ib的数值为:${ib}<br/>   <%-- 2 --%>
b1的数值为:${b1}<br/>   <%-- true --%>
b2的数值为:${b2}<br/>   <%-- false --%>
<hr/>

<%-- 实现算术运算符的使用 --%>
ia+ib的结果为：${ia+ib}<br/>     <%-- 7 --%>
ia-ib的结果为：${ia-ib}<br/>     <%-- 3 --%>
ia*ib的结果为：${ia*ib}<br/>     <%-- 10 --%>
ia/ib的结果为：${ia/ib}<br/>     <%-- 2.5 --%>
ia%ib的结果为：${ia%ib}<br/>     <%-- 1 --%>
<hr/>

<%-- 实现关系运算符的使用 --%>
ia大于ib的结果为：${ia>ib}<br/>     <%-- true --%>
ia大于等于ib的结果为：${ia>=ib}<br/>     <%-- true --%>
ia小于ib的结果为：${ia<ib}<br/>     <%-- false --%>
ia小于等于ib的结果为：${ia<=ib}<br/>  <%-- false --%>
ia等于ib的结果为：${ia==ib}<br/>    <%-- false --%>
ia不等于ib的结果为：${ia!=ib}<br/>    <%-- true --%>
<hr/>

<%-- 实现逻辑运算符的使用 --%>
b1并且b2的结果为：${b1&&b2}<br/>   <%-- false --%>
b1或者b2的结果为：${b1||b2}<br/>   <%-- true --%>
b1取反的结果为：${!b1}<br/>   <%-- false --%>
b2取反的结果为：${!b2}<br/>   <%-- true --%>
<hr/>

<%
    String str1 = null;
    String str2 = "";
    String str3 = "hello";

    List<Integer> list1 = new LinkedList<>();
    List<Integer> list2 = Arrays.asList(11,22,33,44,55);

    request.setAttribute("str1",str1);
    request.setAttribute("str2",str2);
    request.setAttribute("str3",str3);
    request.setAttribute("list1",list1);
    request.setAttribute("list2",list2);
%>
<%-- 实现条件运算符和验证运算符的使用 --%>
ia和ib之间的最大值为：${ia>ib?ia:ib}<br/>
判断是否为空的结果是:${empty str1}<br/>   <%-- true --%>
判断是否为空的结果是:${empty str2}<br/>   <%-- true --%>
判断是否为空的结果是:${empty str3}<br/>   <%-- false --%>
判断是否为空的结果是:${empty list1}<br/>   <%-- true --%>
判断是否为空的结果是:${empty list2}<br/>   <%-- false --%>

</body>
</html>
