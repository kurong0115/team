<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 100%">
<head>
<meta charset="UTF-8">
<title>bbs后台</title>
<%@ include file="links.jsp" %>
</head>
<script type="text/javascript">
	$(function() {
		var bbsUserTree=[
			{
				"id":1,
				"text":"用户浏览",
				"attributes":{
					"url":"<iframe width='100%' height='100%' src='adminPages/admin-user.jsp'/>",
					"iframe":""
				}
			},
			{
				"id":2,
				"text":"用户禁言管理",
				"attributes":{
					"url":"<iframe width='100%' height='100%' src='adminPages/admin-userinfo.jsp'/>",
					"iframe":""
				}
			}
		];
		
		
		
		var bbsTopicTree=[
			{
				"id":1,
				"text":"主板块管理",
				"attributes":{
					"url":"<iframe width='100%' height='100%'  src='adminPages/bigBoard.jsp'/>",
					"iframe":""
				}
			},
			{
				"id":2,
				"text":"子板块管理",
				"attributes":{
					"url":"<iframe width='100%' height='100%'  />",
					"iframe":""
				}
			}
		];
		
		var bbsWordTree=[
			{
				"id":1,
				"text":"敏感词添加",
				"attributes":{
					"url":"<iframe width='100%' height='100%' src='adminPages/adminAddWord.jsp'/>",
					"iframe":""
				}
			},
			{
				"id":2,
				"text":"敏感词删除",
				"attributes":{
					"url":"<iframe width='100%' height='100%' src='adminPages/admin-delword.jsp'/>",
					"iframe":""
				}
			}
		];
		showTree("bbsUserTree",bbsUserTree);
		showTree("bbsTopicTree",bbsTopicTree);
		showTree("bbsWordTree",bbsWordTree);
		
		function showTree(treeId,treeData) {
			$("#"+treeId).tree({
				data:treeData,
				
				onClick:function(node){
					if(node&&node.attributes){
						openTab(node);
					}
				}
			});
		}
		
		function openTab(node) {
			if($("#tt").tabs("exists",node.text)){
				$("#tt").tabs("select",node.text);
			}else{
				$("#tt").tabs("add",{
					title:node.text,
					closable:true,
					content:node.attributes.url
				})
			}
		}
	});
</script>

<body class="easyui-layout">
	<div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north'" style="height:100px">
			<img alt="" src="image/logo.gif">
			<div style="float: right; width: 400px;">
				<font style="margin-top: 40px; display: inline-block;">管理员：${admin.raname} 欢迎使用bbs后台管理</font>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="admin?flag=loginOut" >退出</a>
				
			</div>
		</div>
		<div data-options="region:'south'" style="height:50px;"></div>

		<div data-options="region:'west',split:true" title="West" style="width:200px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				
				<div title="用户管理">
					<ul id="bbsUserTree" class="easyui-tree">
					</ul>
				</div>
				<div title="主题管理" >
					<ul id="bbsTopicTree" class="easyui-tree">
					</ul>
				</div>
				<div title="敏感词管理" >
					<ul id="bbsWordTree" class="easyui-tree">
					</ul>
				</div>
			</div>
		
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'" title="主界面">
			<div id="tt" class="easyui-tabs" style="width:100%; height:100%;" data-options="fit:true,border:false">
			    <div title="欢迎使用" style="font-size: 30px;" >
					欢迎使用
			    </div>
			   
			</div>
		</div>
	</div>
</body>
</html>