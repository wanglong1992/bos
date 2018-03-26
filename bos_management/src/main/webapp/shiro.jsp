<%--
  Created by IntelliJ IDEA.
  User: CC
  Date: 2018/3/25
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="./js/jquery-1.8.3.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="js/easyui/themes/default/easyui.css">
<link rel="stylesheet"  type="text/css" href="./js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="./css/default.css">
<script src="./js/easyui/jquery.easyui.min.js"></script>
<body>
<%--<shiro:hasPermission name="courier:add">
     <a href="#" class="easyui-linkbutton">添加</a>
</shiro:hasPermission>--%>
<shiro:hasPermission name="courier:add">
    <a href="#" class="easyui-linkbutton">添加</a>
</shiro:hasPermission>
<shiro:hasPermission name="courier:edit">
    <a href="#" class="easyui-linkbutton">修改</a>
</shiro:hasPermission>
<shiro:hasPermission name="courier:delete">
    <a href="#" class="easyui-linkbutton">删除</a>
</shiro:hasPermission>
<shiro:hasPermission name="courier:list">
    <a href="#" class="easyui-linkbutton">查询</a>
</shiro:hasPermission>
用户名：<shiro:principal property="username"></shiro:principal>
<shiro:hasPermission name="waybill">aaa</shiro:hasPermission>
<shiro:hasRole name="dd"><a href="#" >dd</a></shiro:hasRole>
</body>
</html>
