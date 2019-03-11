<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--      主体        -->
<DIV><br/>
	<!--      导航        -->
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>&gt;&gt;
	<B><a href="topic?pages=${param.pages }&flag=topicList&boardid=${board.boardid }">${board.boardname}</a>&gt;&gt;帖子详情</B>
</DIV>
	<br/>
	<!--      回复、新帖        -->
	<DIV>
		<A href="<%=request.getContextPath() %>/pages/answer.jsp?pages=${param.pages }&topicid=<%=request.getParameter("topicid")%>"><IMG src="<%=request.getContextPath() %>/image/reply.gif"  border="0" id=td_post></A> 
		<A href="<%=request.getContextPath() %>/pages/post.jsp"><IMG src="<%=request.getContextPath() %>/image/post.gif"   border="0" id=td_post></A>
		<A href="<%=request.getContextPath() %>/topic?flag=topicHostList&boardid=${board.boardid }">返回板块热帖</A>
	
	</DIV>

	<!--      本页主题的标题        -->
	<DIV>
		<TABLE cellSpacing="0" cellPadding="0" width="100%">
			<TR>
				<TH class="h">本页主题: ${topicdetail.title}</TH>
			</TR>
			<TR class="tr2">
				<TD>&nbsp;</TD>
			</TR>
		</TABLE>
	</DIV>
	
	<!--      主题        -->
	
	<DIV class="t">
		<TABLE style="BORDER-TOP-WIDTH: 0px; TABLE-LAYOUT: fixed" cellSpacing="0" cellPadding="0" width="100%">
			<TR class="tr1">
				<TH style="WIDTH: 20%">
					<B>发帖人：${topicdetail.uname}</B><BR/>
					<image src="image/head/${topicdetail.head}"/><BR/>
					注册:${topicdetail.regtime}<BR/>
				</TH>
				<TH>
					<H4>帖名：${topicdetail.title}</H4>
					<DIV>内容：${topicdetail.content}</DIV>
					<DIV class="tipad gray">
						发表：[${topicdetail.publishtime}] &nbsp;
						最后修改:[${topicdetail.modifytime}]
					</DIV>
				</TH>
			</TR>
		</TABLE>
	</DIV>
	
	<!--      回复        -->
	
	<c:forEach items="${pagebeanReply.list}" var="reply">
		<DIV class="t">
		<TABLE style="BORDER-TOP-WIDTH: 0px; TABLE-LAYOUT: fixed" cellSpacing="0" cellPadding="0" width="100%">
			<TR class="tr1">
				<TH style="WIDTH: 20%">
					<B>回帖人：${reply.uname}</B><BR/><BR/>
					<image src="image/head/${reply.head}"/><BR/>
					注册:${reply.regtime}<BR/>
				</TH>
				<TH>
					<DIV>回帖内容：${reply.content}</DIV>
					<DIV class="tipad gray">
						发表：[${reply.publishtime}] &nbsp;
						最后修改:[${reply.modifytime}]
						
						<c:if test="${user.uid==reply.uid}">
							<a onclick="confirm('确定删除?')?location.href='reply?flag=del&replyid=${reply.replyid }&topicid=${reply.topicid}':''" href="javascript:void(0)" >删除</a>
						</c:if>
					</DIV>
				</TH>
			</TR>
		</TABLE>
	</DIV>
	</c:forEach>
	
	<!--         翻 页         -->
	<ul style="text-align: left;list-style-type: none;font-size: 13px;">
		<li>共<span id="num">${pagebeanReply.total}</span>条回复&nbsp;&nbsp; <span>${pagebeanReply.pages}</span>/<span>${pagebeanReply.totalPage }</span>页
		<a href="<%=request.getContextPath() %>/reply?pages=${param.pages }&flag=firstPage&topicid=${param.topicid}&replyPages=1">首页</a>
		<a href="<%=request.getContextPath() %>/reply?pages=${param.pages }&flag=lastPage&topicid=${param.topicid}&replyPages=${pagebeanReply.pages}">上一页</a>
		<a href="<%=request.getContextPath() %>/reply?pages=${param.pages }&flag=nextPage&topicid=${param.topicid}&replyPages=${pagebeanReply.pages+1}">下一页</a>
		<a href="<%=request.getContextPath() %>/reply?pages=${param.pages }&flag=finalPage&topicid=${param.topicid}&replyPages=${pagebeanReply.totalPage }">尾页</a>&nbsp;&nbsp;
		</li>
	</ul>
</DIV>





<%@ include file="bottom.jsp"%>