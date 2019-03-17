<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../adminPages/links.jsp"%>
<!--      主体        -->
<DIV>
<!--      导航        -->
<br/>
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;
	<B><a href="topic?flag=topicList&boardid=${board.boardid }&pages=1">${board.boardname}</a></B>
</DIV>
<c:if test="${postMsg!=null }">
	<script>$.messager.alert('提示',"${postMsg}");</script>
</c:if>
<c:if test="${msg!=null }">
	<script>$.messager.alert('提示',"${msg}");</script>
</c:if>
<br/>
<!--      新帖        -->
	<DIV>
		<A href="<%=request.getContextPath() %>/pages/post.jsp"><IMG src="<%=request.getContextPath() %>/image/post.gif" name="td_post" border="0" id=td_post></A>
	</DIV>
<!--         翻 页         -->
	<ul style="text-align: left;list-style-type: none;font-size: 13px;">
		<li>共<span id="num">${pagebean.total}</span>条记录&nbsp;&nbsp; <span>${pagebean.pages}</span>/<span>${pagebean.totalPage }</span>页
		<a href="<%=request.getContextPath() %>/topic?pages=1&flag=firstPage&boardid=${param.boardid}">首页</a>
		<a href="<%=request.getContextPath() %>/topic?pages=${pagebean.pages}&flag=lastPage&boardid=${param.boardid}">上一页</a>
		<a href="<%=request.getContextPath() %>/topic?pages=${pagebean.pages+1}&flag=nextPage&boardid=${param.boardid}">下一页</a>
		<a href="<%=request.getContextPath() %>/topic?pages=${pagebean.totalPage }&flag=finalPage&boardid=${param.boardid}">尾页</a>&nbsp;&nbsp;
		</li>
	</ul>
	

	<DIV class="t">
		<TABLE cellSpacing="0" cellPadding="0" width="100%">		
			<TR>
				<TH class="h" style="WIDTH: 100%" colSpan="6"><SPAN>&nbsp;</SPAN></TH>
			</TR>
<!--       表 头           -->
			<TR class="tr2">
				<TD>&nbsp;</TD>
				<TD style="WIDTH: 50%" align="center">文章</TD>
				<TD style="WIDTH: 20%" align="center">发布时间</TD>
				<TD style="WIDTH: 10%" align="center">操作</TD>
				<TD style="WIDTH: 10%" align="center">作者</TD>
				<TD style="WIDTH: 10%" align="center">回复</TD>
			</TR>
<!--         主 题 列 表        -->
			<c:forEach items="${pagebean.list}" var="tp">
				<TR class="tr3">
				<TD><IMG src="<%=request.getContextPath() %>/image/topic.gif" border=0></TD>
				<TD style="FONT-SIZE: 15px" >
					<A href="<%=request.getContextPath()%>/topic?flag=topicDetail&topicid=${tp.topicid}&pages=${pagebean.pages}&replyPages=1">${tp.title}</A>
				</TD>
				<td align="center">${tp.publishtime}</td>
				<c:if test="${tp.uname==user.uname }">
					<TD style="FONT-SIZE: 12px" align="center">
						<a href="<%=request.getContextPath()%>/topic?flag=topicDetail&topicid=${tp.topicid}&pages=${pagebean.pages}&replyPages=1" >详情</a>
						
						<a onclick="confirm('确定删除?')?location.href='topic?flag=listDel&topicid=${tp.topicid}&boardid=${param.boardid}':''" href="javascript:void(0)" >删除</a>
						
					</TD>
				</c:if>
				<c:if test="${tp.uname!=user.uname }">
					<TD style="FONT-SIZE: 12px" align="center">
						<a href="<%=request.getContextPath()%>/topic?flag=topicDetail&topicid=${tp.topicid}&replyPages=1" >详情</a>
					</TD>
				</c:if>
				<TD align="center">${tp.uname}</TD>
				<TD align="center">${tp.replycount}</TD>
			</TR>
			</c:forEach>
			

		</TABLE>
	</DIV>
<!--            翻 页          -->

</DIV>
<%@ include file="bottom.jsp"%>