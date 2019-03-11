package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.PageBean;
import bean.Reply;
import bean.Topic;
import biz.replyBizImpl;


@WebServlet("/reply")
public class replyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Reply reply=new Reply();
	
	replyBizImpl rbi=new replyBizImpl();
	
    public replyServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		switch (flag) {
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
		Integer topicid = Integer.parseInt(request.getParameter("topicid")) ;
		Topic topic=new Topic();

		
		topic.setTopicid(topicid);
		PageBean<Reply> pagebean = rbi.findPageBean(topic);
		HttpSession session = request.getSession();
		pagebean.setPages(1);
		session.setAttribute("pagebeanReply",pagebean);
		request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
	}
	
	//下一页
	public void nextPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		PageBean<Topic> pagebeanReply = (PageBean<Topic>) session.getAttribute("pagebeanReply");

		Integer pages = Integer.parseInt(request.getParameter("replyPages")) ;

		if(pagebeanReply.getTotalPage()<=pages) {
			pages=pagebeanReply.getTotalPage().intValue();
		}

		Integer topicid = Integer.parseInt(request.getParameter("topicid")) ;
		Topic topic=new Topic();

		topic.setTopicid(topicid);

		topic.setPages(pages);
		PageBean<Reply> pagebean = rbi.findPageBean(topic);
		pagebean.setPages(pages);

		session.setAttribute("pagebeanReply",pagebean);

		request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
	}
	
	//上一页
	public void lastPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer pages = Integer.parseInt(request.getParameter("replyPages")) ;
		if(pages>1) {
			pages--;
		}
		
		Integer topicid = Integer.parseInt(request.getParameter("topicid")) ;
		Topic topic=new Topic();

		topic.setTopicid(topicid);
		
		HttpSession session = request.getSession();
		topic.setPages(pages);
		PageBean<Reply> pagebean = rbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebeanReply",pagebean);
		request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
	}
	
	//末页
	public void finalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer pages = Integer.parseInt(request.getParameter("replyPages")) ;	
		Integer topicid = Integer.parseInt(request.getParameter("topicid")) ;
		Topic topic=new Topic();

		topic.setTopicid(topicid);
		
		HttpSession session = request.getSession();
		topic.setPages(pages);
		PageBean<Reply> pagebean = rbi.findPageBean(topic);
		pagebean.setPages(pages);
		session.setAttribute("pagebeanReply",pagebean);
		request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
	}

	//删除回复
	private void del(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer replyid = Integer.parseInt(request.getParameter("replyid"));
		Integer topicid = Integer.parseInt(request.getParameter("topicid"));
		reply.setReplyid(replyid);
		int delReply = rbi.delReply(reply);
		if(delReply>0) {
			response.sendRedirect("topic?flag=topicDetail&topicid="+topicid);
		}else {
			request.setAttribute("msg", "服务器繁忙");
			request.getRequestDispatcher("pages/detail.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
