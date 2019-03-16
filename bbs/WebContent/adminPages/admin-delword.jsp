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

var editFlag=undefined; //设置一个编辑标记

function submitForm(){
	$('#fm').form('submit', {   
		url:"<%=request.getContextPath()%>/noword?flag=add",      
	    success:function(data){  
	    	if(data=1){

				$("#dlg").dialog('close');
				$.messager.alert('提示','成功','info');	
				$("#dg").datagrid('reload');		
			}else{
				$.messager.alert('提示','失败','info');	
			}
					
	    }   
	}); 
}




	$(function() {
		$('#dg').datagrid({
		    url:'<%=request.getContextPath()%>/admin?flag=findAllWords',
		    fitColumns:true,
		    singleSelect:true,
		    pagination:true,
		    loadMsg:"正在加载数据。。。。",
		    fit:true,
		    toolbar:[
				{
					text:"添加敏感词",
					iconCls:'icon-add',
					handler:function(){
						$("#dlg").dialog("open").dialog("center").dialog("setTitle","添加主板块");
					}
				},
				'-'
			],
		    columns:[[
				{field:'sid',title:'编号',width:100,halign:'center',align:'center'},
				{field:'sname',title:'敏感词',width:200,halign:'center',align:'center'},				
				{field:'delWord',title:'敏感词删除',width:100,formatter:delWord,halign:'center',align:'center'}
		    ]],
		    
		  //当结束编辑时会触发onafterEdit事件
			onAfterEdit:function(rowIndex, rowData, changes){
				editFlag=undefined;
				
				var path="<%=request.getContextPath()%>";
				$.getJSON(path+"/topic?flag=updateBigBoard",rowData,function(data){
					if(data.code==1){
						$.messager.alert('提示','成功','info');	
					}else if(data.code==0){
						$.messager.alert('提示','服务器繁忙','info');	
					}
				},"text");
			},
			onDblClickCell: function(rowIndex, field, value){
				if(editFlag!=undefined){
					$("#dg").datagrid('endEdit',editFlag); //结束编辑
					
				}
				if(editFlag==undefined){
					var index=$("#dg").datagrid('getRowIndex',rowIndex); //获取选定行的索引
					$("#dg").datagrid('beginEdit',rowIndex);  //开始编辑
					editFlag=rowIndex;
				}
			}
		});
	});

	
	function delWord(value,row,index){		
		var path="<%=request.getContextPath()%>";
		var url=path+"/admin?flag=delById&sid="+row.sid;
		return '<a href="javascript:void(0)" onclick=delById("'+url+'")>删除敏感词</a>';
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
						$("#dg").datagrid('reload');	
					}else{
						alert("删除失败!!!");
						$("#dg").datagrid('reload');	
					}
				},
				error:function(){
					alert("删除异常!!!");
					$("#dg").datagrid('reload');	
				}
			})
		})
	}
	
</script>
<body class="easyui-layout">

	<div data-options="region:'center'" style="height:70%;">
		<table id="dg"></table>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:600px;height:200px;padding:10px 20px;" closed="true" ;buttons="#dlg-buttons">
		<div class="ftitle">添加敏感词</div>
		<form id="fm" method="post">
			
			
			<label>敏感词：</label>
			<input name="noword" class="easyui-textbox" required="required">
			
			
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width: 90px">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"  style="width: 90px">取消</a>
		</div>
	</div>
</body>
</html>