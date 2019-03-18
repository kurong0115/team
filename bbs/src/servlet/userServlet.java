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
	 * ��������ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void resetpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ��֤��
		HttpSession session = request.getSession();
		String code1 = (String)session.getAttribute("code");//������ɵ���֤��
		String code2 = request.getParameter("mycode");//�û��������֤��
		if( code1.equals(code2) ) {//�����������֤����ͬ�����Խ����޸�����
			String uname = request.getParameter("uname");
			String upass = request.getParameter("upass");
			int result = ubi.resetpwd(upass, uname);
			if( result > 0  ) {//�������óɹ�
				String msg = "�������óɹ�";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			}else {
				String msg = "��������æ����������ʧ�ܡ�";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/resetpwd.jsp").forward(request, response);
			}
		}
		
		
		
	}

	/**
	 * ��ȡ��֤��ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void sendcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//ʹ�ù����෢����֤��
		String email = request.getParameter("email");
		System.out.println("�����ַ��"+ email);
		//�����ʼ���������֤�룬�����ص�ǰ�˽���
		String code = Myutil.sendemail(email);
		request.getSession().setAttribute("code", code);
		response.getWriter().append(code);
	}


	//�޸�����
	private void pwdchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ����
		User user = new User();
		
		HttpSession session = request.getSession();
		
		user = (User) session.getAttribute("user");
		
		System.out.println(user.getUpass());
		
		Integer uid = user.getUid();
		String upass = request.getParameter("upass");
		if(upass.equals(user.getUpass())){
			String newpass = request.getParameter("newpass");
			//��ȡ���
			Integer result = ubi.pwdchange(uid,newpass);
			//��תҳ��
			if( result > 0  ) {//�޸ĳɹ�
				String msg = "�޸ĳɹ�,�����µ�¼";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
			}else {//�޸�ʧ��
				String msg = "���ڷ�����ԭ�������޸�ʧ��";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
			}
		}else {
			String msg = "ԭ�����������������";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("pages/personal.jsp").forward(request, response);
		}
		
		
	}
	//��ѯ�����û���չ��Ϣ
	private void findUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> userInfo= ubi.findUserInfo();
		String jsonString=JSON.toJSONString(userInfo);
		response.getWriter().append(jsonString);
	}


	//��ѯ�����û�
	private void findUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> findUSer = ubi.findUSer();
		
		String jsonString = JSON.toJSONString(findUSer);
		
		response.getWriter().append(jsonString);
	}


	//�˳�
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("user", null);
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}
	//�޸�������˳�
	private void logAgain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("user", null);
		request.getRequestDispatcher("pages/login.jsp").forward(request, response);	
	}

	//�ж��û����Ƿ����
	private void isUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = request.getParameter("uName");
		User user=new User();
		user.setUname(uname);
		
		if(uname==null||"".equals(uname)){   //�û���Ϊ��
			response.getWriter().write("2");
			return ;
		}
		int isname = ubi.isUserName(user);
		if(isname==1) {  //�û����Ѵ���
			response.getWriter().write("1");
		}else {           //�û���������
			response.getWriter().write("0");
		}
	}


	//�û�ע��
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
				request.setAttribute("msg", "ע��ɹ�");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "ע��ʧ��");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
			}
		}else {
				request.setAttribute("msg", "��֤�����");
				request.getRequestDispatcher("/pages/reg.jsp").forward(request, response);
		}
	}


	//�û���¼
	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uName");
		String upass = request.getParameter("uPass");
		String code = request.getParameter("code");
		
		//���ж���֤��
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
				//�ж��Ƿ��лص�·��
				if(request.getSession().getAttribute("callbackPath")!=null) {
					String path = (String) request.getSession().getAttribute("callbackPath");
					
					@SuppressWarnings("unchecked")
					Map<String, String[]> newmap = (Map<String, String[]>) request.getSession().getAttribute("callbackMap");
					//ƴ�ӵ�ַ
					path+="?";
					for (Map.Entry<String, String[]> entry : newmap.entrySet()) {
						String name=entry.getKey();
						String value=entry.getValue()[0];
						path+=name+"="+value+"&";
						
					}
					//�ض���ص�ҳ��
					String cxtPath=request.getContextPath();
					response.sendRedirect(cxtPath+path);
				}else {
					response.sendRedirect("index.jsp");
				}
				
			}else {
				request.setAttribute("msg", "�û������������");
				request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			}
		}else {
			request.setAttribute("msg", "��֤�����");
			request.getRequestDispatcher("pages/login.jsp").forward(request, response);
			
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
