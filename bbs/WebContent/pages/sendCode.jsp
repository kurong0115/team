<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ include file="header.jsp"%>
<script type="text/javascript">
	alert("验证码已发送！请进入邮箱查看!");
	window.location='<%=request.getContextPath() %>/pages/resetpwd.jsp';
</script>
<%
	//随机生成验证码
	String code = String.valueOf(((Math.random() * 9 + 1) * 100));

	// 以下变量为用户根据自己的情况设置
	String smtphost = "smtp.qq.com"; // 发送邮件服务器
	String user = "869872053@qq.com"; // 邮件服务器登录用户名
	String password = "nfesakwxpeeobehb"; // 邮件服务器登录密码
	String from = "869872053@qq.com"; // 发送人邮件地址
	String to = "591005846@qq.com"; // 接受人邮件地址
	String subject = "BBS验证码"; // 邮件标题
	//邮件内容
	String body = "亲爱的用户，这是来自BBS的密码重置验证码：" + code + "请注意保护好个人信息！";
	// 以下为发送程序，用户无需改动
	try {
		//初始化Properties类对象
		Properties props = new Properties();
		//设置mail.smtp.host属性
		props.put("mail.smtp.host", smtphost);
		//设置使用验证
		props.put("mail.smtp.auth", "true");
		// 获取非共享的session对象
		Session ssn = Session.getInstance(props, null);
		//创建一个默认的MimeMessage对象。
		MimeMessage message = new MimeMessage(ssn);
		//创建InternetAddress对象
		InternetAddress fromAddress = new InternetAddress(from);
		//设置From: 头部的header字段
		message.setFrom(fromAddress);
		//创建InternetAddress对象
		InternetAddress toAddress = new InternetAddress(to);
		//设置 To: 头部的header字段
		message.addRecipient(Message.RecipientType.TO, toAddress);
		//设置 Subject: header字段
		message.setSubject(subject);
		// 现在设置的实际消息
		message.setText(body);
		//定义发送协议
		Transport transport = ssn.getTransport("smtp");
		//建立与服务器的链接
		transport.connect(smtphost, user, password);

		//发送邮件
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		//transport.send(message);
		//关闭邮件传输
		transport.close();
%>
<%
	} catch (Exception m) //捕获异常
	{
		out.println(m.toString());
		m.printStackTrace();
	}
%>
<%@ include file="bottom.jsp"%>
