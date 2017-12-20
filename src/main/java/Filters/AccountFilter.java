package Filters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.Cooky;
import HelperClasses.Redirecter;


public class AccountFilter implements Filter{
	private Gson gson = new GsonBuilder().create();
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String value =Cooky.getCookieValue("gc_account",req.getCookies());
		String name = Cooky.getContextName("gc_account",req.getCookies(),"cookie", req);
		HashMap<String,String> hello = new HashMap<String,String>();
		System.out.println(req.getRequestURI());
			if( !req.getRequestURI().equals("/ajax/getReplays")&&!req.getRequestURI().equals("/ajax/getVideo")) {
				if((value == null || value=="null"||value == "" || name==null )) {
					System.out.println("FIlter rediecting" );
					hello.put("msg","signup to continue");
					resp.getWriter().write(gson.toJson(hello));
					resp.sendRedirect(Redirecter.giveUrlFor(req,"/home"));
				}else {
					chain.doFilter(request, response);
				}
			}else {
				chain.doFilter(request, response);
			}
			
		
			
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
