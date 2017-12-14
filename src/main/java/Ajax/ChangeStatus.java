package Ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.Cooky;

public class ChangeStatus extends HttpServlet {

	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 Gson gson = new GsonBuilder().create();
		 ServletContext context=request.getSession().getServletContext();
		 HashMap<String,HashMap<String,ArrayList<String>>> g= (HashMap<String,HashMap<String,ArrayList<String>>>)context.getAttribute("PlayDetails");
		  HashMap<String,String[]> gameid= ( HashMap<String,String[]>)context.getAttribute("GameIds");
		  
		  Cookie[] cookie=request.getCookies();
		  String usercookie=Cooky.getCookieValue("gc_account", request.getCookies());
		  String gi=request.getParameter("gameid");
		  
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
		 HashMap<String,String> result=new  HashMap<String,String>(); 
		 HashMap<String,String> values=(HashMap<String,String> )context.getAttribute("cookies");
		 if(g.get(usercookie).get("status").get(0).equals("Playing")) {
			  
			         HashMap<String,ArrayList<String>> player=g.get(usercookie);
	
				      ArrayList<String> status=new ArrayList<String>();
				      status.add("Waiting");
				      player.put("status",status);
	
				    HashMap<String,ArrayList<String>> oppositeplayer=g.get(oppcookie);
	
				    ArrayList<String> status1=new ArrayList<String>();
				    status1.add("Playing");
				    oppositeplayer.put("status",status1);
		    
				     
			     
	     		     result.put("status","ok");
				    
				     
				     Result result1 =
						        PusherService.getDefaultInstance()
						            .trigger(
						                request.getParameter("channel_id"),
						                "statuschange", 
						                result
						                ); 
				  
			  
		  }
		  else { 
			  
			  result.put("status","Invalid move");
	  
		     response.getWriter().println(gson.toJson(result));
		  }

	 }
}
