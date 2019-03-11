<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"  %>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	window.onload=function(){
		CKEDITOR.replace('content');
	}
</script>
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;
	<B><a href="topic?flag=topicList&boardid=${board.boardid }">${board.boardname}</a>&gt;&gt;发表话题</B>
</DIV>

<div class="t" style="margin-top: 15px" align="center">
	<center>
		发表话题
		<hr width=80%>
		
		<form action="<%=request.getContextPath() %>/topic" method="post">
			<input type="hidden" name="flag" value="post" />
			<input type="hidden" name="uid" value="${user.uid}" />
			<input type="hidden" name="boardid" value="${board.boardid}" /> 
			<br />
			标题&nbsp;
			<input type="text" name="title" value="${param.title}" required="required">
			<br />
			内容&nbsp;
			<textarea name="content" row="5" cols="50"  value="${param.content}" required="required"></textarea>
			
			<br />
			
			<input class="btn" tabIndex="6"  type="submit" value="发表">
			<font>${msg}</font> 
		</form>
	</center>
</div>


<%@ include file="bottom.jsp"  %>