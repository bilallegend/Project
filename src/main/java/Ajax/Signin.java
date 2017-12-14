package Ajax;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.*;
public class Signin extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");

	
	
		
		ResultSet data_table=psql.select(conn, "player_info", "username,email_id,password,photo", request.getParameter("name"));
		HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                    .create();
	      String json ="";
	      String photo="";
		
		try {
			while(data_table.next()) {
				
			    if(data_table.getString("email_id").equals(request.getParameter("email")) ){
			        if(data_table.getString("password").equals(request.getParameter("pass"))){
			        	
			            CookieCreator one = new CookieCreator();
			            one.createContext("gc_account", data_table.getString("username"),request,response);
			             photo=data_table.getString("photo");
			             result.put("status","200");
		                  result.put("name",request.getParameter("name"));
		                  result.put("mail",request.getParameter("email"));
		                  if(photo==null) {
		                	  result.put("photo","../Images/pr.png");
		                  }
		                  else {
		                	  result.put("photo","http://localhost:8080"+photo);
		                  }
		                  json=gson.toJson(result);
		                  System.out.println(result);
		                  try {
		        			  conn.close();
		        			} catch (SQLException e) {
		        				
		        				e.printStackTrace();
		        			}
		                  
		                  response.getWriter().write(json);
			           
			            
			            return;
			        }else{
			        	result.put("status","150");
			        	  System.out.println(result);
			        	  json=gson.toJson(result);
			        	  try {
			    			  conn.close();
			    			} catch (SQLException e) {
			    				
			    				e.printStackTrace();
			    			}
		                  response.getWriter().write(json);
			        	
			            return;
			        }
			    }
			    else {
			    	result.put("status","100");
			    	json=gson.toJson(result);
			    	  System.out.println(result);
			    	  try {
						  conn.close();
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
	                  response.getWriter().write(json);
			    	return;
			    }
			}
			result.put("status","50");
            System.out.println(result);
            json=gson.toJson(result);
            try {
  			  conn.close();
  			} catch (SQLException e) {
  				
  				e.printStackTrace();
  			}
            response.getWriter().write(json);//Invalid username
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}

