import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import HelperClasses.Cooky;

public class OnlineMembers extends HttpServlet{
	
	private static String AppKey ="c551d3cca2fce9983539";
	private static String cluster ="ap2";
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		if(req.getPathInfo().equals("/getRoom")) {
			Gson gson = new Gson(); 
			System.out.println("/getRoom\tOnlineMembers");
			System.out.println(req.getSession().getServletContext().getAttribute("room"));
			HashMap<String,String> room=(HashMap<String, String>) req.getSession().getServletContext().getAttribute("room");
	
			if(room==null) {
				room = new HashMap<String,String>();
				room.put("AppKey",AppKey);
				room.put("cluster", cluster);
				room.put("channelname","private-online-"+Math.round((Math.random()*10000000)));
				req.getSession().getServletContext().setAttribute("room", room);;
				System.out.println(room);
				resp.getWriter().write(gson.toJson(room));
				
			}else {
				resp.getWriter().write(gson.toJson(room));
			}
			return;
		}
//	}
}
