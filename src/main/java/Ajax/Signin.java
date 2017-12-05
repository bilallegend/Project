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

	
	
		
		ResultSet data_table=psql.select(conn, "player_info", "username,email_id,password", request.getParameter("name"));
		HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                    .create();
	      String json ="";
		
		try {
			while(data_table.next()) {
				
			    if(data_table.getString("email_id").equals(request.getParameter("email")) ){
			        if(data_table.getString("password").equals(request.getParameter("pass"))){
			            CookieCreator one = new CookieCreator();
			            one.createContext("gc_account", data_table.getString("username"),request,response);
			             result.put("status","200");
		                  result.put("name",request.getParameter("name"));
		                  result.put("mail",request.getParameter("email"));
		                  json=gson.toJson(result);
		                  System.out.println(result);
		               
		                  
		                  response.getWriter().write(json);
			           
			            
			            return;
			        }else{
			        	result.put("status","150");
			        	  System.out.println(result);
			        	  json=gson.toJson(result);
		                  response.getWriter().write(json);
			        	
			            return;
			        }
			    }
			    else {
			    	result.put("status","100");
			    	json=gson.toJson(result);
			    	  System.out.println(result);
	                  response.getWriter().write(json);
			    	return;
			    }
			}
			result.put("status","50");
            System.out.println(result);
            json=gson.toJson(result);
            response.getWriter().write(json);//Invalid username
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}

