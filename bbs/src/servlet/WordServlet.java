package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.WordbBizImpl;


@WebServlet("/noword")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private WordbBizImpl wb=new WordbBizImpl();
    
    public WordServlet() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag=request.getParameter("flag");
		switch (flag) {
		case "add":
			add(request, response);
			break;

		default:
			break;
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String word=request.getParameter("noword");
		int num=wb.add(word);
		if(num>0) {
			response.getWriter().write(1);
		}else{
			response.getWriter().write(0);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
