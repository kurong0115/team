<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">

	function name() {
		$.ajax({
		url:"<%=request.getContextPath() %>/reply?flag=replyAgree&replyid=${param.topicid }",
		type:"POST",
		dataType:"JSON",
			success:function(data ){
				if(data.code==1){
					$("#glktimes").html("点赞数:"+data.obj);	 			
				}
			}
		
		});
	}
	
	$(function(){
		 $.ajax({
			url:"<%=request.getContextPath() %>/reply?flag=glktimes&topicid=${param.topicid }",
			type:"POST",
			dataType:"JSON",
		 	success:function(data ){
		 		if(data.code==1){
		 			$("#replygreed").html("点赞数:"+data.obj);
		 			
		 		}
		 	}
			
		 });
	});
 	
	function glk(  topicid ){
	  	//js   jsp
		$.ajax({
			url:"<%=request.getContextPath() %>/reply?flag=glk&topicid="+topicid,
			type:"POST",
		 	dataType:"JSON",
		 	success:function(data ){
		 		if(data.code==1){
		 			$("#glktimes").html("点赞数:"+data.obj);
		 			
		 		}
		 	}
		});
	}   

	$(function() {	
			$("#collectmsg").fadeIn(2000);
			$("#collectmsg").fadeOut(3000);		
	});
	
	
	$(function() {
		$("#agree").click(function() {
			$("#disagree").show();
			$("#agree").hide();
		});
		$("#disagree").click(function() {
			$("#agree").show();
			$("#disagree").hide();
		});
		
	})
</script>


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
		<A href="<%=request.getContextPath() %>/pages/post.jsp"><IMG src="<%=request.getContextPath() %>/image/post.gif"   border="0" id=td_post></A>&nbsp;&nbsp;&nbsp;
		<A href="<%=request.getContextPath() %>/topic?flag=topicHostList&boardid=${board.boardid }">返回板块热帖</A>&nbsp;&nbsp;&nbsp;&nbsp;
	
		
		
		<c:if test="${user!=null}">
			<a id="agree" href='javascript:glk( ${param.topicid }  )'>点赞</a> 
		</c:if>
		<c:if test="${user!=null}">
			<a id="disagree" href='javascript:glk( ${param.topicid }  )' style="display: none;">取消点赞</a> 
		</c:if>
		<div id="collectmsg" style="width: 200px;line-height: 30px;background-color: #fff;text-align: center;position: absolute;left: 45%;top:28%;border: 2px solid #E0F0F9; ">
				${msg }
		</div>
		
	</DIV>

	<!--      本页主题的标题        -->
	<DIV>
		<TABLE cellSpacing="0" cellPadding="0" width="100%">
			<TR>
				<TH class="h">本页主题: ${topicdetail.title}
				
				&nbsp;&nbsp;&nbsp;&nbsp;
				<font id="glktimes">
					点赞数:
				</font>
				
				&nbsp;&nbsp;&nbsp;&nbsp;
				
				<c:if test="${user.uid!=null}">
					<a id="collect" href="<%=request.getContextPath()%>/collect?flag=addCollect&topicid=${topicdetail.topicid}&boardid=${board.boardid }" >加入收藏</a>
				</c:if>
				
				
				</TH>
				
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
						
						
						<br/>
						<c:if test="${user!=null}">
							<a href="javascript:void(0)" onclick="">点赞</a>
						</c:if>
						<font id="replygreed">点赞数：${reply.agreecount}</font>
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