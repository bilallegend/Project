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

public class RequestConfirmHandler extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};

	  @Override
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String body = CharStreams.readLines(request.getReader()).toString();
	    System.out.println(body);
	    String name = Cooky.getContextName("gc_account",request.getCookies(),"cookie", request);
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    System.out.println(json);
	    
	    String Reply  = data.get("reply");
	    HashMap<String,String[]> reqInfo = Cooky.getContextValue("reqInfo",request);
	    Map<String,String> messageData = new HashMap<String, String>();
	    
		if(reqInfo == null) {
			messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
		}else {
			reqInfo = Cooky.getContextValue("reqInfo",request);
			String requesterName = "";
			boolean tryF = true;
			for(String Key : reqInfo.keySet()) {
				if(reqInfo.get(Key)[1].equals(name)) {
					messageData.put("replyId",reqInfo.get(Key)[0]);
					requesterName=Key;
					tryF=false;
					break;
				}
				
			}
			reqInfo.remove(requesterName);			
			if(Reply.equals("YES") && !tryF) {
				messageData.put("reply", Reply);
				messageData.put("redir", Redirecter.giveUrlFor(request,"/game"));
			}else {
				messageData.put("reply", "Sorry!");
			}
			Result result =
			        PusherService.getDefaultInstance()
			            .trigger(
			                "presence-MyNotification-"+messageData.get("replyId"),
			                "GameResp", // name of event
			                messageData);
			messageData.put("status", result.getStatus().name());
			System.out.println("presence-MyNotification-"+messageData.get("replyId"));
			messageData.remove("reply");messageData.remove("replyId");
		}
		
		
		response.getWriter().println(gson.toJson(messageData));
	    
	  }
}
