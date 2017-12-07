package HelperClasses;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Cooky {
		
	
	public static String getContextName(String cookie_name,Cookie[] cookies ,String context_name,HttpServletRequest req) {
			String cookie_value=getCookieValue(cookie_name, cookies);
			if(cookie_value == null) {
				return null;
			}
					try {
						HashMap<String,String> cookieMap = (HashMap<String, String>) req.getSession().getServletContext().getAttribute(context_name);
						return cookieMap.get(cookie_value);
					}catch(Exception e) {
						return null;
					}
	}
	
	public static HashMap<String,String[]> getContextValue(String context_name,HttpServletRequest req) {	
				try {
					return (HashMap<String,String[]>) req.getSession().getServletContext().getAttribute(context_name);
				}catch(Exception e) {
					return null;
				}
	}
	

	
	public static String getCookieValue(String cookie_name,Cookie[] cookies ) {
		try {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals(cookie_name)) {
					return cookie.getValue();
				}
			}
			throw new Exception();
		}catch(Exception e) {
			return null;
		}
		
	}

}