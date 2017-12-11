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
	  HashMap<String,HashMap<String,ArrayList<String>>> gamedetail= (HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("playdetails");
	  String body = CharStreams.readLines(request.getReader()).toString();
	  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	  Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    

	  HashMap<String,ArrayList<String>> g= gamedetail.get(data.get("gameid"));
	  ArrayList<String> cookie=g.get("cookie");
	  ArrayList<String> white=new ArrayList<String>();
	  ArrayList<String> black=new ArrayList<String>();
	  
	  for(String i:cookie) {
		  
	  }
	    

  
}
}
  
  
  