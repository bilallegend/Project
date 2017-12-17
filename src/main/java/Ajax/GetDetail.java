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

public class GetDetail extends HttpServlet {
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  Gson gson = new GsonBuilder().create(); 
		 ServletContext context=request.getSession().getServletContext();
		 ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  HashMap<String,String> details=new HashMap<String,String>();
		 String cookie=Cooky.getCookieValue("gc_account", request.getCookies());
		 
			 
			 String name=request.getParameter("name");
			 try{
				    Statement stmt = conn.createStatement();
				    String Query="select username,win,score,photo from player_info where username='"+name+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						
						details.put("name", data_table.getString("username"));
						details.put("win", data_table.getInt("win")+"");
						details.put("score", data_table.getString("number"));
						details.put("photo", data_table.getString("photo"));

					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
			 try {
				  conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			 response.getWriter().println(gson.toJson(details));
			  
			 
			 
		 }
		 
	 }

