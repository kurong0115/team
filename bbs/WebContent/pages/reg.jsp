<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="header.jsp"%>


<Link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/style/style.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	var path="<%=request.getContextPath()%>"
		/* 发送验证码 */
		function sendcode(){
			if( $("#email").val() != null ){				
				$.ajax({
					url:"/bbs/bbsUser?flag=sendcode",
					method:"post",
					dataType:"text",
					data:{"email":$("#email").val()},
					async:true,
					success:function(data){
						if(data!=null){
							alert("发送成功");
						}		
					},
					error:function(){
						alert("服务器异常！！！");
					} 
				})
				}else{
					alert("请输入您的邮箱地址！！");
				}
		}
	function checkUserInfo() {
		if (document.regForm.uName.value == "") {
			alert("用户名不能为空");
			return false;
		}
		if (document.regForm.uPass.value == "") {
			alert("密码不能为空");
			return false;
		}
		if (document.getElementById("samePwd").innerHTML == "密码不一致") {
			alert("密码不一致");
			return false;
		}
	}
	
	function isSamePwd() {
		if(document.regForm.uPass.value!=document.regForm.uPass1.value){
			document.getElementById("samePwd").innerHTML="密码不一致";
		}
		if(document.regForm.uPass.value==document.regForm.uPass1.value){
			document.getElementById("samePwd").innerHTML="";
		}
	}
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
</script>



	


<BR/>
<!--      导航        -->
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>
</DIV>
<!--      用户注册表单        -->
<DIV  class="t" style="MARGIN-TOP: 15px" align="center">
	<FORM onSubmit="return checkUserInfo()" name="regForm" 
	action="<%=request.getContextPath() %>/bbsUser" method="post">
		<input type="hidden" name="flag" value="userReg">
		<br/>用&nbsp;户&nbsp;名 &nbsp;
			<INPUT class="input" tabIndex="1" tryp="text" maxLength="20" size="20" name="uName" onblur="isusername(this)">
			<font id="info" ></font>
		<br/>密&nbsp;&nbsp;&nbsp;&nbsp;码 &nbsp;
			<INPUT class="input" tabIndex="2" type="password" maxLength="20" size="20" name="uPass">
		<br/>重复密码 &nbsp;
			<INPUT class="input" tabIndex="3" type="password" maxLength="20" size="20" name="uPass1" onblur="isSamePwd()">
			<font style="color: red;" id="samePwd"></font>
		<br/>邮箱 &nbsp;
			<INPUT class="input" tabIndex="4" type="email" maxLength="20" size="20" name="email" id="email">
			<a href="javascript:void(0)" onclick="sendcode()">获取验证码</a>
		</br>验证码 &nbsp;
			<INPUT class="input" tabIndex="5" type="text" maxLength="20" size="20" name="regcode">
		<br/>性别 &nbsp;
			女<input type="radio" name="gender" value="1">
			男<input type="radio" name="gender" value="2" checked="checked" />
		<br/>请选择头像 <br/>
		<image src="<%=request.getContextPath() %>/image/head/1.gif"/><input type="radio" name="head" value="1.gif" checked="checked">
		<img src="<%=request.getContextPath() %>/image/head/2.gif"/><input type="radio" name="head" value="2.gif">
		<img src="<%=request.getContextPath() %>/image/head/3.gif"/><input type="radio" name="head" value="3.gif">
		<img src="<%=request.getContextPath() %>/image/head/4.gif"/><input type="radio" name="head" value="4.gif">
		<img src="<%=request.getContextPath() %>/image/head/5.gif"/><input type="radio" name="head" value="5.gif">
		<BR/>
		<img src="<%=request.getContextPath() %>/image/head/6.gif"/><input type="radio" name="head" value="6.gif">
		<img src="<%=request.getContextPath() %>/image/head/7.gif"/><input type="radio" name="head" value="7.gif">
		<img src="<%=request.getContextPath() %>/image/head/8.gif"/><input type="radio" name="head" value="8.gif">
		<img src="<%=request.getContextPath() %>/image/head/9.gif"/><input type="radio" name="head" value="9.gif">
		<img src="<%=request.getContextPath() %>/image/head/10.gif"/><input type="radio" name="head" value="10.gif">
		<BR/>
		<img src="<%=request.getContextPath() %>/image/head/11.gif"/><input type="radio" name="head" value="11.gif">
		<img src="<%=request.getContextPath() %>/image/head/12.gif"/><input type="radio" name="head" value="12.gif">
		<img src="<%=request.getContextPath() %>/image/head/13.gif"/><input type="radio" name="head" value="13.gif">
		<img src="<%=request.getContextPath() %>/image/head/14.gif"/><input type="radio" name="head" value="14.gif">
		<img src="<%=request.getContextPath() %>/image/head/15.gif"/><input type="radio" name="head" value="15.gif">
		<br/>
			<INPUT class="btn" tabIndex="4" type="submit" value="注 册">
			<br>
			
			
		<%
			String msg=(String)request.getAttribute("msg");
			if(msg==null){
				msg="";
			}
			if(msg.equals("注册成功")){
				%>
				<script type="text/javascript" language="javascript">
					alert("<%=msg%>");
					window.location='pages/login.jsp';
				</script> 
				<%
			}
		%>
		<font style="color: red;">${msg}</font>
	</FORM>
</DIV>



 <%@ include file="bottom.jsp"%>