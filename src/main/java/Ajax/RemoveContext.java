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
 		 HashMap<String,String> values=(HashMap<String,String> )context.getAttribute("cookie");	
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
		 if(data.get("white").equals("0") && Integer.parseInt(data.get("white")) < Integer.parseInt(data.get("black"))) {
			  winner="Black";
		 }
		 else {
			 winner="White";
		 }
		 
		 String winnername="";
		 String losername="";
		 HashMap<String,String> cookiename = (HashMap<String,String>)context.getAttribute("cookie");
		 if(playdetails.get(usercookie).get("color").get(0).equals(winner)){
			 winnername=cookiename.get(usercookie);
			 losername=cookiename.get(oppcookie);
		 }
		 else {
			 winnername=cookiename.get(oppcookie);
			 losername=cookiename.get(usercookie);
		 }
		 
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  
		  String winnerid="";
		  String loserid="";
		  int win=0;
		  int loss=0;
		  
		  
		 
			 
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="select player_id,username,win,loss from player_info where username in ('"+winnername+"','"+losername+"')";
				ResultSet data_table=stmt.executeQuery(Query);
				
				while(data_table.next()) {
					
					if(data_table.getString("username").equals(winnername)) {
						winnerid=data_table.getInt("player_id")+"";
						win=data_table.getInt("win")+1;
					}
					else {
						loserid=data_table.getInt("player_id")+"";
						loss=data_table.getInt("loss")+1;
					}
				}
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="update player_info set win="+win+" where username='"+winnername+"'";
			    System.out.println(Query);
				stmt.executeUpdate(Query);
				
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  

		  try{
			    Statement stmt = conn.createStatement();
			    String Query="update player_info set loss="+loss+" where username='"+losername+"'";
				stmt.executeUpdate(Query);
				
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  
		  
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="update game_list set winner_id="+Integer.parseInt(winnerid)+" where game_id="+Integer.parseInt(gameid.substring(gameid.indexOf('_')+1, gameid.length()));
				System.out.println(Query);
			    stmt.executeUpdate(Query);
				
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }

		  
		  int score1=0;
		  int score2=0;
		  
		  if(playdetails.get(usercookie).get("color").get(0).equals("White")) {
			  
			  score1=Integer.parseInt(data.get("white"));
			  score2=Integer.parseInt(data.get("black"));
			  
		  }
		  else {
			  score2=Integer.parseInt(data.get("white"));
			  score1=Integer.parseInt(data.get("black"));	  
			  
		  }
		  
		 
		  try{
			    Statement stmt = conn.createStatement();
				String Query="update player_info set score="+score1+" where username='"+values.get(usercookie)+"'";
				System.out.println(Query);
				stmt.executeUpdate(Query);//execution					
		    } catch (SQLException e) {
		      System.out.println(e+"");
		      
		    }
		  try{
			    Statement stmt = conn.createStatement();
				String Query="update player_info set score="+score2+" where username='"+values.get(oppcookie)+"'";
				System.out.println(Query);
				stmt.executeUpdate(Query);//execution					
		    } catch (SQLException e) {
		      System.out.println(e+"");
		      
		    }
		 
		  
		  try {
			  conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		  
		
		
		
		HashMap<String,String> result= new HashMap<String,String>();
		result.put("redirect","http://localhost:8080/home/winner");
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
