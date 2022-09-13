<%@ page import="java.util.List" %>
<%@ page import="com.lagou.module01.entity.Student" %>
<%--
  Created by IntelliJ IDEA.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生信息管理界面</title>
    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <script src="./js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min.js" type="text/javascript"></script>
</head>
<style type="text/css">
    .pagination_div {
        margin: 0 auto;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>
<body>
<div>
    <div class="table-responsive" >
        <%-- 绘制一个10行7列的表格用于展示所有学生信息，最开始没有学生信息时表格中数据全部为空即可，让表格填充整个页面 --%>
        <table class="table">
            <%-- 提供相关功能对应的按钮，其中增加和修改操作共用同一个模态框即可，其它操作通过JS来处理 --%>
            <caption>
                <button class="btn btn-info" id="student_page">显示学生</button>
                <button class="btn btn-info" id="student_add" data-toggle="modal" data-target="#myModal">增加学生</button>
                <button class="btn btn-info" id="student_delete">删除学生</button>
                <button class="btn btn-info" id="student_modify" data-toggle="modal" data-target="#myModal">修改学生</button>
                <button class="btn btn-info" id="student_find">查询学生</button>
                <input type="text" id="s_code" placeholder="按学号查询" style="width: 130px; height: 33px">
            </caption>

            <%-- 绘制1行7列的表头信息 --%>
            <thead>
            <tr>
                <th>选择</th>
                <th>学号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>出生日期</th>
                <th>邮箱</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody id="tbody"></tbody>

            <%-- 绘制10行7列的假数据内容添加到表格中占位置 --%>
            <%-- 获取内置对象中放入的所有学生信息，在这里进行显示 --%>
            <%
                List<Student> studentList = (List<Student>) session.getAttribute("studentList");
                // System.out.println("集合的大小是：" + studentList.size());
                // System.out.println("--------------------------------------------------------");
                for (int i = 0; i < studentList.size(); i++) {
            %>
            <tr>
                <td><input type="checkbox" value="<%= studentList.get(i).getId() %>"/></td>
                <td> <%= studentList.get(i).getId() %> </td>
                <td> <%= studentList.get(i).getName() %> </td>
                <td> <%= studentList.get(i).getSex() %> </td>
                <td> <%= studentList.get(i).getBirthday() %> </td>
                <td> <%= studentList.get(i).getEmail() %> </td>
                <td> <%= studentList.get(i).getcomments() %> </td>
            </tr>
            <%
                }
            %>
        </table>

            <!-- 管理学生信息的模态框（Modal） -->
            <div class="modal " id="myModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal" aria-hidden="true">
                                <span>&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabelAdd">
                                学生信息
                            </h4>
                        </div>
                        <form action="studentAddServlet" method="post">
                            <input type="hidden" id="flag" name="flag"/>
                            <div class="modal-body" id="modal-body">
                                <label for="name">姓名:</label>
                                <input type="text" class="form-control" id="m_name" name="name" placeholder="请输入姓名">
                                <label for="name">性别:</label>
                                <input type="text" class="form-control" id="m_sex" name="sex" placeholder="请输入性别">
                                <label for="name">出生日期:</label>
                                <input type="text" class="form-control" id="m_birthday" name="birthday" placeholder="请输入出生日期">
                                <label for="name">邮箱:</label>
                                <input type="text" class="form-control" id="m_mail" name="mail" placeholder="请输入邮箱">
                                <label for="name">备注:</label>
                                <input type="text" class="form-control" id="m_comments" name="comments" placeholder="请输入备注">
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" >保存</button>
                                <button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
    </div>

    <div class="pagination_div">

        <ul class="pagination">
            <%
                // 声明一个局部变量i负责来实现当前页和上一页以及下一页的控制
                int i = 1;
                // 声明一个变量来获取总页数
                int len = studentList.size()/10+1;
            %>

            <li><a href="studentPageQueryServlet?page=<%=(i>1?--i:i)%>">&laquo;上一页</a></li>
            <%
                // 循环显示所有页码
                for (int j = 1; j <= len; j++) {
                    // 当页码过多时需要使用...来代表，此时的编写思路就是采用条件判断
                    // 若j的数值大于某个范围且小于某个范围时，把打印j的位置换成...即可
                    // 当然随着当前页的改变也会随之改变，这就是纯JavaSE功底了，没时间写了...
                    //if (j == i) {
            %>
            <li><a href="studentPageQueryServlet?page=<%=j%>" id=<%=j%>> <%=j%> </a></li>
            <%--<li class="active"><a href="studentPageQueryServlet?page=<%=i%>" id=<%=i%>> <%=i%> </a></li>--%>
            <%
                    //}
                }
            %>
            <li><a href="studentPageQueryServlet?page=<%=(i<len?++i:i)%>">&raquo;下一页</a></li>
            <%--<li><a href="#">总共<%=len%>页</a></li>--%>
        </ul>

    </div>
</div>
</body>
</html>

<script type="text/javascript">
    // 显示学生
    $("#student_page").on("click", function () {
        // 获取模块路径信息：/module01/manageStudent.jsp
        var modulePathName = window.document.location.pathname;
        // 获取模块名称：/module01
        var moduleName = modulePathName.substring(0, modulePathName.substr(1).indexOf('/') + 1);
        // 拼接结果为：id=1&id=2
        window.location.href = moduleName + "/studentPageQueryServlet";
    });

    // 增加学生
    $("#student_add").on("click", function (flag) {
        $("#flag").val("add");
        // alert($("#flag").val());
    });

    // 修改学生
    $("#student_modify").on("click", function (flag) {
        var checkboxs = $("input[type='checkbox']:checked");
        // 处理用户没有勾选的情况
        if (checkboxs.length == 0) {
            alert("请选择一个学生！");
            return false;
        } else if (checkboxs.length > 1) {
            alert("只能选择一个学生！");
            return false;
        } else {
            // alert(checkboxs[0].value);
            // 修改操作暂时支持一个个操作
            $("#flag").val(checkboxs[0].value);
            // alert($("#flag").val());
        }
    });

    // 删除用户
    $("#student_delete").on("click", function() {
        // 获取勾选的复选框
        var checkboxs = $("input[type='checkbox']:checked");
        // 根据勾选的复选框就后面对应的值拼接起来
        var idsString = "";
        for(var i = 0; i < checkboxs.length; i++) {
            idsString += "id="+checkboxs[i].value;
            if(i != checkboxs.length-1){
                idsString += "&";
            }
        }
        // 处理用户没有勾选的情况
        if (checkboxs.length==0) {
            alert("请选择一个用户！");
        } else {
            // 获取模块路径信息：/module01/manageStudent.jsp
            var modulePathName = window.document.location.pathname;
            // 获取模块名称：/module01
            var moduleName = modulePathName.substring(0, modulePathName.substr(1).indexOf('/') + 1);
            // 拼接结果为：id=1&id=2
            window.location.href = moduleName + "/studentDeleteServlet?" + idsString;
        }
    });

    // 查询学生
    $("#student_find").on("click", function() {
        var codeLike = $(this).siblings("#s_code").val();
        if (0 == codeLike.length) {
            alert("必须输入学号！");
            return false;
        }else if(/^\d{0,4}$/g.test(codeLike)){
            // 获取模块路径信息：/module01/manageStudent.jsp
            var modulePathName = window.document.location.pathname;
            // 获取模块名称：/module01
            var moduleName = modulePathName.substring(0, modulePathName.substr(1).indexOf('/') + 1);
            // 拼接结果为：id=1&id=2
            window.location.href = moduleName + "/studentQueryServlet?id=" + codeLike;
        }else{
            alert("学号必须是数字");
        }

    });
</script>
