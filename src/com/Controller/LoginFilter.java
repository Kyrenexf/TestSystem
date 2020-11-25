package com.Controller;

import com.Entity.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{
	public LoginFilter() {
		super();
	}
	@Override
    public void init(FilterConfig filterConfig)  {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String path = req.getServletPath();
    	String requestURI =  req.getRequestURI();
    	if("/validCode".equals(path) ||"/userServlet".equals(path) ||"/register.jsp".equals(path) ||"/login.jsp".equals(path) ||requestURI.indexOf("/css/") != -1||requestURI.indexOf("/js/") != -1||requestURI.indexOf("/images/") != -1){
	    	chain.doFilter(request, response);
    	} else {
	    	if(session.getAttribute("User")==null){
		    	PrintWriter out=response.getWriter();
		    	out.print("<script>alert('请先登录！');location.href='login.jsp'</script>");
	    	} else{
		    	// pass the request along the filter chain
		    	chain.doFilter(request, response);
	    	}
    	}
    }
    @Override
    public void destroy() {

    }
}
