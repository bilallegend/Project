import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.common.io.CharStreams;
import com.pusher.rest.Pusher;
import com.pusher.rest.data.PresenceUser;

import HelperClasses.Cooky;

public class LiveWatchAuth extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			 ServletContext context = req.getSession().getServletContext();
			 
			 
			 String query = CharStreams.toString(req.getReader());
			    System.out.println(" query "+query);
			    Map<String, String> userInfo = new HashMap<>();
			    Map<String, String> data = splitQuery(query);
			    System.out.println("data Map "+data);
			    String socketId = data.get("socket_id");
			    String channelId = data.get("channel_name");		    
			    String CookieValue =Cooky.getCookieValue("gc_account",req.getCookies());
			    Pusher pusher = PusherService.getDefaultInstance();
			    String auth = pusher.authenticate(socketId, channelId, new PresenceUser(
			    		Cooky.getContextName("gc_account",req.getCookies(),"cookie", req),userInfo.put("name","name"))
			    		);
			    System.out.println(auth);
			    resp.getWriter().append(auth);
			}catch (Exception e) {
				return;
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
