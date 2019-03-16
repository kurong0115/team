<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--      主体        -->
<DIV>
<!--      导航        -->
<br/>
<DIV>
	&gt;&gt;<B><a href="<%=request.getContextPath() %>/index.jsp">论坛首页</a></B>
</DIV>
<br/>

	<DIV class="t">
		<TABLE cellSpacing="0" cellPadding="0" width="100%">		
			<TR>
				<TH class="h" style="WIDTH: 100%" colSpan="4"><SPAN>&nbsp;</SPAN></TH>
			</TR>
<!--       表 头           -->
			<TR class="tr2">
				<TD style="WIDTH: 5%" align="center">排行</TD>
				<TD style="WIDTH: 20%" align="center">作者</TD>
				<TD style="WIDTH: 20%" align="center">操作</TD>
				<TD style="WIDTH: 10%" align="center">发帖数</TD>
			</TR>
<!--         主 题 列 表        -->
			<%int count=0; %>
			<c:forEach items="${personTop}" var="tp">
				<TR class="tr3">
				<TD style="text-align: center;"><%=++count %></TD>
				<TD style="FONT-SIZE: 15px" align="center">
					<font>${tp.uname}</font>
				</TD>
				
				<TD style="FONT-SIZE: 12px" align="center">
					<a href="<%=request.getContextPath()%>/topic?flag=personTopTopic&uid=${tp.uid}" >查看所有帖子</a>
				</TD>
				
				<td align="center">
				<c:if test="${tp.total==null}">0</c:if>
				<c:if test="${tp.total!=null}">${tp.total}</c:if>
				</td>
			</TR>
			</c:forEach>
			

		</TABLE>
	</DIV>
<!--            翻 页          -->

</DIV>
<%@ include file="bottom.jsp"%>