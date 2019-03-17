<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="header.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	
	var path="<%=request.getContextPath()%>"
	function checkUserInfo() {
		if (document.pwdForm.upass.value == "") {
			alert("密码不能为空");
			return false;
		}
		if (document.getElementById("samePwd").innerHTML == "密码不一致") {
			alert("密码不一致");
			return false;
		}
	}
	
	function isSamePwd() {
		if(document.pwdForm.newpass.value!=document.pwdForm.newpass1.value){
			document.getElementById("samePwd").innerHTML="密码不一致";
		}
		if(document.pwdForm.newpass.value==document.pwdForm.newpass1.value){
			document.getElementById("samePwd").innerHTML="";
		}
	}
	
	
	
	$(function() {
		$("#showMsg").click(function() {
			$("#usermessage").show();
			$("#pwdchange").hide();
		})
		$("#pwd").click(function() {
			$("#usermessage").hide();
			$("#pwdchange").show();
		})
		
		$("#pwdchange").hide();
	})
</script>

<style type="text/css">
.show a:hover {
	color: blue;
}

#usermessage {
	width: 50%;
	float: left;
	display: inline;
}

#pwdchange {
	width: 50%;
	float: left;
	display: inline;
}
</style>

<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;个人中心
	
</DIV>
<table width="89%"  align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td class="show" width="243" height="50" align="center"
			valign="middle" bgcolor="#E0F0F9"><h3 id="showMsg">
				<a href="javascript:void(0)">信息展示</a>
			</h3></td>
		<td class="show" width="268" height="50" align="center"
			bgcolor="#E0F0F9"><h3 id="pwd">
				<a href="javascript:void(0)">修改密码</a>
			</h3></td> 
	</tr>
</table>



	<div id="usermessage" style="width: 88.5%; border: 2px solid #E0F0F9;margin-left: 5.6%;height: 200px" align="center">
		<table >
			<tr>
				<td height="10" colspan="1"><img
					src="image/head/${user.head }"></td>
			</tr>
			<tr>
				<td height="20" colspan="2"><p class="style3">姓名：${user.uname }</p></td>
			</tr>
			<tr>
				<td height="20" colspan="3"><p class="style3">
						性別：
						<c:if test="${user.gender == 2 }">
							男
						</c:if>
						<c:if test="${user.gender == 1 } ">
							女
						</c:if>
					</p></td>
			</tr>
			<tr>
				<td height="20" colspan="4"><p class="style3">注册时间：${user.regtime }</p></td>
			</tr>
		</table>
</div>

	<div id="pwdchange" style="width: 88.5%; border: 2px solid #E0F0F9;margin-left: 5.6%; height: 200px" align="center">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<FORM onSubmit="return checkUserInfo()" name="pwdForm"
						action="<%=request.getContextPath()%>/bbsUser" method="post">
						<input type="hidden" name="flag" value="pwdchange"> 
						<br />原&nbsp;密&nbsp;码
						&nbsp; 
						<INPUT class="input" tabIndex="1" type="text"
							maxLength="20" size="20" name="upass">
						<font id="info"></font>
						 <br />新&nbsp;密&nbsp;码 &nbsp;
						  <INPUT class="input" tabIndex="2" type="password" maxLength="20"
							size="20" name="newpass" > 
						<br />重复密码 &nbsp; 
						<INPUT class="input" tabIndex="3" type="password" maxLength="20"
							size="20" name="newpass1" onblur="isSamePwd()"> <font
							style="color: red;" id="samePwd"></font> 
							<br /> <br /> 
							<INPUT class="btn" tabIndex="4" type="submit" value="修改"> <br />
					</FORM>
				</div>

			</div>
		</div>

		<%
			String msg = (String) request.getAttribute("msg");
			if (msg == null) {
				msg = "";
			}
			if (msg.equals("修改成功,请重新登录")) {
		%>

		<script type="text/javascript" language="javascript">
					alert("<%=msg%>");
					
					window.location='<%=request.getContextPath()%>/bbsUser?flag=logAgain';
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

