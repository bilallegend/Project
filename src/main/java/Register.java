import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.CookieCreator;
import HelperClasses.Cooky;

public class Register extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookievalue= Cooky.getCookieValue("gc_guest",req.getCookies());
		String gcvalue= Cooky.getCookieValue("gc_account",req.getCookies());
		if(gcvalue==null && cookievalue == null) {
			CookieCreator cc= new CookieCreator();
//			cc.createContext("gc_guest", id, req, resp);
		}
	}
	
}
