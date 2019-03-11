package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class rightFilter
 */
@WebFilter(urlPatterns= {"/pages/post.jsp","/pages/answer.jsp"})
public class rightFilter implements Filter {

    /**
     * Default constructor. 
     */
    public rightFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpSession session=req.getSession();
		if(session.getAttribute("user")==null) {
			
			//获取
			String callbackPath = req.getServletPath();
			Map<String, String[]> map = req.getParameterMap();

			//创建新的map保存参数
			Map<String, String[]> newmap= new HashMap<String, String[]>();
			newmap.putAll(map);
			
			//保存请求地址和参数
			session.setAttribute("callbackPath", callbackPath);
			session.setAttribute("callbackMap", newmap);

			
			HttpServletResponse res=(HttpServletResponse) response;
			res.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = res.getWriter();
			out.println("<script>alert('您尚未登录');location.href='login.jsp';</script>");
			out.flush();
		}else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
