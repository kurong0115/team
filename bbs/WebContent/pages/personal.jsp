<%@ page language="java"  pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	var path="<%=request.getContextPath()%>"
	function checkUserInfo() {
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
	
</script>
<table width="89%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td width="243" height="50" align="center" valign="middle" bgcolor="#E0F0F9"><h3 onclick="showmessage()">信息展示</h3></td>
		<td width="268" height="50" align="center" bgcolor="#E0F0F9"><h3 onclick="changepwd()">密碼修改</h3></td>  
		</tr>
</table>
<style>
#usermessage{width:50%; float:left; display:inline;}
#pwdchange {width:50%; float:left; display:inline;}
</style>
<div id="usermessage" text-align="center">
	<table width="89%" height="418" border="0" align="center">
		<tr>
		<td height="10" colspan="1" ><img src="image/head/${user.head }"></td>
	</tr>
	<tr>
		<td height="20" colspan="2"><p class="style3">姓名：${user.uname }</p></td>
	</tr>
	<tr>
		<td height="20" colspan="3"><p class="style3">性別：
		<c:if test="${user.gender < 2 }">
			<c:out value="男"></c:out>
		</c:if>
		<c:if test="${user.gender > 1 } ">
			<c:out value="女"></c:out>
		</c:if>
		</p></td>
	</tr>
	<tr>
		<td height="20" colspan="4"><p class="style3">註冊時間：${user.regtime }</p></td>
	</tr>	
</table>
</div>
<div id="pwdchange" text-align="center";>
	<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<FORM onSubmit="return checkUserInfo()" name="pwdForm" 
					action="<%=request.getContextPath() %>/bbsUser" method="post">
		<input type="hidden" name="flag" value="pwdchange">
		<br/>原&nbsp;密&nbsp;码 &nbsp;
			<INPUT class="input" tabIndex="1" tryp="text" maxLength="20" size="20" name="upass">
			<font id="info" ></font>
		<br/>新&nbsp;密&nbsp;码 &nbsp;
			<INPUT class="input" tabIndex="2" type="password" maxLength="20" size="20" name="newpass">
		<br/>重复密码 &nbsp;
			<INPUT class="input" tabIndex="3" type="password" maxLength="20" size="20" name="newpass1" onblur="isSamePwd()">
			<font style="color: red;" id="samePwd"></font>
		<br/>
		</div>
	</div>
</div>
<INPUT class="btn" tabIndex="4" type="submit" value="修改">
<%
			String msg=(String)request.getAttribute("msg");
			if(msg==null){
				msg="";
			}
			if(msg.equals("修改成功,请重新登录")){
				%>
				
				<script type="text/javascript" language="javascript">
					alert("<%=msg%>");
					
					window.location='<%=request.getContextPath() %>/bbsUser?flag=logAgain';
				</script> 
				<%
			}
		%>
<font style="color: red;">${msg}</font>
</FORM>
</div>
<p height="50" colspan="5" align="center">©CopyRight 2019-2020 www.ycinfo.com AllRights Reserved</p>
 <%@ include file="bottom.jsp"%>
