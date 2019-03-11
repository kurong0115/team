<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
		    url:'<%=request.getContextPath()%>/bbsUser?flag=findUser',
		    fitColumns:true,
		    singleSelect:true,
		    columns:[[
				{field:'uid',title:'编号',width:100,halign:'center',align:'center'},
				{field:'uname',title:'用户名',width:200,halign:'center',align:'center'},
				{field:'head',title:'头像',width:200,formatter:getHeadDisplay,halign:'center',align:'center'},
				{field:'regtime',title:'注册时间',width:200,halign:'center',align:'center'},
				{field:'gender',title:'性别',width:100,formatter:getGenderDisplay,halign:'center',align:'center'}
		    ]]
		});
	});
	
	function getHeadDisplay(value,row,index) {
		return  '<img src="../image/head/'+value+'">';
	}
	
	function getGenderDisplay(value,row,index) {
		if(value=='2'){
			return '男';
		}
		if(value=='1'){
			return '女';
		}
	}
	
</script>
<body>
	<table id="dg" style="height: 490px;"></table>
</body>
</html>