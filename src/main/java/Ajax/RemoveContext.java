package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class RemoveContext extends HttpServlet {
	
	private Gson gson = new GsonBuilder().create();
	  private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};

	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  
		 ServletContext context=request.getSession().getServletContext(); 
		 
		 String body = CharStreams.readLines(request.getReader()).toString();
		  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		  Map<String, String> data = gson.fromJson(json, typeReference.getType());
		  
		 HashMap<String,String[]> gameids= (HashMap<String,String[]>) context.getAttribute("GameIds");
		 HashMap<String,HashMap<String,ArrayList<String>>> playdetails= (HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		 
		 String usercookie=Cooky.getCookieValue("gc_account",request.getCookies());
		 String oppcookie=null;
		 String gameid=null;
		 
		 if(playdetails.get(usercookie).get("status").get(0).equals("Playing")) {
		 
		 for(String i:gameids.keySet()) {
			 
			 if(gameids.get(i)[0].equals(usercookie)) {
				 gameid=i;
				 oppcookie=gameids.get(i)[0];
			 }
			 if(gameids.get(i)[1].equals(usercookie)) {
				 gameid=i;
				 oppcookie=gameids.get(i)[0];
			 }
			
		 }
		 String winner=null;
		 if(data.get("white").equals("0")) {
			  winner="Black";
		 }
		 else {
			 winner="White";
		 }
		 
		 String winnername="";
		 String losername="";
		 
		 if(playdetails.get(usercookie).get("color").get(0).equals(winner)){
			 winnername=((HashMap<String,String>)context.getAttribute("cookie")).get(usercookie);
			 losername=((HashMap<String,String>)context.getAttribute("cookie")).get(oppcookie);
		 }
		 else {
			 winnername=((HashMap<String,String>)context.getAttribute("cookie")).get(oppcookie);
			 losername=((HashMap<String,String>)context.getAttribute("cookie")).get(usercookie);
		 }
		 
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  
		  String winnerid="";
		  String loserid="";
			 
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="select player_id,username from player_info where username in ('"+winnername+"','"+losername+"')";
				ResultSet data_table=stmt.executeQuery(Query);
				
				while(data_table.next()) {
					
					if(data_table.getString("username").equals(winnername)) {
						winnerid=data_table.getInt("player_id")+"";
					}
					else {
						loserid=data_table.getInt("player_id")+"";
					}
				}
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  
		  
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="update game_list set winner_id="+Integer.parseInt(winnerid)+" where game_id="+Integer.parseInt(gameid.substring(gameid.indexOf('_')+1, gameid.length()));
				stmt.executeUpdate(Query);
				
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  try {
			  conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		  
		
		
		
		HashMap<String,String> result= new HashMap<String,String>();
		result.put("redirect","http://localhost:8080/winner");
	     System.out.println(result);
	     
	     Result result1 =
			        PusherService.getDefaultInstance()
			            .trigger(
			                data.get("channel_id"),
			                "redirect", 
			                result
			                ); 
		 }
		 else {
			 response.getWriter().write("incorrect");
		 }
	}
}
