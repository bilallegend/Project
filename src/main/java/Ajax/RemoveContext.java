package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class RemoveContext extends HttpServlet {

	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  
		 ServletContext context=request.getSession().getServletContext(); 
		 HashMap<String,String[]> gameids= (HashMap<String,String[]>) context.getAttribute("GameIds");
		 HashMap<String,HashMap<String,ArrayList<String>>> playdetails= (HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		 
		 String usercookie=Cooky.getCookieValue("gc_account",request.getCookies());
		 String oppcookie=null;
		 String gameid=null;
		 
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
		 if(request.getAttribute("white").equals("0")) {
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
			    String Query="insert into winner_id where game_id="+Integer.parseInt(gameid);
				stmt.executeUpdate(Query);
				
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  try {
			  conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		  
		gameids.remove(gameid);
		
		playdetails.remove(usercookie);
		playdetails.remove(oppcookie);
		
	   
	    response.getWriter().write("ok");
		 
		
	}
}
