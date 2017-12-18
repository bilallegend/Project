package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class GetPhoto extends HttpServlet {
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 ServletContext context=request.getSession().getServletContext();
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		  HashMap<String,String> result=new HashMap<String,String>();
		  Gson gson = new GsonBuilder().create();
		  
		  String cookievalue=Cooky.getCookieValue("gc_account", request.getCookies());
		  String name=( (HashMap<String,String>) context.getAttribute("cookie")).get(cookievalue);
		  String photo="";
		  
		  try{
			    Statement stmt = conn.createStatement();
			    String Query="select photo from player_info where username='"+name+"'";
				ResultSet data_table=stmt.executeQuery(Query);
				
				while(data_table.next()) {
					
					photo=data_table.getString("photo");

				}
				
		    } catch (SQLException e) {
		    	
		        System.out.println(e+"");
		    }
		  if(photo.equals("")|| photo==null) {
			  
			  photo="../Images/pr.png";
			  
		  }
		  result.put("photo", photo);
		  response.getWriter().write(gson.toJson(result));
		  
	    }
	 }

