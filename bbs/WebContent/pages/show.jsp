<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ include file="header.jsp"%>

	<!--      主体        -->
	<DIV class="t" >
		<TABLE cellSpacing="0" cellPadding="0" width="100%">
			<TR class="tr2" align="center">
				<TD colSpan="2">论坛</TD>
				
				<TD style="WIDTH: 10%;">操作</TD>
				<TD style="WIDTH: 10%;">主题</TD>
				<TD style="WIDTH: 30%">最后发表</TD>
			</TR>
			<!--       主版块       -->

			<%--  循环整个Map<Integer, List<Board>> --%>
			<c:forEach items="${boardMap}" var="allBoards">

				<c:if test="${allBoards.key==0}">
				
					<%--  循环整4个Map中的List<Board> 取出大板块名--%>
					<c:forEach items="${allBoards.value}" var="boards">
						<TR class="tr3">
							<TD colspan="5">${boards.boardname}</TD>
						</TR>
						
						<%--  重新循环整个Map<Integer, List<Board>> 判断找出key值与大板块的boardid相同的list集合--%>
						<c:forEach items="${boardMap}" var="allBoards2">
							<c:if test="${allBoards2.key==boards.boardid}">
								
								<%--  循环找到对应key值的的List<Board>> --%>
								<c:forEach items="${allBoards2.value}" var="sonBoard">
								
									<c:if test="${ sonBoard!=null }">
									<!--       子版块       -->
									<TR class="tr3">
										<TD width="5%">&nbsp;</TD>
										<TH align="left"><IMG
											src="<%=request.getContextPath()%>/image/board.gif"> <A
											href="topic?pages=1&flag=topicList&boardid=${sonBoard.boardid }">${sonBoard.boardname}</A>
										</TH>
										<td align="center"> 
											<a href="topic?flag=topicHostList&boardid=${sonBoard.boardid }">查看板块热帖</a>
										</td>

										<TD align="center">${sonBoard.topicsum}</TD>
										<TH><SPAN> <A href="<%=request.getContextPath()%>/topic?flag=topicDetail&topicid=${sonBoard.recenttopicid }&boardid=${sonBoard.boardid}">${sonBoard.recenttopictitle}
											</A>
										</SPAN> <BR /> <SPAN>${sonBoard.recenttopicusername}</SPAN> 
										
										<c:if test="${sonBoard.recenttopicmodifytime!=null }">
											<SPAN class="gray">[ ${sonBoard.recenttopicmodifytime} ]</SPAN>
										</c:if>
										<c:if test="${sonBoard.recenttopicmodifytime==null }">
											<SPAN class="gray">暂无新帖发出</SPAN>
										</c:if>
										
										
										</TH>
									</TR>
									</c:if>

									
								</c:forEach>
	
							</c:if>

						</c:forEach>


					</c:forEach>
				</c:if>

			</c:forEach>

		</TABLE>
	</DIV>

 <%@ include file="bottom.jsp"%>