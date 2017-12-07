import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.Cooky;
import HelperClasses.Redirecter;

public class GameRequestHandler extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};

	  @Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // Parse POST request body received in the format:
	    // [{"message": "my-message", "socket_id": "1232.24", "channel": "presence-my-channel"}]
		 
	    String body = CharStreams.readLines(request.getReader()).toString();
	    System.out.println(body);
	    
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    System.out.println(json);
	    
	    String socketId = data.get("socket_id");
	    String channelId =null;
	    String parentID = data.get("parentID");
	    String  CookieValue = Cooky.getCookieValue("gc_account",request.getCookies());
	    
	    
	    
	    System.out.println(data);
	    String name= Cooky.getContextName("gc_account", request.getCookies(),"cookie", request);
	    HashMap<String,String[]> DivMap = Cooky.getContextValue("DivMap", request);
	    Map<String, String> messageData = new HashMap<>();
	    
	    
	    System.out.println(DivMap);
	    
	    if(DivMap == null) {
//	    	mySession.setAttribute("DivMap", new HashMap<String,String>());  
	    	messageData.put("redir",Redirecter.giveUrlFor(request,"/home"));
	    	return;
	    }else {
	    	
	    	
	    	messageData.put("req_id",DivMap.get(CookieValue)[0]);
	    	messageData.put("msg",name+" wants to play with you?");
	    }
	    	
	    for(String[] strArr: DivMap.values()) {
    		if(strArr[0] == parentID) {
    			channelId="presence-MyNotification-"+strArr[1];
    		}
    	}
	    
	    
	    // Send a message over the Pusher channel (maximum size of a message is 10KB)
	    Result result =
	        PusherService.getDefaultInstance()
	            .trigger(
	                channelId,
	                "GameReq", // name of event
	                messageData,
	                socketId); // (Optional) Use client socket_id to exclude the sender from receiving the message

	    // result.getStatus() == SUCCESS indicates successful transmission
	    messageData.put("status", result.getStatus().name());

	    response.getWriter().println(gson.toJson(messageData));
	  }
}
