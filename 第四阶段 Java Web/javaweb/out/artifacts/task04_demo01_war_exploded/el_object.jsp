<%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现从内置对象中获取数据并打印</title>
</head>
<body>
<%
//    pageContext.setAttribute("name1","pageContext对象中的属性值：zhangfei");
//    request.setAttribute("name2","request对象中的属性值：guanyu");
//    session.setAttribute("name3","session对象中的属性值：liubei");
//    application.setAttribute("name4","application对象中的属性值：zhaoyun");
    pageContext.setAttribute("name","pageContext对象中的属性值：zhangfei");
    request.setAttribute("name","request对象中的属性值：guanyu");
    session.setAttribute("name","session对象中的属性值：liubei");
    application.setAttribute("name","application对象中的属性值：zhaoyun");
%>

<%-- 使用JSP中原始方式获取数据和打印 --%>
<%--<%= "name1的数值为：" + pageContext.getAttribute("name1") %><br/> &lt;%&ndash; zhanfei &ndash;%&gt;--%>
<%--<%= "name2的数值为：" + request.getAttribute("name2") %><br/> &lt;%&ndash; guanyu &ndash;%&gt;--%>
<%--<%= "name3的数值为：" + session.getAttribute("name3") %><br/> &lt;%&ndash; liubei &ndash;%&gt;--%>
<%--<%= "name4的数值为：" + application.getAttribute("name4") %><br/> &lt;%&ndash; zhaoyun &ndash;%&gt;--%>
<%-- 使用EL表达式实现获取数据和打印 --%>
<%--name1的数值为：${name1}<br/>--%>
<%--name2的数值为：${name2}<br/>--%>
<%--name3的数值为：${name3}<br/>--%>
<%--name4的数值为：${name4}<br/>--%>
<h1>name的数值为：${name}</h1>
</body>
</html>
