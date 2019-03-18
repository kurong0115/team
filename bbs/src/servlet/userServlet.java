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

import com.alibaba.fastjson.JSON;

import bean.User;
import bean.UserInfo;
import biz.bbsUserBizImpl;
import dao.UserDao;
import utils.Myutil;


@WebServlet("/bbsUser")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao ud=new UserDao();
	bbsUserBizImpl ubi=new bbsUserBizImpl();
    public userServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		
		switch (flag) {
		case "userLogin":
			userLogin(request,response);
			break;
		case "userReg":
			userReg(request,response);
			break;
		case "isUserName":
			isUserName(request,response);
			break;
		case "logout":
			logout(request,response);
			break;
		case "logAgain":
			logAgain(request,response);
			break;
		case "findUser":
			findUser(request,response);
			break;
		case "findUserInfo":
			findUserInfo(request,response);
			break;
		case "pwdchange":
			pwdchange(request,response);
			break;
		case "sendcode":
			sendcode(request,response);
			break;
		case "resetpwd":
			resetpwd(request,response);
			break;
		default:
			break;
		}
	}
	/**
	 * 重置密码的方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void resetpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取验证码
		HttpSession session = request.getSession();
		String code1 = (String)session.getAttribute("code");//随机生成的验证码
		String code2 = request.getParameter("mycode");//用户输入的验证码
		if( code1.equals(code2) ) {//两次输入的验证码相同，可以进行修改密码
			String uname = request.getParameter("uname");
			String upass = request.getParameter("upass");
			int result = ubi.resetpwd(upass, uname);
			if( result > 0  ) {//密码重置成功
				String msg = "密码重置成功";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			}else {
				String msg = "服务器繁忙，密码重置失败。";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/resetpwd.jsp").forward(request, response);
			}
		}
		
		
		
	}

	/**
	 * 获取验证码的方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void sendcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//使用工具类发送验证码
		String email = request.getParameter("email");
		System.out.println("邮箱地址："+ email);
		//发送邮件，返回验证码，并带回到前端界面
		String code = Myutil.sendemail(email);
		request.getSession().setAttribute("code", code);
		response.getWriter().append(code);
	}


	//修改密码
	private void pwdchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		User user = new User();
		
		HttpSession session = request.getSession();
		
		user = (User) session.getAttribute("user");
		
		System.out.println(user.getUpass());
		
		Integer uid = user.getUid();
		String upass = request.getParameter("upass");
		if(upass.equals(user.getUpass())){
			String newpass = request.getParameter("newpass");
			//获取结果
			Integer result = ubi.pwdchange(uid,newpass);
			//跳转页面
			if( result > 0  ) {//修改成功
				String msg = "修改成功,请重新登录";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
			}else {//修改失败
				String msg = "由于服务器原因，密码修改失败";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
			}
		}else {
			String msg = "原密码错误，请重新输入";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
		}
		
		
	}
	//查询所有用户扩展信息
	private void findUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> userInfo= ubi.findUserInfo();
		String jsonString=JSON.toJSONString(userInfo);
		response.getWriter().append(jsonString);
	}


	//查询所有用户
	private void findUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> findUSer = ubi.findUSer();
		
		String jsonString = JSON.toJSONString(findUSer);
		
		response.getWriter().append(jsonString);
	}


	//退出
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("user", null);
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}
	//修改密码后退出
	private void logAgain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("user", null);
		request.getRequestDispatcher("pages/login.jsp").forward(request, response);	
	}

	//判断用户名是否存在
	private void isUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = request.getParameter("uName");
		User user=new User();
		user.setUname(uname);
		
		if(uname==null||"".equals(uname)){   //用户名为空
			response.getWriter().write("2");
			return ;
		}
		int isname = ubi.isUserName(user);
		if(isname==1) {  //用户名已存在
			response.getWriter().write("1");
		}else {           //用户名不存在
			response.getWriter().write("0");
		}
	}


	//用户注册
	private void userReg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uName");
		String upass = request.getParameter("uPass");
		Integer gender = Integer.parseInt(request.getParameter("gender")) ;
		String head = request.getParameter("head");
		String email=request.getParameter("email");
		String regcode = request.getParameter("regcode");
		String code = (String)request.getSession().getAttribute("code");
		
		User user=new User();
		user.setGender(gender);
		user.setHead(head);
		user.setUname(uname);
		user.setUpass(upass);
		user.setEmail(email);
		if( regcode.equals(code) ) {
			int num = ubi.regUser(user);
			List<Map<String,Object>> list=ubi.getBasicInfo(user.getUname(), user.getUpass());
			Integer uid=(Integer) list.get(0).get("uid");
			System.out.println(uid);
			user.setUid(uid);
			ubi.addExpendInfo(user);
			if(num==1) {
				request.setAttribute("msg", "注册成功");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "注册失败");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
			}
		}else {
				request.setAttribute("msg", "验证码错误");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
		}
	}


	//用户登录
	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uName");
		String upass = request.getParameter("uPass");
		String code = request.getParameter("code");
		
		//先判断验证码
		HttpSession session = request.getSession();
		String valCode = (String) session.getAttribute("code");
		if(code.equals(valCode)) {
			User user=new User();
			user.setUname(uname);
			user.setUpass(upass);
			List<User> userLogin = ubi.userLogin(user);
			
			if(userLogin.size()>0) {
				user=userLogin.get(0);
				session.setAttribute("user", user);
				System.out.println(user);
				UserInfo userinfo=ud.selectAll(user.getUid());
				session.setAttribute("userinfo", userinfo);
				session.setMaxInactiveInterval(3600);
				//ubi.addExpendInfo(user);
				//判断是否有回调路径
				if(request.getSession().getAttribute("callbackPath")!=null) {
					String path = (String) request.getSession().getAttribute("callbackPath");
					
					@SuppressWarnings("unchecked")
					Map<String, String[]> newmap = (Map<String, String[]>) request.getSession().getAttribute("callbackMap");
					//拼接地址
					path+="?";
					for (Map.Entry<String, String[]> entry : newmap.entrySet()) {
						String name=entry.getKey();
						String value=entry.getValue()[0];
						path+=name+"="+value+"&";
						
					}
					//重定向回调页面
					String cxtPath=request.getContextPath();
					response.sendRedirect(cxtPath+path);
				}else {
					response.sendRedirect("index.jsp");
				}
				
			}else {
				request.setAttribute("msg", "用户名或密码错误");
				request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			}
		}else {
			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
