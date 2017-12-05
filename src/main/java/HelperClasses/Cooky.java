package HelperClasses;

import javax.servlet.http.Cookie;

public class Cooky {
		
	private Cookie[] cookies;
	
	public Cooky(Cookie[] _cookies) {
		this.cookies=_cookies;
	}
	
	public String getData(String name) {
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}

