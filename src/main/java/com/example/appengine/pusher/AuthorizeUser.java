package com.example.appengine.pusher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.common.io.CharStreams;
import com.pusher.rest.Pusher;

import HelperClasses.Cooky;

public class AuthorizeUser extends HttpServlet{
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 Pusher pusher = PusherService.getDefaultInstance();
		 Cooky cookieFinder = new Cooky(request.getCookies());
		 String value=cookieFinder.getData("gc_account");
		 ServletContext context=request.getSession().getServletContext();
		 if(context.getAttribute(value)!= null) {
			 String query = CharStreams.toString(request.getReader());
			 Map<String, String> data = splitQuery(query);
			    System.out.println("data Map "+data);
			    String socketId = data.get("socket_id");
			    String channelId = data.get("channel_name");
			    String auth =
			    		pusher.authenticate(socketId, channelId);
			    response.getWriter().append(auth);
		 }
	 }
	
	 private static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		    Map<String, String> query_pairs = new HashMap<>();
		    String[] pairs = query.split("&");
		    for (String pair : pairs) {
		      int idx = pair.indexOf("=");
		      query_pairs.put(
		          URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
		          URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		    }
		    return query_pairs;
		  }
	
}
