package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class AddGameId extends HttpServlet {

	  private Gson gson = new GsonBuilder().create();
	  private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};
	  private static int x=0;
	  @Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  
		  ServletContext context  =   request.getSession().getServletContext();
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  
		  String body = CharStreams.readLines(request.getReader()).toString();
		  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		  Map<String, String> data = gson.fromJson(json, typeReference.getType());
		  String id=data.get("gameid").substring(data.get("gameid").indexOf('_')+1, data.get("gameid").length());
		  System.out.println(id);
		  int no=-1;
		  int time=0;
		  if(!data.get("message1").equals("")) {
			  time=Integer.parseInt(data.get("message1"));
		  }
		  if(!data.get("message").equals("")) {
			  no=Integer.parseInt(data.get("message"));
		  }
		  try{
			    Statement stmt = conn.createStatement();
				String Query="insert into game_info (game_id,coin_move_id,Time) values ("+Integer.parseInt(id)+","+no+","+time+")";//Query to be passed
				System.out.println(Query);
				stmt.executeUpdate(Query);//execution
				
		    } catch (SQLException e) {
		    	
		      System.out.println(e+"");
		      
		    }
		 
	  }	  
	
}
