<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">
	$(function() {	
		$("#collectmsg").fadeIn(2000);
		$("#collectmsg").fadeOut(3000);		
	});

</script>
<!--      主体        -->
<DIV>
<!--      导航        -->
<br/>
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;我的收藏
</DIV>
	<div id="collectmsg" style="width: 200px;line-height: 30px;background-color: #fff;text-align: center;position: absolute;left: 45%;top:25%;border: 2px solid #E0F0F9; ">
				${msg }
	</div>
<br/>

	<DIV class="t">
		<TABLE cellSpacing="0" cellPadding="0" width="100%">		
			<TR>
				<TH class="h" style="WIDTH: 100%" colSpan="5"><SPAN>&nbsp;</SPAN></TH>
			</TR>
<!--       表 头           -->
			<TR class="tr2">
				<TD style="WIDTH: 3%">&nbsp;</TD>
				<TD style="WIDTH: 30%" align="center">文章</TD>
				<TD style="WIDTH: 20%" align="center">作者</TD>
				<TD style="WIDTH: 20%" align="center">收藏帖子时间</TD>
				<TD style="WIDTH: 25%" align="center">操作</TD>
			</TR>
<!--         主 题 列 表        -->
			
			<c:forEach items="${myCollect}" var="mc">
				<TR class="tr3">
				<TD >&nbsp;</TD>
				<TD style="FONT-SIZE: 15px" align="center">
					<a href="<%=request.getContextPath()%>/topic?flag=topicDetail&topicid=${mc.topicid}&pages=1&replyPages=1">${mc.title}</a>
				</TD>
				<TD style="text-align: center;">${mc.sendname}</TD>
				<TD style="text-align: center;">${mc.collectime}</TD>
				<TD style="FONT-SIZE: 12px" align="center">
					<a href="<%=request.getContextPath()%>/collect?flag=cancleCollect&cid=${mc.cid}&uid=${mc.uid}" >取消收藏</a>
				</TD>
				
			</TR>
			</c:forEach>
			

		</TABLE>
	</DIV>
<!--            翻 页          -->

</DIV>
<%@ include file="bottom.jsp"%>