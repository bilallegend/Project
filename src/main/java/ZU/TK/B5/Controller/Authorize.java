package ZU.TK.B5.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.common.io.CharStreams;
import com.pusher.rest.Pusher;
import com.pusher.rest.data.PresenceUser;

import HelperClasses.Cooky;
import HelperClasses.PostParamGiver;

public class Authorize extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			String query = CharStreams.toString(req.getReader());
	    	System.out.println(" query "+query);
	    	Map<String,String> data = PostParamGiver.splitQuery(query);
			Pusher pusher = PusherService.getDefaultInstance();
			HashMap<String,String> userInfo = new HashMap<String,String>();
			userInfo.put("Name",Cooky.getContextName("gc_account",req.getCookies(),"cookie", req));
			String auth =pusher.authenticate(data.get("socket_id"), data.get("channel_id"), 
					new PresenceUser(Cooky.getCookieValue("gc_account",req.getCookies()),userInfo));
			
	}
	
}
