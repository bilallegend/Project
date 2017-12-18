package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class ChangePassword extends HttpServlet {
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		 boolean pass=Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher(request.getParameter("curr")).matches();
		 boolean pass1=Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher(request.getParameter("newpass")).matches();
		 boolean pass2=Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher(request.getParameter("newpass1")).matches();
		 System.out.println(request.getParameter("curr"));
		 System.out.println(request.getParameter("newpass"));
		 System.out.println(request.getParameter("newpass1"));
		 System.out.println(Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher("123456789").matches());
		 System.out.println(Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher("1234567890").matches());
		 ServletContext context=request.getSession().getServletContext();
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		  HashMap<String,String> result=new HashMap<String,String>();
		  Gson gson = new GsonBuilder().create(); 

		 
		 String cookie=Cooky.getCookieValue("gc_account", request.getCookies());
	      if(cookie==null) {
	    	  response.sendRedirect("/home");
	      }
	      else {
	    	  
	    	  String name1=((HashMap<String,String>) context.getAttribute("cookie")).get(cookie);
	    	  int id=0;
	    	  try{
				    Statement stmt = conn.createStatement();
				    String Query="select player_id from player_info where username='"+name1+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						
						id=data_table.getInt("player_id");

					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
	      
		 if(pass&&pass1&&pass2) {
			 
			 if(request.getParameter("newpass").equals(request.getParameter("newpass1"))) {
			 
			 String old="";
			 
			 try{
		  		    Statement stmt = conn.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
		  			String Query="select password from player_info  where player_id="+id;	  			
                     ResultSet data_table=stmt.executeQuery(Query);
      					
					while(data_table.next()) {
						
						old=data_table.getString("password");
					}
					
			        } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			       }
		  			
		  	if(request.getParameter("curr").equals(old)) {
			 
			 try{
		  		    Statement stmt = conn.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
		  			String Query="update player_info set password='"+request.getParameter("newpass")+"' where player_id="+id;	  			
		  			stmt.executeUpdate(Query);
		  			
		  			
		  	    } catch (SQLException e) {
		  	    	
		  	    }
		          
		    	  result.put("status","ok");
		    	
		    	  response.getWriter().write(gson.toJson(result));
		  	}
		  	else {
		  		
		  		result.put("status","Invalid current password");
		    	
		    	  response.getWriter().write(gson.toJson(result));
		  		
		  	}
	    }
			 else {
				 
				 result.put("status","Password didn't match");
			    	
		    	  response.getWriter().write(gson.toJson(result));
				 
			 }
	 }
		 else {
			 
			 result.put("status","Invalid data");
		    	
	    	  response.getWriter().write(gson.toJson(result));
		 }
	     
       } 
	
	}

}
