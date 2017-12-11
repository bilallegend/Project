import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public class Feeds extends HttpServlet{
	private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = CharStreams.readLines(request.getReader()).toString();
	    System.out.println(body);
	    
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    System.out.println(json);
	    String socketId = data.get("socket_id");
	    String channelId = data.get("channel_id");
	    
	    ServletContext context = request.getSession().getServletContext();
		
	    if(context.getAttribute("playdetails") == null) {
	    	
	    }else {
	    	HashMap<String,String[]> GameIds = (HashMap<String,String[]>) context.getAttribute("GameIds");
	    	Set<String> GameId = GameIds.keySet();
	    	HashMap<String,HashMap< String,ArrayList<String>>>  PlayDetails = (HashMap<String, HashMap<String, ArrayList<String>>>) context.getAttribute("PlayDetails");
	    	if(context.getAttribute("feedsDivId")==null) {
	    		context.setAttribute("feedsDivId",new HashMap<String,String>());
	    	}
	    	HashMap<String,String> feedsDivId = (HashMap<String, String>) context.getAttribute("feedsDivId");
	    	String html="";String[] cookies=null;
	    	Map<String, String> messageData = new HashMap<>();
	    	for(String gameId : GameId) {
	    		if(!feedsDivId.values().contains(gameId)) {
		    		String randomNum = getRandomNumber(feedsDivId);
		    		feedsDivId.put(randomNum,gameId);
		    		cookies=GameIds.get(gameId);
		    		html+=getHtml(PlayDetails.get(cookies[0]).get("name").get(0), PlayDetails.get(cookies[1]).get("name").get(0), randomNum);
	    		}
	    	}
	    	messageData.put("html",html);
	    	if(channelId != null && channelId.equals("presence-live")) {
	    		Result result =
	    		        PusherService.getDefaultInstance()
	    		            .trigger(
	    		            		"presence-live",
	    		                "PlayLive", // name of event
	    		                messageData);
	    		messageData.remove("html");
	    		messageData.put("status", result.getStatus().name());
	    		
	    	}
	    	response.getWriter().println(gson.toJson(messageData));
	    	
	    	
	    }
	}
	
	
	private String getRandomNumber(HashMap<String,String> feedsDivId) {
		String random           =   ""+(int)(Math.random()*500000+100000);
    	while(feedsDivId.containsKey(random)) {
    		random           =   ""+(int)(Math.random()*500000+100000);
    	}
		return random;
	}
	
	private String getHtml(String player1 ,String player2,String gameRandomId) {
		return "<div id="+gameRandomId+">\n" + 
				"    <div class=\"top-div\">\n" + 
				"        <div class=\"profile\"></div>\n" + 
				"        <p>"+player1 +" vs " + player2+"</p>\n" + 
				"    </div>\n" + 
				"    <div class=\"feedvideo\"></div>\n" + 
				"    <div class=\"likediv\">\n" + 
				"        <i class=\"fa fa-heart like\" aria-hidden=\"true\"></i>\n" + 
				"        <span id='likes'>0</span>\n" + 
				"        <span class=\"sec-spn\">Likes</span>\n" + 
				"    </div>\n" + 
				"    <div class=\"viewdiv\">\n" + 
				"         <span id='views'>0</span>\n" + 
				"         <i class=\"fa fa-eye\" aria-hidden=\"true\"></i>\n" + 
				"    </div>\n" + 
				"</div>";
		
	}
	
}
