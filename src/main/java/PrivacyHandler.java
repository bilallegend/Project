import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import HelperClasses.Redirecter;

public class PrivacyHandler extends HttpServlet{
	private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};

	  @Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

	    String body = CharStreams.readLines(request.getReader()).toString();
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    String socketId = data.get("socket_id");
	    String channelId = data.get("channel_id");
	    String privacy = data.get("privacy");
	    String again= data.get("again");
	    String  CookieValue = Cooky.getCookieValue("gc_account",request.getCookies());
	    HashMap<String,String[]> DivMap = Cooky.getContextValue("DivMap", request);
	    Map<String, String> messageData = new HashMap<>();
	    ServletContext context= request.getSession().getServletContext();
	    
	    
	    if(context.getAttribute("NameIdMap")==null) { 
	    	context.setAttribute("NameIdMap", new HashMap<String,String>());
	    }
	    	HashMap<String,String> NameIdMap = (HashMap<String, String>) context.getAttribute("NameIdMap");
	    	HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
	   
	    if(DivMap == null || MultiTabs==null ) {
	    	messageData.put("redir",Redirecter.giveUrlFor(request,"/home"));
	    }else {
	    	String name= Cooky.getContextName("gc_account", request.getCookies(),"cookie", request);
		    if(DivMap.get(CookieValue)[2]== privacy ) {
		    	return;
		    }
		    //setting privacy to all the same person in DivMap
		    for(String key : DivMap.keySet()) { 
		    	if(DivMap.get(key)[3].equals(name)) {
		    		DivMap.get(key)[2]=privacy; 
		    	}
		    }
		    System.out.println(data);
		    
		    String showingId=null;
		    if(!NameIdMap.containsKey(name)) {
		    	NameIdMap.put(name,DivMap.get(CookieValue)[0]);	//setting {username:DivId}
		    }
		    
		    showingId=NameIdMap.get(name);
		    messageData.put("id",showingId);
		    	messageData.put("name",DivMap.get(CookieValue)[3]);
		    	messageData.put("html",getHtml(showingId,name,privacy));
		    	messageData.put("privacy",privacy);
		    
		    
		    
		    // Send a message over the Pusher channel (maximum size of a message is 10KB)
		    Result result =
		        PusherService.getDefaultInstance()
		            .trigger(
		                channelId,
		                "privacyHandle", // name of event
		                messageData,
		                socketId); // (Optional) Use client socket_id to exclude the sender from receiving the message
	
		    // result.getStatus() == SUCCESS indicates successful transmission
		    System.out.println(result.getStatus()+"\tprivacy member added sent");
		    messageData.remove("name");
		    messageData.put("status", result.getStatus().name());
		    if(again== null) {
			    String html="";
			    System.out.println(MultiTabs.get(name));
			    for(String key : DivMap.keySet()) {
			    	System.out.println(key);
			    	System.out.println(Arrays.toString(DivMap.get(key)));
			    	String memberName 	= DivMap.get(key)[3];
			    	String memberDivId 	= DivMap.get(key)[0];
			    	System.out.println(MultiTabs);
			    	try {
			    		if(!MultiTabs.get(name).contains(DivMap.get(key)[1]) && NameIdMap.get(memberName).equals(memberDivId) && !CookieValue.equals(key) && MultiTabs.containsKey(memberName)) {
				    		System.out.println(memberDivId+" "+memberName+" "+DivMap.get(key)[2]);
				    		html+=getHtml( memberDivId,memberName,DivMap.get(key)[2]);
				    		
				    	}
			    	}catch(Exception e) {
			    		
			    	}
			    	
			    }
			    messageData.put("html", html);
			   
		    }
		    
		    System.out.println(messageData);
	    }

	    response.getWriter().println(gson.toJson(messageData));
	  }
	  
	  
	  
	  private String getHtml(String id ,String name,String privacy) {
		  
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  String image=null;
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="select photo from player_info where username='"+name+"'";
				ResultSet data_table=stmt.executeQuery(Query);
				
				while(data_table.next()) {
					
					image=data_table.getString("photo");
				}
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  String img="";
		  if(image==null) {
			   img="style=\"background-image: url('../Images/pr.png');background-size:cover\">";
		  }
		  else {
			  img="style=\"background-image: url('"+image+"');background-size:cover\">";
		  }
		  
		  return "<div id='"+id+"' name='req'>"
			        +"<div class='img' id='img"+id+"' "+img+"</div>"
			        +"<div>"
				    +"<p id='"+id+"name'>"+name+"</p>"
				    +"<p>Privacy: <span id='pri'>"+privacy+"</span></p>"
				    +"</div>"
				    +"<button class='req' >Request</button>"
				    +"</div>";
				    
		   
	  }
}
