package filters;

import java.io.IOException;
import java.io.PrintWriter;

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


@WebFilter("/admin.jsp")
public class adminFilter implements Filter {

   
    public adminFilter() {
        
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpSession session=req.getSession();
		if(session.getAttribute("admin")==null) {
		
			HttpServletResponse res=(HttpServletResponse) response;
			res.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = res.getWriter();
			out.println("<script>alert('ÄúÉÐÎ´µÇÂ¼');location.href='adminPages/adminLogin.jsp';</script>");
			out.flush();
		}
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
