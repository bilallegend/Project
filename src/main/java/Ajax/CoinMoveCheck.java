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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CoinMoveCheck extends HttpServlet {

  private Gson gson = new GsonBuilder().create();
  private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	  
	  ServletContext context  =   request.getSession().getServletContext();
	  HashMap<String,ArrayList<String>> gamedetail= (HashMap<String,ArrayList<String>>) context.getAttribute("playdetails");
	  String body = CharStreams.readLines(request.getReader()).toString();
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    
	  ArrayList<> gamedetail.get(data.get("cookie"));
  }
}