package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.collect;
import bean.User;
import biz.collectBizImpl;


@WebServlet("/collect")
public class collectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	collectBizImpl cbi=new collectBizImpl();
    public collectServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		
		switch (flag) {
		case "addCollect":
			addCollect(request,response);
			break;
		case "myCollect":
			myCollect(request,response);
			break;
		case "cancleCollect":
			cancleCollect(request,response);
			break;
		default:
			break;
		}
	}
	

	//取消收藏
	private void cancleCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		collect col=new collect();
		col.setCid(cid);
		
		int num=cbi.cancleCollect(col);
		if(num>0) {
			request.setAttribute("msg", "取消收藏成功");
			myCollect(request,response);
			
		}else {
			request.setAttribute("msg", "服务器繁忙，请稍后再试");
			request.getRequestDispatcher("/pages/myCollect.jsp").forward(request, response);
		}
	}

	//查看我的收藏
	private void myCollect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		collect col=new collect();
		col.setUid(uid);
		
		List<collect> list=cbi.findMyCollect(col);
		HttpSession session = request.getSession();
		session.setAttribute("myCollect", list);

		request.getRequestDispatcher("/pages/myCollect.jsp").forward(request, response);
	}

	//加入收藏1
	private void addCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int topicid = Integer.parseInt(request.getParameter("topicid"));
		Integer boardid = Integer.parseInt(request.getParameter("boardid"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		collect col=new collect();
		col.setTopicid(topicid);
		col.setBoardid(boardid);
		col.setUid(user.getUid());
		int n= cbi.addCollect(col);
		
		if(n>0) {
			request.setAttribute("msg", "收藏成功");
			request.getRequestDispatcher("/pages/detail.jsp").forward(request, response);
		}else {
			request.setAttribute("msg", "帖子已收藏，请查看我的收藏");
			request.getRequestDispatcher("/pages/detail.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
