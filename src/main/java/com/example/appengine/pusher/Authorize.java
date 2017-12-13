package com.example.appengine.pusher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.io.CharStreams;
import com.pusher.rest.Pusher;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;
import HelperClasses.Redirecter;
import com.pusher.rest.data.PresenceUser;

public class Authorize extends HttpServlet{
	@Override
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		 String name="";
		 // Instantiate a pusher connection
		
			 name = Cooky.getContextName("gc_account", request.getCookies(),"cookie",request) ;
		 	System.out.println(name+ "  user name");
		 	if(name.equals("")) {	 		
		 		return;
		 	}else {
		    Pusher pusher = PusherService.getDefaultInstance();

		    String query = CharStreams.toString(request.getReader());
		    System.out.println(" query "+query);
		    // socket_id, channel_name parameters are automatically set in the POST body of the request
		    // eg.socket_id=1232.12&channel_name=presence-my-channel
		    Map<String, String> data = splitQuery(query);
		    System.out.println("data Map "+data);
		    String socketId = data.get("socket_id");
		    String channelId = data.get("channel_name");	
		    
		    ConnectionDatabase psql = new ConnectionDatabase();
			 Connection conn			=psql.createConnection("gamecenter");
			 int count=0;
			 try{
				    Statement stmt = conn.createStatement();
					String Query="select id from game ";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						count+=1;
					}
					
			    } catch (SQLException e) {
			       // System.out.println(e);
			    	System.out.println("Anu");
			    }
			 Cookie[] c=request.getCookies();
				String num="";
				for(Cookie cookie:c) {
					if(cookie.getName().equals("gc_account")) {
						num=cookie.getValue();
					}
				}
			 ServletContext context  =   request.getSession().getServletContext();
			 HashMap<String,String[]> gamedetail= (HashMap<String,String[]>) context.getAttribute("GameIds");
			 Map<String, String> userInfo = new HashMap<>();
			 String currentUserId=num;
			 userInfo.put("displayName", Cooky.getContextName("gc_account", request.getCookies(),"cookie", request));
			 
				  if((gamedetail.get("game_"+(count+1)))!=null||!((gamedetail.get("game_"+count)[0].equals(num)||(gamedetail.get("game "+count)[1].equals(num)))) ){
					  
					  String auth=null;
					    if(("private-MyNotification-"+socketId).equals(channelId)) {
					    	auth =pusher.authenticate(socketId, channelId);
					    }else {
					    	auth =pusher.authenticate(socketId, channelId,new PresenceUser(currentUserId, userInfo));
					    }
			     	    response.getWriter().append(auth);
					  
				  }
				  else {
					  return;
				  }
		    
		    
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
