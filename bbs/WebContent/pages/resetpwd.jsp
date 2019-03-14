<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ include file="header.jsp"%>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	$("#sendcode").click(function(){
		if( $("#uname").val() != null ){				
			$.ajax({
				url:"/bbs/bbsUser?flag=sendcode",
				method:"post",
				dataType:"text",
				data:{"uname":$("#uname").val()},
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
				alert("请输入您的用户名！！");
			}
	})
})

function checkUserInfo() {
		if (document.getElementById("samePwd").innerHTML == "密码不一致") {
			alert("密码不一致");
			return false;
		}
	}
	
	function isSamePwd() {
		if(document.pwdForm.upass.value!=document.pwdForm.upass1.value){
			document.getElementById("samePwd").innerHTML="密码不一致";
		}
		if(document.pwdForm.upass.value==document.pwdForm.upass1.value){
			document.getElementById("samePwd").innerHTML="";
		}
	}
</script>
<div id="pwdchange" text-align="center";>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<FORM onSubmit="return checkUserInfo()" name="pwdForm"
					action="<%=request.getContextPath()%>/bbsUser" method="post">
					<input type="hidden" name="flag" value="resetpwd"> 
					<span>&nbsp;用&nbsp;户&nbsp;名：</span>
					<input type="text" id="uname"></br> 
					<span>&nbsp;新&nbsp;密&nbsp;码：</span>
					<input type="password" id="upass"></br> 
					<span>重复密码：</span>
					<input type="password" id="upass1" onblur="isSamePwd()"></br>
					<span>&nbsp;验&nbsp;证&nbsp;码：</span><input type="text" id="code">
					<button id="sendcode">获取验证码</button></br>
					<font style="color: red;" id="samePwd"></font>
					<INPUT class="btn" tabIndex="4" type="submit" value="修改">
				</FORM>
			</div>
		</div>
	</div>
</div>
<%@ include file="bottom.jsp"%>
