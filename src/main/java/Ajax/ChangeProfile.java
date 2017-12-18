package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class ChangeProfile extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		 boolean name=Pattern.compile("^[A-Za-z0-9]{3,30}$").matcher(request.getParameter("name")).matches();
		 boolean phone=Pattern.compile("^[0-9-()+]{10,15}").matcher(request.getParameter("number")).matches();
		 
		 boolean check[]= {name,phone};
		 ServletContext context=request.getSession().getServletContext();
		 HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                   .create();
	      String json ="";
	      
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
	      
	      if(name&&phone) {
	    	  
	    	  try{
	  		    Statement stmt = conn.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
	  			String Query="update player_info set username='"+request.getParameter("name")+"',email_id='"+request.getParameter("mail")+"',number='"+request.getParameter("number")+"' where player_id="+id;	  			
	  			stmt.executeUpdate(Query);
	  			
	  			
	  	    } catch (SQLException e) {
	  	    	
	  	    }
	          
	    	  result.put("status","ok");
	    	  json=gson.toJson(result); 
	    	  response.getWriter().write(json);
	    	  
	      }
	      else {
	    	  
	    	    String errors="";
	    	    String a[]= {"#na","#no"};
	    	    
				
	    	    for(int i=0;i<check.length;i++) {
	    	    	if(check[i]==false) {
	    	    		errors+="&"+a[i];
	    	    	}
	    	    }
	    	    result.put("status","Invalid data");
	    	    result.put("errors",errors);
	    	    json=gson.toJson(result);  
	    	    response.getWriter().write(json);
	    	  
	      }
	      }
	} 
}
