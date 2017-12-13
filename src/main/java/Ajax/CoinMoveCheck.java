package Ajax;
import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CoinMoveCheck extends HttpServlet {

  private Gson gson = new GsonBuilder().create();
  private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
  private static int x=0;
  
  private static  ArrayList<String> color=new ArrayList<String>();
  private static ArrayList<String> color1=new ArrayList<String>();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	  
	  ServletContext context  =   request.getSession().getServletContext();
	  ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
	  
	  String body = CharStreams.readLines(request.getReader()).toString();
	  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	  Map<String, String> data = gson.fromJson(json, typeReference.getType());
	  Map<String, String> messageData = new HashMap<>();

	  HashMap<String,HashMap<String,ArrayList<String>>> g= (HashMap<String,HashMap<String,ArrayList<String>>>)context.getAttribute("PlayDetails");
	  HashMap<String,String[]> gameid= ( HashMap<String,String[]>)context.getAttribute("GameIds");
	  
	  Cookie[] cookie=request.getCookies();
	  String usercookie=Cooky.getCookieValue("gc_account", request.getCookies());
	  String gi=data.get("gameid");
	  
	  String[] cookies=gameid.get(gi);
	  String oppcookie="";
	  if(cookies[0].equals(usercookie)) {
		  
		   oppcookie=cookies[1];
	  }
	  else {
		 oppcookie=cookies[0];
	  }
	  
	 System.out.println(usercookie);
	 System.out.println(g.get(usercookie).get("status").get(0));
	 System.out.println(g.get(oppcookie).get("status").get(0));
	 HashMap<String,String> values=(HashMap<String,String> )context.getAttribute("cookies");
	 if(g.get(usercookie).get("status").get(0).equals("Playing")||data.get("message").equals("")) {
		  
		         HashMap<String,ArrayList<String>> player=g.get(usercookie);

			      ArrayList<String> status=new ArrayList<String>();
			      status.add("Waiting");
			      player.put("status",status);

			    HashMap<String,ArrayList<String>> oppositeplayer=g.get(oppcookie);

			    ArrayList<String> status1=new ArrayList<String>();
			    status1.add("Playing");
			    oppositeplayer.put("status",status1);
	    
			     HashMap<String,String> result=new  HashMap<String,String>(); 
		     
     		     result.put("status","ok");
			     result.put("id",data.get("message"));
			     result.put("player1", values.get(usercookie));
			     result.put("player2", values.get(oppcookie));
			     result.put("player1status", g.get(usercookie).get("status").get(0) );
			     result.put("player2status", g.get(oppcookie).get("status").get(0) );
			     
			     result.put("color",g.get(usercookie).get("color").get(0));
			     System.out.println(result);
			     
			     Result result1 =
					        PusherService.getDefaultInstance()
					            .trigger(
					                data.get("channel_id"),
					                "colorchange", 
					                result
					                ); 
			  
		  
	  }
	  else { 
		  
		  messageData.put("status","Invalid move");
  
	     response.getWriter().println(gson.toJson(messageData));
	  }

  
  }
  

}
  
  