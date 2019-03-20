
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
var uid;
var time;
function selectAll(){
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
				{field:'postControl',title:'禁言管理',width:100,halign:'center',align:'center',formatter:getPostManage}
		    ]]
		});
	});
}
selectAll();
function getUidDisplay(value,row,index){
	uid=value;
	return row.uid;
}

function getStartTimeDisplay(value,row,index){
	var date=new Date(value);
	if(date=="Invalid Date"){
		return "-";
	}else{
		return date.toLocaleString();
	}
	
}

function getEndTimeDisplay(value,row,index){
	var date=new Date(value);
	time=date;
	if(date=="Invalid Date"){
		return "-";
	}else{
		return date.toLocaleString();
	}
}

function getPostManage(value,row,index){
	//alert("time"+time);
	//alert(row.uid);
	if(time=="Invalid Date"){
		return "-";
	}else{
		var path="<%=request.getContextPath()%>";
		var url=path+"/admin?flag=release&uid="+row.uid;
//		return '<a onclick="releaseById('+url+');">解除禁言</a>';
//		return '<a href="'+url+'">解除禁言</a>';
		return '<a onclick=releaseById("'+url+'")>解除禁言</a>';
	}
	
}

function releaseById(url){
	$(function(){
		$.ajax({
			async:true,
			url:url,
			dataType:"text",
			method:"get",
			success:function(data){
				if(data=="解除成功"){
					alert("解除成功");
					selectAll();
				}else{
					alert("解除失败");
					selectAll();
				}
			},
			error:function(){
				alert("服务器异常!!!");
				selectAll();
			}
		})
	})
}

</script>
<body>
	<table id="dg" style="height: 490px;"></table>
</body>
</html>