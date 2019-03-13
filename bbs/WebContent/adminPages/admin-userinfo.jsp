<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.text.SimpleDateFormat.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@ include file="links.jsp" %>
</head>
<script type="text/javascript">
$(function() {
	$('#dg').datagrid({
	    url:'<%=request.getContextPath()%>/bbsUser?flag=findUserInfo',
	    fitColumns:true,
	    singleSelect:true,
	    columns:[[
			{field:'uid',title:'编号',width:100,halign:'center',align:'center'},
			{field:'uname',title:'用户名',width:100,halign:'center',align:'center'},
			{field:'time',title:'发敏感词次数',width:100,halign:'center',align:'center'},
			{field:'starttime',title:'禁言开始时间',width:100,halign:'center',align:'center',formatter:getStartTimeDisplay},
			{field:'endtime',title:'禁言结束时间',width:100,halign:'center',align:'center',formatter:getEndTimeDisplay},
			{field:'postControl',title:'禁言管理',width:100,halign:'center',align:'center'}
	    ]]
	});
});
function getStartTimeDisplay(value,row,index){
	var date=new Date(value);
	return date.toLocaleString();
}
function getEndTimeDisplay(value,row,index){
	var date=new Date(value);
	return date.toLocaleString();
}
</script>
<body>
	<table id="dg" style="height: 490px;"></table>
</body>
</html>