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
import HelperClasses.Redirecter;

public class MemberRemoveChecker extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = CharStreams.readLines(req.getReader()).toString();
		String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		Map<String, String> data = gson.fromJson(json, typeReference.getType());
		String name = data.get("id");
		String gameid= data.get("game_id");
		String cookievalue = Cooky.getCookieValue("gc_account",req.getCookies());
		String oppcookie="";
		Map<String, String> messageData = new HashMap<>();
		ServletContext context = req.getSession().getServletContext(); 
		HashMap<String,String[]>  gameIds =  (HashMap<String, String[]>) context.getAttribute("GameIds");
		HashMap<String,String[]>  DivMap =  (HashMap<String, String[]>) context.getAttribute("DivMap");
		HashMap<String,Object> playdetails = (HashMap<String, Object>) context.getAttribute("PlayDetails");
		if(playdetails.containsKey(cookievalue)) {
			for(String key :gameIds.keySet()) {
				String[] values=gameIds.get(key);
				if(values[0].equals(cookievalue)) {
					oppcookie=values[1];
					break;
				}else if(values[1].equals(cookievalue)) {
					oppcookie=values[0];
					break;
				}
			}
			if(playdetails.containsKey(oppcookie) && DivMap.get(oppcookie)[3].equals(name)) {
				messageData.put("msg","Sorry ! "+name+" exists the game");
				
			}
		}else {
			String playerCookie ="";
			for(String key : DivMap.keySet()) {
				if(DivMap.get(key)[3].equals(name)) {
					playerCookie=key;
					break;
				}
			}
			if(playdetails.containsKey(playerCookie)) {
				messageData.put("count","1");
			}
			
		}
		resp.getWriter().println(gson.toJson(messageData));
	}
}
