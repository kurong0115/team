<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@ include file="links.jsp" %>
</head>

<script type="text/javascript">
function selectAll(){
	$(function() {
		$('#dg').datagrid({
		    url:'<%=request.getContextPath()%>/admin?flag=findAllWords',
		    fitColumns:true,
		    singleSelect:true,
		    columns:[[
				{field:'sid',title:'编号',width:100,halign:'center',align:'center'},
				{field:'sname',title:'敏感词',width:200,halign:'center',align:'center'},				
				{field:'delWord',title:'敏感词删除',width:100,formatter:delWord,halign:'center',align:'center'}
		    ]]
		});
	});
}
	selectAll();
	
	function delWord(value,row,index){		
		var path="<%=request.getContextPath()%>";
		var url=path+"/admin?flag=delById&sid="+row.sid;
		return '<a onclick=delById("'+url+'")>删除敏感词</a>';
	}
			
	function delById(url){
		$(function(){
			$.ajax({
				async:true,
				url:url,
				dataType:"text",
				method:"post",
				success:function(data){
					if(data=="ok"){
						alert("删除成功");
						selectAll();
					}else{
						alert("删除失败!!!");
					}
				},
				error:function(){
					alert("删除异常!!!");
				}
			})
		})
	}
	
</script>
<body>

	<table id="dg" style="height: 490px;"></table>
</body>
</html>