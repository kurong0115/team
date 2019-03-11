<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function checkUserInfo() {
		if (document.loginForm.loginName.value == "") {
			alert("用户名不能为空");
			return false;
		}
		if (document.loginForm.loginPass.value == "") {
			alert("密码不能为空");
			return false;
		}
	}
</script>
</head>
<body style="width: 70%;margin-left: 20%">
	管理员登录
	<form method="POST" name="loginForm" onSubmit="return checkUserInfo()"
		action="<%=request.getContextPath()%>/admin" enctype="multipart/form-data">
		<input type="hidden" name="flag" value="adminLogin">
		<table width="100%" border="0">
			<tr>
				<td width="10%">&nbsp;</td>
				<td width="10%">&nbsp;</td>
				<td width="10%">&nbsp;</td>
				<td width="40%">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td valign="middle" align="center">用户名：</td>
				<td valign="top"><input type="text" name="loginName" size="19"
					class="input"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td valign="middle" align="center">密&nbsp;&nbsp;码：</td>
				<td valign="top"><input type="password" name="loginPass"
					size="20" class="input"></td>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td colspan="2" align="center"><input type="submit"
					name="Submit" value="登录"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" style="color: red" valign="middle" align="center">${msg}</td>
			</tr>
			<tr>
				<td height="33">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>