package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Board;
import bean.PageBean;
import bean.Reply;
import bean.Topic;
import bean.User;
import biz.BizException;
import biz.replyBizImpl;
import biz.topicBizImpl;
import utils.Myutil;


@WebServlet("/topic")
public class topicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	topicBizImpl tbi=new topicBizImpl();
	
	Topic topic =new Topic();
	
	replyBizImpl rbi=new replyBizImpl();
	
    public topicServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		switch (flag) {
		case "topicList":
			topicList(request,response);
			break;
		case "post":
			post(request,response);
			break;
		case "topicDetail":
			topicDetail(request,response);
			break;
		case "answer":
			answer(request,response);
			break;
		case "del":
			del(request,response);
			break;
		case "firstPage":
			firstPage(request, response);
			break;
		case "nextPage":
			nextPage(request, response);
			break;
		case "lastPage":
			lastPage(request, response);
			break;
		case "finalPage":
			finalPage(request, response);
			break;
		default:
			break;
		}
	}
	
	//首页
	public void firstPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer pages = Integer.parseInt(request.getParameter("pages")) ;
		Topic topic=new Topic();
		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		topic.setBoardid(boardid);
		
		topic.setPages(pages);
		PageBean<Topic> pagebean = tbi.findPageBean(topic);
		HttpSession session = request.getSession();
		pagebean.setPages(pages);
		session.setAttribute("pagebean",pagebean);
		request.getRequestDispatcher("pages/list.jsp").forward(request, response);
	}
	
	//下一页
	public void nextPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		PageBean<Topic> pageBean = (PageBean<Topic>) session.getAttribute("pagebean");

		Integer pages = Integer.parseInt(request.getParameter("pages")) ;

		if(pageBean.getTotalPage()<=pages) {
			pages=pageBean.getTotalPage().intValue();
		}

		Topic topic=new Topic();

		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		topic.setBoardid(boardid);
		
		topic.setPages(pages);
		PageBean<Topic> pagebean = tbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebean",pagebean);

		request.getRequestDispatcher("pages/list.jsp").forward(request, response);
	}
	
	//上一页
	public void lastPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer pages = Integer.parseInt(request.getParameter("pages")) ;
		if(pages>1) {
			pages--;
		}
		
		Topic topic=new Topic();

		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		topic.setBoardid(boardid);
		
		HttpSession session = request.getSession();
		topic.setPages(pages);
		PageBean<Topic> pagebean = tbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebean",pagebean);
		request.getRequestDispatcher("pages/list.jsp").forward(request, response);
	}
	
	//末页
	public void finalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer pages = Integer.parseInt(request.getParameter("pages")) ;	
		Topic topic=new Topic();
		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		topic.setBoardid(boardid);
		
		HttpSession session = request.getSession();
		topic.setPages(pages);
		PageBean<Topic> pagebean = tbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebean",pagebean);
		request.getRequestDispatcher("pages/list.jsp").forward(request, response);
	}

	//删除帖子
	private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int topicid = Integer.parseInt(request.getParameter("topicid")) ;
		topic.setTopicid(topicid);
		
		int delTopic = tbi.delTopic(topic);
		if(delTopic>0) {
			topicList(request,response);
		}
	}

	//回复帖子
	private void answer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int uid = Integer.parseInt(request.getParameter("uid")) ;
		int topicid = Integer.parseInt(request.getParameter("topicid")) ;
		
		Integer pages = Integer.parseInt(request.getParameter("pages"));
		
		String content = request.getParameter("content");
		topic.setUid(uid);
		topic.setTopicid(topicid);
		topic.setContent(content);
		
		int answer = rbi.answer(topic);
		
		if(answer>0) {
			response.sendRedirect("topic?flag=topicDetail&topicid="+topic.getTopicid()+"&pages="+pages);
		}else {
			request.setAttribute("msg", "服务器繁忙");
			request.getRequestDispatcher("pages/answer.jsp").forward(request, response);
		}
	}

	//每个帖子的详情
	private void topicDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer topicid = Integer.parseInt(request.getParameter("topicid"));
		topic.setTopicid(topicid);
		HttpSession session=request.getSession();
		
		//查询该帖子的详情
		Topic topicdetail = tbi.topicdetail(topic);
		
		if(request.getParameter("boardid")!=null) {
			Board board=new Board();
			Integer boardid = Integer.parseInt(request.getParameter("boardid"));
			board.setBoardid(boardid);
			
			
			@SuppressWarnings("unchecked")
			Map<Integer, List<Board>> map=(Map<Integer, List<Board>>) session.getAttribute("boardMap");
			for( Map.Entry<Integer, List<Board>> entry: map.entrySet()  ) {
				List<Board> listBoard=entry.getValue();
				for(  Board b:listBoard) {
					if( b.getBoardid()==board.getBoardid()) {
						board=b;
						break;
					}
				}
			}
			session.setAttribute("board", board);
		}
		session.setAttribute("topicdetail", topicdetail);

		Integer pages=0;
		if(request.getParameter("replyPages")==null || "".equals(request.getParameter("replyPages"))) {
			pages=1;
		}else {
			pages = Integer.parseInt(request.getParameter("replyPages")) ;
		}
		
		//查询回复该贴的列表
		topic.setPages(pages);
		PageBean<Reply> pagebean = rbi.findPageBean(topic);
		//所有回复该帖的列表	
		session.setAttribute("pagebeanReply", pagebean);


		
		request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
	}

	//发帖
	private void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, String[]> parameterMap = request.getParameterMap();
		topic = Myutil.MapToJavaBean(parameterMap, Topic.class);
		
		Integer uid = Integer.parseInt(request.getParameter("uid")) ;
		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		
		topic.setUid(uid);
		topic.setBoardid(boardid);

		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");

			topic.setUid(user.getUid() );
		}
						
		Integer post = null;
		try {
			post = tbi.post(topic);
		} catch (BizException e) {
			response.getWriter().print("<script  language='javascript'>'您已被禁言'</script>");
			response.sendRedirect("topic?flag=topicList&boardid="+topic.getBoardid());
			return;
		}
		
		if(post>0) {
			response.sendRedirect("topic?flag=topicList&boardid="+topic.getBoardid());
		}else {
			request.setAttribute("msg", "服务器繁忙");
			request.getRequestDispatcher("pages/post.jsp").forward(request, response);
		}
	}

	//帖子列表
	private void topicList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Board board=new Board();
		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		Integer pages =0;
		if(request.getParameter("pages")==null || "".equals(request.getParameter("pages"))) {
			pages=1;
		}else {
			pages = Integer.parseInt(request.getParameter("pages"));
			
		}
		
		
		board.setBoardid(boardid);
		
		HttpSession session=request.getSession();
		@SuppressWarnings("unchecked")
		Map<Integer, List<Board>> map=(Map<Integer, List<Board>>) session.getAttribute("boardMap");
		for( Map.Entry<Integer, List<Board>> entry: map.entrySet()  ) {
			List<Board> listBoard=entry.getValue();
			for(  Board b:listBoard) {
				if( b.getBoardid()==board.getBoardid()) {
					board=b;
					break;
				}
			}
		}
		session.setAttribute("board", board);
		
		topic.setBoardid(board.getBoardid());
		topic.setPages(pages);
		//每页查询到的帖子
		PageBean<Topic> pagebean = tbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebean", pagebean);
		
		request.getRequestDispatcher("/pages/list.jsp").forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
