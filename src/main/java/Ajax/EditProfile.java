package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class EditProfile extends HttpServlet {
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  Gson gson = new GsonBuilder().create(); 
		 ServletContext context=request.getSession().getServletContext();
		  ConnectionDatabase psql = new ConnectionDatabase();
		  Connection conn			=psql.createConnection("gamecenter");
		  HashMap<String,String> details=new HashMap<String,String>();
		 String cookie=Cooky.getCookieValue("gc_account", request.getCookies());
		 if(cookie==null) {
			 response.sendRedirect("/home");
		 }
		 else {
			 
			 String name=((HashMap<String,String>) context.getAttribute("cookie")).get(cookie);
			 System.out.println(name);
			 try{
				    Statement stmt = conn.createStatement();
				    String Query="select photo,email_id,username,number from player_info where username='"+name+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						
						details.put("name", data_table.getString("username"));
						details.put("mail", data_table.getString("email_id"));
						details.put("number", data_table.getString("number"));
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
			 System.out.println(details);
			 response.getWriter().println(gson.toJson(details));
			  
			 
			 
		 }
		 
	 }

}
