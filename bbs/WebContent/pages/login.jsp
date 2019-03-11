<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!--      导航        -->
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>
</DIV>
<!--      用户登录表单        -->
<DIV class="t" style="MARGIN-TOP: 15px" align="center">
	<FORM name="loginForm" action="<%=request.getContextPath() %>/bbsUser" method="post">
		<input type="hidden" name="flag" value="userLogin">
		<br/>
		用户名 &nbsp;
		<INPUT class="input" tabIndex="1"  type="text"      maxLength="20" size="35" name="uName" value="${param.uName}">
		<br/>
		密　码 &nbsp;
		<INPUT class="input" tabIndex="2"  type="password"  maxLength="20" size="40" name="uPass" value="${param.uPass}">
		<br/>
		验证码 &nbsp;
		<input class="input" tabindex="1" type="text" name="code">	
		<img id="code" src="<%=request.getContextPath() %>/createCode.jsp" onclick="updateCode()" style="vertical-align: bottom;">
		<a href="javascript:updateCode()">换一张</a>
		<script type="text/javascript">
			function updateCode() {
				var img=document.getElementById("code");
				//相同的url请求，浏览器会在缓存里加载数据并不会往服务器重新发送，所以后面加一个随机数
				img.src="<%=request.getContextPath() %>/createCode.jsp?"+Math.random();
			}
		</script>
		<br/>
		<INPUT class="btn"  tabIndex="6"  type="submit" value="登 录">
		<br>
		<font style="color: red;">${msg}</font>
	</FORM>
</DIV>
 <%@ include file="bottom.jsp"%>