package HelperClasses;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class Redirecter {
	
	public static String giveUrlFor(HttpServletRequest request,String RedirectPath) {
		URI thisUri =null;
		try {
			thisUri = new URI(request.getRequestURL().toString());
			System.out.println(thisUri.getScheme()+"://"+thisUri.getHost()+":"+thisUri.getPort()+RedirectPath);
			return thisUri.getScheme()+"://"+thisUri.getHost()+":"+thisUri.getPort()+RedirectPath;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return null;
		} 
		
	}
}
