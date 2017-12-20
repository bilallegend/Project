package com.example.appengine.pusher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.io.CharStreams;
import com.pusher.rest.Pusher;

import HelperClasses.Cooky;
import HelperClasses.Redirecter;
import com.pusher.rest.data.PresenceUser;

public class AuthorizeUser extends HttpServlet{
	@Override
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		    //intln("/ AuthorizeUser");
			String name=Cooky.getContextName("gc_account", request.getCookies(),"cookie",request) ;
			
		 	//intln(name+ "  user name");
		 	if(name == null) {	 		
		 		return;
		 	}else {
			    Pusher pusher = PusherService.getDefaultInstance();
			    String query = CharStreams.toString(request.getReader());
			    Map<String, String> data = splitQuery(query);
			    String socketId = data.get("socket_id");
			    String channelId = data.get("channel_name");
			    Map<String, String> userInfo = new HashMap<>();
			    String  CookieValue = Cooky.getCookieValue("gc_account",request.getCookies());
			    String 	currentUserId=null;
			    ServletContext context= request.getSession().getServletContext();
			    
			    HashMap<String,String[]> DivMap = (HashMap<String, String[]>) context.getAttribute("DivMap");
			    
			    if(!channelId.contains("private-MyNotification-")) {
				    if(context.getAttribute("DivMap")==null) {
				    	context.setAttribute("DivMap", new HashMap<String,String[]>());
				    }
				    
				    DivMap = (HashMap<String, String[]>) context.getAttribute("DivMap");
				    if(DivMap.size()>50) {
				    	return;
				    }
				    if(context.getAttribute("MultiTabs")==null) {
				    	context.setAttribute("MultiTabs", new HashMap<String,ArrayList<String>>());
				    }
				    HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
	
				    //setting 
				    	String[] ValuesArray =  new String[4];
				    		String size=null;
				    		if(DivMap.containsKey(CookieValue)) { // If the user on the same window more window 
								size=DivMap.get(CookieValue)[0];
							}else {
								size=(DivMap.keySet().size())+"";//If the user on the other browser
							}
				    		ValuesArray[0]=	size;
				    		currentUserId = size;
						    ValuesArray[1]=socketId;
						    ValuesArray[3]=name;
						    ArrayList<String> socketsList = new ArrayList<String>();
						    if(MultiTabs.containsKey(name)) {
						    	socketsList = MultiTabs.get(name);
						    }
						    if(!socketsList.contains(socketId)) {
						    	socketsList.add(socketId);
						    }
						    MultiTabs.put(name,socketsList);//setting MultiTabs {requestername:[socketId.....]}
						    DivMap.put(CookieValue,ValuesArray);
				    	//intln("MultiTabs "+MultiTabs);
				    
			    }
			    
			    
			    userInfo.put("displayName", Cooky.getContextName("gc_account", request.getCookies(),"cookie", request));
			    
			    // Inject custom authentication code for your application here to allow/deny current request
	
			    String auth=null;
			    if(("private-MyNotification-"+socketId).equals(channelId)) {
			    	auth =pusher.authenticate(socketId, channelId);
			    }else {
			    	auth =pusher.authenticate(socketId, channelId, new PresenceUser(currentUserId, userInfo));
			    }
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
