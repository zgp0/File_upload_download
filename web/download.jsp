<%--
  Created by IntelliJ IDEA.
  User: 周光璞
  Date: 2023/5/21
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件下载</title>
    <base href="<%=request.getContextPath()+"/"%>>">
</head>
<body>
<h1>文件下载</h1>
<a href="fileDownloadServlet?name=dog.jpg">点击下载小狗图片</a><br/><br/>
<a href="fileDownloadServlet?name=消愁.mp3">点击下载 消愁.mp3</a><br/><br/>
</body>
</html>
