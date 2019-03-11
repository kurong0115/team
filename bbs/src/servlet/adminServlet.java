package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.TblAdmin;
import biz.adminBizImpl;

@WebServlet("/admin")
@MultipartConfig
public class adminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    adminBizImpl abi=new adminBizImpl();
    public adminServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		
		switch (flag) {
		case "adminLogin":
			adminLogin(request,response);
			break;
		case "loginOut":
			loginOut(request,response);
			break;
		default:
			break;
		}
	}
	
	//管理员登录
	private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TblAdmin admin=new TblAdmin();

		String username=request.getParameter("loginName");
		String pwd=request.getParameter("loginPass");
		admin.setRapwd(pwd);
		admin.setRaname(username);
		HttpSession session=request.getSession();
		
		admin = abi.login(admin);
		if(admin!=null) {
			session.setAttribute("admin", admin);
			request.getRequestDispatcher("adminPages/admin.jsp").forward(request, response);
		}else {
			request.setAttribute("msg", "用户名或密码错误");
			request.getRequestDispatcher("adminPages/adminLogin.jsp").forward(request, response);
		}
	}
	
	//退出
	private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.setAttribute("admin", "");
		request.getRequestDispatcher("adminPages/adminLogin.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
