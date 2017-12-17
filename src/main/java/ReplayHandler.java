import java.io.IOException;
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

public class ReplayHandler extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("hi ");
		String body = CharStreams.readLines(req.getReader()).toString();
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    String divId = data.get("divId");
	    ServletContext context = req.getSession().getServletContext();
	    System.out.println(divId);
	    HashMap<String,String> sendData = new HashMap<String,String>();
	    if(divId.contains("Live")) {
	    	HashMap<String,String> feedsDivId = (HashMap<String, String>) context.getAttribute("feedsDivId");
	    	System.out.println("FeedsDivId");
	    	System.out.println(feedsDivId);
	    	String gameId = feedsDivId.get(divId);
	    	System.out.println(gameId);
	    	
	    	sendData.put("redir", "/watch?LiveId="+(gameId));
//	    	req.setAttribute("LiveId",divId);  
	    	
	    }else if(divId.contains("Replay")){
	    	sendData.put("redir", "/replay?RId="+divId.split("Replay")[0]);
	    }
	    resp.getWriter().println(gson.toJson(sendData));
	    
	   
	}
	
	
}
