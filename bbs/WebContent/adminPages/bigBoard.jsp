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
	var editFlag=undefined; //设置一个编辑标记
	function submitForm(){
		$('#fm').form('submit', {   
			url:"<%=request.getContextPath()%>/topic?flag=addBigBoard",      
		    success:function(data){   	    			        
				$("#dlg").dialog('close');
				$.messager.alert('提示','成功','info');	
				$("#bigBoardList").datagrid('reload');				
		    }   
		}); 
	}
	$(function() {
		
		$('#bigBoardList').datagrid({
			title:'主板块列表',
			pagination:true,
			loadMsg:"正在加载数据。。。。",
			rownumbers:false,
			fit:true,
			singleSelect:true,
			nowrap:true,
			fitColumns:true,
			url:"<%=request.getContextPath()%>/topic?flag=showBigBoardList",
			toolbar:[
				{
					text:"添加主板块",
					iconCls:'icon-add',
					handler:function(){
						$("#dlg").dialog("open").dialog("center").dialog("setTitle","添加主板块");
					}
				},
				{
					text:"修改",
					iconCls:'icon-edit',
					handler:function(){
						//选中一行编辑
						var rows=$("#bigBoardList").datagrid('getSelections');
						if(rows.length==1){  //选中一行的话触发事件
							//如果当前状态为编辑状态，则退出编辑状态
							if(editFlag!=undefined){
								$("#bigBoardList").datagrid('endEdit',editFlag); //结束编辑
								
							}
							if(editFlag==undefined){
								var index=$("#bigBoardList").datagrid('getRowIndex',rows[0]); //获取选定行的索引
								$("#bigBoardList").datagrid('beginEdit',index);  //开始编辑
								editFlag=index;
							}
							
						}
					}
				},
				'-',
				{
					text:"保存",
					iconCls:'icon-save',
					handler:function(){
						$("#bigBoardList").datagrid('endEdit',editFlag); //会触发onafterEdit事件，在哪里写更新代码
						
					}
				},
				'-',
				{
					text:"撤销",
					iconCls:'icon-redo',
					handler:function(){
						editFlag=undefined;
						$("#bigBoardList").datagrid('rejectChanges');
					}
				},
				'-'
			],
			columns:[[
				{
					field:'boardid',
					title:'板块编号',
					width:'20%',
					halign:'center',
					align:'center',
					sortable:"true"
				},
				{
					field:'boardname',
					title:'板块名',
					width:'40%',
					align:'center',
					editor:{ //设置其为了编辑
						type:'validatebox', //设置编辑格式
						options:{
							required:true //设置编辑规则属性
						}
					}
				},
				{
					field:'sonTotal',
					title:'子版块数量',
					width:'20%',
					sortable:"true",
					align:'center'
					
				},
				{
					field:'caozuo',
					title:'操作',
					width:'20%',
					formatter:delWord,
					halign:'center',
					align:'center'
					
				}
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
						$("#bigBoardList").datagrid('endEdit',editFlag); //结束编辑
						
					}
					if(editFlag==undefined){
						var index=$("#bigBoardList").datagrid('getRowIndex',rowIndex); //获取选定行的索引
						$("#bigBoardList").datagrid('beginEdit',rowIndex);  //开始编辑
						editFlag=rowIndex;
					}
				}
		});
	});
	
	function delWord(value,row,index){		
		var path="<%=request.getContextPath()%>";
		var url=path+"/topic?flag=delBigBoard&boardid="+row.boardid;
		return '<a href="javascript:void(0)" onclick=delById("'+url+'")>删除板块</a>';
	}
	
	function delById(url){
		$(function(){
			$.ajax({
				async:true,
				url:url,
				dataType:"text",
				method:"post",
				success:function(data){
					if(data=1){
						$.messager.alert('提示','成功','info');	
						$("#bigBoardList").datagrid('reload');	
					}else{
						$.messager.alert('提示','失败','info');	
						$("#bigBoardList").datagrid('reload');	
					}
				}
			})
		})
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center'" style="height:70%;">
		<table id="bigBoardList"></table>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:600px;height:200px;padding:10px 20px;" closed="true" ;buttons="#dlg-buttons">
		<div class="ftitle">添加主板块</div>
		<form id="fm" method="post" novalidate="novalidate">
			
			
			<label>主板块名：</label>
			<input id="boardname" name="boardname" class="easyui-textbox" required="required">
			
			
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm()" style="width: 90px">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancle" onclick="javascript:$('#dlg').dialog('close')" style="width: 90px">取消</a>
		</div>
	</div>
</body>
</html>