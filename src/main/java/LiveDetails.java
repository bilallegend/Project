import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.Cooky;
import HelperClasses.Redirecter;

/**
 * Servlet implementation class LiveDetails
 */
public class LiveDetails extends HttpServlet {

	private Gson gson = new GsonBuilder().create();
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
	      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	String body = CharStreams.readLines(request.getReader()).toString(); 
		    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		    Map<String, String> data = gson.fromJson(json, typeReference.getType());
		    String gameid = data.get("game_id");
		    System.out.println(gameid);
		    ServletContext context = request.getSession().getServletContext();
		    HashMap<String,String> feedsDivId = (HashMap<String, String>) context.getAttribute("feedsDivId");
		    HashMap<String,String[]> GameIdCookies = (HashMap<String,String[]>) context.getAttribute("GameIds");
		    Map<String, Object> messageData = new HashMap<String, Object>();
		    System.out.println(GameIdCookies.containsKey(gameid)); System.out.println(feedsDivId.containsValue(gameid));
		    try {
		    	if(GameIdCookies.containsKey(gameid) && feedsDivId.containsValue(gameid)) {
			    	String[] cookies = GameIdCookies.get(gameid);
			    	HashMap<String,HashMap<String,ArrayList<String>>>  play = (HashMap<String, HashMap<String, ArrayList<String>>>) context.getAttribute("PlayDetails"); 
			    	HashMap<String,String[]> DivMap = Cooky.getContextValue("DivMap", request); 
			    	System.out.println(play);
			    	System.out.println(DivMap);
			    	System.out.println(Arrays.toString(cookies));
			    	for(String cookie : cookies) {
			    		if(!cookie.contains("p")) {
				    		System.out.println(cookie);
				    		String color = play.get(cookie).get("color").get(0);
				    		System.out.println(color);
				    		String name = DivMap.get(cookie)[3];
				    		messageData.put(color, name);
				    		messageData.put(color+"coins",play.get(cookie).get("color"));
				    		messageData.put(name,play.get(cookie).get("status").get(0));
			    		}
			    	}
			    	//Exception if play details does not contains cookie	
			    }else {
			    	throw new Exception();
			    }
		    }catch(Exception e){
		    	e.printStackTrace();
		    	messageData.put("alert","No proper user");
		    	messageData.put("redir", Redirecter.giveUrlFor(request, "/home"));
		    }
	    	response.getWriter().println(gson.toJson(messageData));
		    
	}
	
	

}
