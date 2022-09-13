<%@ page import="java.util.List" %>
<%@ page import="com.lagou.demo.entity.ClassGrade" %>
<%--
  Created by IntelliJ IDEA.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>班级信息管理界面</title>
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
        <%-- 绘制一个10行7列的表格用于展示所有班级信息，最开始没有班级信息时表格中数据全部为空即可，让表格填充整个页面 --%>
        <table class="table">
            <%-- 提供相关功能对应的按钮，其中增加和修改操作共用同一个模态框即可，其它操作通过JS来处理 --%>
            <caption>
                <button class="btn btn-info" id="classGrade_back">返回主界面</button>
                <button class="btn btn-info" id="classGrade_page">显示班级</button>
                <button class="btn btn-info" id="classGrade_add" data-toggle="modal" data-target="#myModal">增加班级</button>
                <button class="btn btn-info" id="classGrade_delete">删除班级</button>
                <button class="btn btn-info" id="classGrade_modify" data-toggle="modal" data-target="#myModal">修改班级</button>
                <button class="btn btn-info" id="classGrade_find">查询班级</button>
                <label for="s_code"></label><input type="text" id="s_code" placeholder="按班级编号查询" style="width: 130px; height: 33px">
            </caption>

            <%-- 绘制1行7列的表头信息 --%>
            <thead>
            <tr>
                <th>选择</th>
                <th>班级编号</th>
                <th>班级名称</th>
                <th>年级名称</th>
                <th>班主任名称</th>
                <th>班级口号</th>
                <th>班级人数</th>
            </tr>
            </thead>
            <tbody id="tbody"></tbody>

            <%-- 绘制10行6列的假数据内容添加到表格中占位置 --%>
            <%-- 获取内置对象中放入的所有班级信息，在这里进行显示 --%>
            <%-- 使用EL表达式和JSTL标签 --%>
            <%--                判断session对象中的数据classGradeList是否为空--%>
            <c:if test="${null != sessionScope.classGradeList && sessionScope.classGradeList.size() > 0}">
                <%--                遍历集合--%>
                <c:forEach var="classGradeList" items="${sessionScope.classGradeList}">
                    <tr>
                        <td><label>
                            <input type="checkbox" value="${classGradeList.classId}"/>
                        </label></td>
                        <td>${classGradeList.classId}</td>
                        <td>${classGradeList.className}</td>
                        <td>${classGradeList.gradeName}</td>
                        <td>${classGradeList.headTeacherName}</td>
                        <td>${classGradeList.classSlogan}</td>
                        <td>${classGradeList.classNumber}</td>
                    </tr>

                </c:forEach>

            </c:if>
        </table>

        <!-- 管理年级信息的模态框（Modal） -->
        <div class="modal " id="myModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal" aria-hidden="true">
                            <span>&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabelAdd">
                            班级信息
                        </h4>
                    </div>
                    <form action="classGradeAddServlet" method="post">
                        <input type="hidden" id="flag" name="flag"/>
                        <div class="modal-body" id="modal-body">
                            <label for="name">班级名称:</label>
                            <input type="text" class="form-control" id="m_className" name="className" placeholder="请输入名称">
                            <label for="name">年级名称:</label>
                            <input type="text" class="form-control" id="m_gradeName" name="gradeName" placeholder="请输入年级">
                            <label for="name">班主任名称:</label>
                            <input type="text" class="form-control" id="m_headTeacherName" name="headTeacherName" placeholder="请输入班主任名称">
                            <label for="name">班级口号:</label>
                            <input type="text" class="form-control" id="m_classSlogan" name="classSlogan" placeholder="请输入班级口号">
                            <label for="name">班级人数:</label>
                            <input type="text" class="form-control" id="m_classNumber" name="classNumber" placeholder="请输入班级人数">
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
            <%--            定义变量i初始值为1--%>
            <c:set var="i" value="1"></c:set>
            <%--    定义变量i初始值为集合的大小--%>
            <c:set var="len" value="${sessionScope.size()/10+1}"></c:set>

            <li><a href="classGradePageQueryServlet?page=" + ${i>1?i-1:i}>&laquo;上一页</a></li>
            <%--            遍历循环所有页码--%>
            <c:if test="${null != sessionScope.classGradeList && sessionScope.classGradeList.size() > 0}">
                <c:forEach var="j" begin="1" end="${len}" step="1">
                    <li><a href="classGradePageQueryServlet?page="+${j}>${j}</a></li>
                </c:forEach>
            </c:if>
            <li><a href="classGradePageQueryServlet?page=" + ${i<len?i+1:i}>&raquo;下一页</a></li>
        </ul>

    </div>
</div>
</body>
</html>

<script type="text/javascript">
    // 获取模块路径信息：/my_mvc_test/manageClassGrade.jsp
    var modulePathname = window.document.location.pathname;
    // 获取模块名称：/my_mvc_test
    var moduleName =  modulePathname.substring(0,modulePathname.substr(1).indexOf('/') + 1);

    // 返回主界面
    $("#classGrade_back").on("click",function () {
        location.href="mainPage.jsp";
    })
    // 显示班级
    $("#classGrade_page").on("click", function () {
        // 查询所有班级信息
        window.location.href = moduleName + "/classGradePageQueryServlet";
    });

    // 创建班级
    $("#classGrade_add").on("click", function (flag) {
        $("#flag").val("add");
        // alert($("#flag").val());
    });

    // 修改班级
    $("#classGrade_modify").on("click", function (flag) {
        var checkboxs = $("input[type='checkbox']:checked");
        // 处理用户没有勾选的情况
        if (checkboxs.length == 0) {
            alert("请选择一个班级！");
            return false;
        } else if (checkboxs.length > 1) {
            alert("只能选择一个班级！");
            return false;
        } else {
            // alert(checkboxs[0].value);
            // 修改操作暂时支持一个个操作
            $("#flag").val(checkboxs[0].value);
            // alert($("#flag").val());
        }
    });

    // 删除班级
    $("#classGrade_delete").on("click", function() {
        // 获取勾选的复选框
        var checkboxs = $("input[type='checkbox']:checked");
        // 根据勾选的复选框就后面对应的值拼接起来
        var idsString = "";
        for(var i = 0; i < checkboxs.length; i++) {
            idsString += "classId="+checkboxs[i].value;
            if(i != checkboxs.length-1){
                idsString += "&";
            }
        }
        // 处理用户没有勾选的情况
        if (checkboxs.length==0) {
            alert("请选择一个班级！");
        } else {
            window.location.href = moduleName + "/classGradeDeleteServlet?" + idsString;
        }
    });

    // 查询班级
    $("#classGrade_find").on("click", function() {
        var codeLike = $(this).siblings("#s_code").val();
        if (0 == codeLike.length) {
            alert("必须输入班号！");
            return false;
        }else if(/^\d{0,4}$/g.test(codeLike)){
            // 获取模块路径信息：/module01/manageStudent.jsp
            var modulePathName = window.document.location.pathname;
            // 获取模块名称：/module01
            var moduleName = modulePathName.substring(0, modulePathName.substr(1).indexOf('/') + 1);
            // 拼接结果为：id=1&id=2
            window.location.href = moduleName + "/classGradeQueryServlet?classId=" + codeLike;
        }else{
            alert("班号必须是数字");
        }

    });
</script>
