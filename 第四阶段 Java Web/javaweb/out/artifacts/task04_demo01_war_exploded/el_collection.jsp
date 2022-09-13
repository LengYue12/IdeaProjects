<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: 张舒
  Date: 2022/4/17
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现集合中数据内容的获取和打印</title>
</head>
<body>
<%
    // 准备一个List集合并添加数据内容
    List<String> list = new LinkedList<>();
    list.add("two");
    list.add("one");
    list.add("three");
    // 将整个集合放入指定的内置对象中
    pageContext.setAttribute("list",list);

    // 准备一个Map集合并添加数据
    Map<String, Integer> map = new HashMap<>();
    map.put("one",1);
    map.put("two",2);
    map.put("th.ree",3);
    // 将整个集合放入指定的内置对象中
    pageContext.setAttribute("map",map);
%>

<%-- 使用EL表达式实现集合中数据内容的获取 --%>
集合中下标为0的元素是：${list[0]}<br/>    <%-- two --%>
集合中下标为1的元素是：${list[1]}<br/>    <%-- one --%>
集合中下标为2的元素是：${list[2]}<br/>    <%-- three --%>
<hr/>
<%-- 使用EL表达式实现Map集合中数据内容的获取 不支持下标 --%>
整个Map集合中的元素有：${map}<br/>
获取带有特殊字符key对应的数值为：${map["th.ree"]}<br/> <%-- 3 --%>
</body>
</html>
