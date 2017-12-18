package Ajax;

import java.io.IOException;
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
		 c.remove(cookie);
		 context.setAttribute("cookie",c);
		 System.out.println(cookie);
		 for(Cookie cookie1 : request.getCookies()) {
			 cookie1.setMaxAge(0);
			 cookie1.setPath("/");
			 response.addCookie(cookie1);
		 }
		 response.sendRedirect("/home");		
	 }

}
