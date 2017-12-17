import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class CoinSender extends HttpServlet{
	private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String,Object>> typeReference =
	      new TypeReference<Map<String,Object>>() {};
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = CharStreams.readLines(req.getReader()).toString();
	    System.out.println(body);
	    
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String,Object> data = gson.fromJson(json, typeReference.getType());
	    HashMap<String,Object> messageData= new HashMap<>();
	    System.out.println(data.get("Black").getClass().getName());
	    System.out.println(json);
	    ArrayList<Integer> Black = (ArrayList<Integer>) data.get("Black");
	    ArrayList<Integer> White = (ArrayList<Integer>) data.get("White");
	    String B_player = (String) data.get("B_player");
	    String W_player = (String) data.get("W_player");
	    String W_status = (String) data.get("W_status");
	    String B_status = (String) data.get("b_status");
	    String gameId = (String) data.get("gameId");
	    
	    messageData.put("Black",Black);
	    messageData.put("White", White);messageData.put("B_player", B_player);
	    messageData.put("W_player", W_player);
	    messageData.put("W_status", W_status);messageData.put("B_player", B_player);
	    messageData.put("W_player", W_player);
	    System.out.println(messageData);
	    System.out.println("presence-live-"+gameId);
	    Result result =
		        PusherService.getDefaultInstance()
		            .trigger(
		                gameId,
		                "addNew", // name of event
		                messageData
		                );
	    messageData.put("status", result.getStatus().name());
	    resp.getWriter().println(gson.toJson(messageData));
	   
	}
}
