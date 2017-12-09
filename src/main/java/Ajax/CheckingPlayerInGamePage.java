package Ajax;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.*;
public class CheckingPlayerInGamePage extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
	   	 ServletContext context  =   request.getSession().getServletContext();
	   	 HashMap<String,ArrayList<String>> gamedetail= (HashMap<String,ArrayList<String>>) context.getAttribute("playdetails");
		  String gameid="";
		  for(String i:gamedetail.keySet()) {
			  if(gamedetail.get(i).contains(request.getParameter("num"))) {
				  gameid=i;
			  }
		  }
		  if(gameid.equals("")) {
			  response.sendRedirect("http://localhost:8080/home");
		  }
		  else {
			     
			  Number player1=0;
				Number player2=0;
			  
			  try{
				    Statement stmt = conn.createStatement();
					String Query="select player1,player2 from game where game_id='"+gameid+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						player1=data_table.getInt("player1");
						player2=data_table.getInt("player2");		
					}
					
			    } catch (SQLException e) {
			       // System.out.println(e);
			    	System.out.println("Anu");
			    }
			  String player1name="";
			  String player2name="";
			  try{
				    Statement stmt = conn.createStatement();
					String Query="select username,player_id from player_info where player_id in ('"+player1+"','"+player2+"')";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						if((Number)data_table.getInt("player_id")==player1) {
						player1name=data_table.getString("username");
						}
						else {
						player2name=data_table.getString("player2");		
					}
					}		
					
			    } catch (SQLException e) {
			       // System.out.println(e);
			    	System.out.println("Anu");
			    }
			  HashMap<String,String> result= new  HashMap<String,String>(); 
		      Gson gson = new GsonBuilder().setPrettyPrinting()
	                   .create();
		      String json ="";
		      result.put("player1", player1name);
		      result.put("player2", player2name);
		      result.put("gameid",gameid);
		      json=gson.toJson(result);
			  response.getWriter().write(json);
		  }
	}
}	