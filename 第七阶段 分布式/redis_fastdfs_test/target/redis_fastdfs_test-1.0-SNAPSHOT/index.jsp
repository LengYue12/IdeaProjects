<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>上传视频</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
                <span>小视频上传</span>
                <div class="form-group">
                    <input type="file" name="filePic" class="btn btn-warning">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">提交</button>
                </div>
                <span style="color: blue">
                    <c:if test="${not empty fileSystem}">
                        文件上传成功!<br>
                        原文件名：${fileSystem.fileName}<br>
                        上传后的路径：${fileSystem.filePath}<br>
                        <a href="http://www.fdfsstorage.com/${fileSystem.fileId}" type="btn">查看文件</a>
                    </c:if>
                </span>
            </form>
        </div>
    </div>
</div>
</body>
</html>
