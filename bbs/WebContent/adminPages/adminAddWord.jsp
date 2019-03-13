<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height:100%">
<head>
<script type="text/javascript">
	
</script>
<meta charset="UTF-8">
<title></title>
<%@ include file="links.jsp" %>
</head>
<body id="cc" class="easyui-layout" style="width:100%;height:100%;">
	    <form id="ff" method="post">
		    <div>
				<label for="name">敏感词:</label>
				<input class="easyui-validatebox" type="text" name="word" id ="word" data-options="required:true" />
		    </div>
		    <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">添加</a>
		</form>

<script type="text/javascript">
	$(function(){
		$("#btn").click(function(){
			$.ajax({
				async:true,
				method:"get",
				url:"<%=request.getContextPath()%>/word?flag=add",
				data:{"word":$("#word").val()},
				dataType:"text",
				success:function(data){
					if(data=="添加成功"){
						alert("添加成功");
						window.location="";
					}
				},
				error:function(){
					alert("添加失败!!!");
				}
			})
		})
	})
	
</script>
</body>
</html>