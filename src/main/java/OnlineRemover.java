import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class OnlineRemover extends HttpServlet{
	 private Gson gson = new GsonBuilder().create();
	 private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = CharStreams.readLines(request.getReader()).toString();
		  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		  Map<String, String> data = gson.fromJson(json, typeReference.getType());
		  Map<String, String> messageData = new HashMap<>();
		  String Id = data.get("id");
		  ServletContext context= request.getSession().getServletContext();
		  HashMap<String,String[]> DivMap = (HashMap<String, String[]>) context.getAttribute("DivMap");
		  String name="";
		  System.out.println("entering");
		  for(String key : DivMap.keySet()) {
			  System.out.println(DivMap.get(key)[0]+"  "+Id);
			  if(DivMap.get(key)[0].equals(Id)){
				  name=DivMap.get(key)[3];
				  break;
			  }
		  } 
		  System.out.println(name);
		  if(name!=null||name.equals("")) {
			  HashMap<String,String> NameIdMap = (HashMap<String, String>) context.getAttribute("NameIdMap");
			  HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
			  MultiTabs.remove(name);
			  NameIdMap.remove(name);

			  messageData.put("status","successfully");
		  }
		  response.getWriter().println(gson.toJson(messageData));
	}
}
