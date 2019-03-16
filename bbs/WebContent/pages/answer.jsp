<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="header.jsp"  %>
<%@ include file="../adminPages/links.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	window.onload=function(){
		CKEDITOR.replace('content');
	}
</script>
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;
	<B><a href="topic?flag=topicList&boardid=${board.boardid }&pages=${param.pages }">${board.boardname}</a>
	&gt;&gt;回复话题</B>
</DIV>
<c:if test="${replyMsg!=null }">
	<script>$.messager.alert('提示',"${replyMsg}");</script>
</c:if>
<div class="t" style="margin-top: 15px" align="center">
	<center>
		回复帖子
		<hr width=80%>
		
		<form action="<%=request.getContextPath() %>/topic" method="post">
			<input type="hidden" name="flag" value="answer" />
			<input type="hidden" name="uid" value="${user.uid}" />
			<input type="hidden" name="pages" value="${param.pages}" />
			<input type="hidden" name="topicid" value="<%=request.getParameter("topicid")%>" />
			<br />
			内容&nbsp;
			<textarea name="content" row="5" cols="50" >${param.content }</textarea>
			
			<br />
			
			<input class="btn" tabIndex="6"  type="submit" value="回复"> 
			<font>${msg}</font>
		</form>
	</center>
</div>


<%@ include file="bottom.jsp"  %>