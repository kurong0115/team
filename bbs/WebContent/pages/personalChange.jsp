<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
function isusername(input) {
	$.post(path+"/bbsUser",{"flag":"isUserName","uName":input.value},function(data){
		if(data.trim()=="1"){
			document.getElementById("info").style.color="red";
			document.getElementById("info").innerHTML="用户名已存在";
		}else if(data.trim()=="2"){
			document.getElementById("info").style.color="red";
			document.getElementById("info").innerHTML="用户名不能为空";
		}else{
			document.getElementById("info").style.color="green";
			document.getElementById("info").innerHTML="用户名可用";
		}
	},"text");
}
function checkUserInfo() {
	if (document.changeForm.uName.value == "") {
		alert("用户名不能为空");
		return false;
	}
}
function gender(){
	if( document.changeForm.gender.value == "1" ){
		document.getElementById("#gender").innerHTML="女";
	}else{
		document.getElementById("#gender").innerHTML="男";
	}
}
</script>	
	<DIV>
	
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;个人中心
	</DIV>
	<div id="usermessage" style="width: 88.5%; border: 2px solid #E0F0F9;margin-left: 5.6%;height: 280px" align="center">
	<form onSubmit="return checkUserInfo()" name="changeForm" 
	action="<%=request.getContextPath() %>/bbsUser" method="post">
	<input type="hidden" name="flag" value="personalChange">
		<table >
			<tr>
				<td height="10" colspan="1"><img
					src="image/head/${user.head }"></td>
			</tr>
			<tr>
				<td height="20" colspan="2"><p class="style3">姓名：<input type="text" value="${user.uname }" name="uName" onblur="isusername(this)"></p></td>
				<td><font id="info"></font></td>
			</tr>
			<tr>
				<td height="20" colspan="3"><p class="style3">
						性别 &nbsp;
			女<input type="radio" name="gender" value="1" <c:if test="${user.gender == 1 }">checked = "checked"</c:if>/>
			男<input type="radio" name="gender" value="2" <c:if test="${user.gender == 2 }">checked = "checked"</c:if>/>
					</p></td>
			</tr>
			<tr>
				<td height="20" colspan="4"><p class="style3">邮箱：<input type="email" value="${user.email}"></td>
			</tr>
			<tr>
				<td><INPUT class="btn" tabIndex="4" type="submit" value="修改"></td>
			</tr>
		</table>
		</form>
		<%
			String msg = (String) request.getAttribute("msg");
			if (msg == null) {
				msg = "";
			}
			if (msg.equals("修改成功")) {
		%>

		<script type="text/javascript" language="javascript">
					alert("<%=msg%>");
					
					window.location='<%=request.getContextPath()%>/pages/personal.jsp';
		</script>
		<%
			}else if(!msg.equals("")){
		%>
			<script type="text/javascript" language="javascript">
					alert("<%=msg%>");
			</script>
		<%
			}
		%>
</div>