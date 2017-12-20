package Ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.Cooky;

public class SignOut extends HttpServlet {
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		 String cookie=Cooky.getCookieValue("gc_account", request.getCookies());
		 ServletContext context=request.getSession().getServletContext();
		 HashMap<String,String> c= (HashMap<String,String>)context.getAttribute("cookie");
		 String name=c.get(cookie);
		 HashMap<String,String[]> divmap= (HashMap<String,String[]>)context.getAttribute("DivMap");
		 divmap.remove(cookie);
		 HashMap<String, ArrayList<String>> multitab=(HashMap<String, ArrayList<String>>)context.getAttribute("MultiTabs");
		 HashMap<String,String> NameIdMap = (HashMap<String, String>) context.getAttribute("NameIdMap");
		 NameIdMap.remove(name);
		 multitab.remove(name);
		 c.remove(cookie);
		 context.setAttribute("cookie",c);
		 for(Cookie cookie1 : request.getCookies()) {
			 cookie1.setMaxAge(0);
			 cookie1.setPath("/");
			 response.addCookie(cookie1);
		 }
		 response.sendRedirect("/home");		
	 }

}
