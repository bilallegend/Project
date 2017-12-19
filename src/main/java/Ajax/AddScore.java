package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class AddScore extends HttpServlet {

	 private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 Gson gson = new GsonBuilder().create();
		
			      String body = CharStreams.readLines(request.getReader()).toString();
				  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
				  Map<String, String> data = gson.fromJson(json, typeReference.getType());		      
				 		 ServletContext context=request.getSession().getServletContext();
				 		 HashMap<String,String> values=(HashMap<String,String> )context.getAttribute("cookie");	      

		 HashMap<String,HashMap<String,ArrayList<String>>> g= (HashMap<String,HashMap<String,ArrayList<String>>>)context.getAttribute("PlayDetails");
		  HashMap<String,String[]> gameid= ( HashMap<String,String[]>)context.getAttribute("GameIds");
		  Cookie[] cookie=request.getCookies();
		  String usercookie=Cooky.getCookieValue("gc_account", request.getCookies());
		  String gi=data.get("gameid");
		  System.out.println(data.get("gameid"));
		  String[] cookies=gameid.get(gi);
		  String oppcookie="";
		  if(cookies[0].equals(usercookie)) {
			  
			   oppcookie=cookies[1];
		  }
		  else {
			 oppcookie=cookies[0];
		  }
		  
		  int score1=0;
		  int score2=0;
		  
		  if(g.get(usercookie).get("color").get(0).equals("White")) {
			  
			  score1=Integer.parseInt(data.get("white"));
			  score2=Integer.parseInt(data.get("black"));
			  
		  }
		  else {
			  score2=Integer.parseInt(data.get("white"));
			  score1=Integer.parseInt(data.get("black"));	  
			  
		  }
		  
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  
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
		  response.getWriter().write("ok");
	 }
}
