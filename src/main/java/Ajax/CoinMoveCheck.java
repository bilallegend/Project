package Ajax;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;
import java.io.IOException;
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
  
	

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	  
	  ServletContext context  =   request.getSession().getServletContext();
	//  HashMap<String,HashMap<String,ArrayList<String>>> gamedetail= (HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("playdetails");
	  String body = CharStreams.readLines(request.getReader()).toString();
	  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	  Map<String, String> data = gson.fromJson(json, typeReference.getType());
	  Map<String, String> messageData = new HashMap<>();

	  HashMap<String,HashMap<String,ArrayList<String>>> g= (HashMap<String,HashMap<String,ArrayList<String>>>)context.getAttribute("PlayDetails");
	  HashMap<String,String[]> gameid= ( HashMap<String,String[]>)context.getAttribute("GameIds");
	  
	  Cookie[] cookie=request.getCookies();
	  String usercookie="";
	  for(Cookie i:cookie) {
		  
		  if(i.getName().equals("gc_account")) {
			  usercookie=i.getValue();
		  }
	  }
	  
	  
	  String gi=data.get("gameid");
	  String[] cookies=gameid.get(gi);
	  String oppcookie="";
	  if(cookies[0].equals(usercookie)) {
		   oppcookie=cookies[1];
	  }
	  else {
		 oppcookie=cookies[0];
	  }
	  
	  
	  ArrayList<String> color=new ArrayList<String>();
	  ArrayList<String> color1=new ArrayList<String>();
	  
	  if(g.get(usercookie).get("status").get(0).equals("playing")) {
		  
		  color=g.get(usercookie).get("coins");
		  color1=g.get(oppcookie).get("coins");
		  response.getWriter().println("Ok");
	  }
	  else {
		  
		  messageData.put("status","Invalid move");
	  
	     response.getWriter().println(gson.toJson(messageData));
	  }

  
  }
}
  
  
  