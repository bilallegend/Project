package com.example.appengine.pusher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
		 System.out.println("/ AuthorizeUser");
		 String name=null;
		// Instantiate a pusher connection
		
			 name = Cooky.getContextName("gc_account", request.getCookies(),"cookie",request) ;
		 	System.out.println(name+ "  user name");
		 	if(name == null) {
		 		
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
		    
		    Map<String, String> userInfo = new HashMap<>();
		    String  CookieValue = Cooky.getCookieValue("gc_account",request.getCookies());
		    String currentUserId=null;
		    ServletContext context= request.getSession().getServletContext();
		    
		    HashMap<String,String[]> DivMap = (HashMap<String, String[]>) context.getAttribute("DivMap");
		    
		    if(channelId != "presence-MyNotification-"+socketId) {
			    if(context.getAttribute("DivMap")==null) {
			    	context.setAttribute("DivMap", new HashMap<String,String[]>());
			    }
			    DivMap = (HashMap<String, String[]>) context.getAttribute("DivMap");
			    
			   
			    for(String[] arr : DivMap.values()) {
			    	if(arr[3].equals(name)) {
			    		currentUserId=arr[0];
			    		 System.out.println(Arrays.deepToString(arr));
			    		break;
			    	}
			    }
			    if(currentUserId==null) {
				    String[] ValuesArray =  new String[4];
				    System.out.println(DivMap.keySet().size());
				    String size=(DivMap.keySet().size())+"";
				    ValuesArray[0]=size;
				    ValuesArray[1]=socketId;
				    ValuesArray[3]=name;
				    System.out.println(Arrays.deepToString(ValuesArray));
				    if(!DivMap.containsKey(CookieValue) ) {
				    	System.out.println(!DivMap.containsKey(CookieValue)+"");
				    	DivMap.put(CookieValue,ValuesArray); 
				    	
				    }
				    currentUserId = DivMap.get(CookieValue)[0];
			    }
			    
		    }
		    
		    
		    System.out.println(DivMap.toString());
		    
		    
		    
		    
		    
		    // Presence channels (presence-*) require user identification for authentication
		    
		    userInfo.put("displayName", Cooky.getContextName("gc_account", request.getCookies(),"cookie", request));
		    
		    // Inject custom authentication code for your application here to allow/deny current request

		    String auth =pusher.authenticate(socketId, channelId, new PresenceUser(currentUserId, userInfo));
		    
//		    		pusher.authenticate(socketId, channelId);
		        
		    // if successful, returns authorization in the format
		    //    {
		    //      "auth":"49e26cb8e9dde3dfc009:a8cf1d3deefbb1bdc6a9d1547640d49d94b4b512320e2597c257a740edd1788f",
		    //      "channel_data":"{\"user_id\":\"23423435252\",\"user_info\":{\"displayName\":\"John Doe\"}}"
		    //    }

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