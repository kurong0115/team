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
import biz.boardBizImpl;


@WebServlet("/init")
public class initServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    boardBizImpl bbi=new boardBizImpl();   

    public initServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<Integer, List<Board>> allBoard = bbi.findAllBoard();
		HttpSession session = request.getSession();
		session.setAttribute("boardMap", allBoard);
		request.getRequestDispatcher("pages/show.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
