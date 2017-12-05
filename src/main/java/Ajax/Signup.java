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
public class Signup extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		
			String[] params= new String[]{"usrname,usrmail,usrpassword"};
			      
			      HashMap<String,String> result= new  HashMap<String,String>(); 
			      Gson gson = new GsonBuilder().setPrettyPrinting()
		                    .create();
			      String json ="";
				  String res=psql.insert(conn, "player_info", "username,email_id,password,number", "'"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("pass")+"',"+request.getParameter("num"));
				  if(res.equals("Signup Successfull")) {
				  CookieCreator one = new CookieCreator();
                  one.createContext("gc_account", request.getParameter("name"),request,response);
                  //response.getWriter().write("{'status':'Signup Successful','name':'"+request.getParameter("name")+"','mail':'"+request.getParameter("email")+"'}");
                  result.put("status","Signup Successful");
                  result.put("name",request.getParameter("name"));
                  result.put("mail",request.getParameter("email"));
                  json=gson.toJson(result);
                  
                  response.getWriter().write(json);
                  return;
				  }
				
				  else {
				if (res .contains("player_info_username_key")) {
					
					result.put("status","Name already Exist ");
					json=gson.toJson(result);
					response.getWriter().write(json);
					return;
				}
				if (res.contains("player_info_email_id_key")) {
					result.put("status","Mail Already Exist");
				    json=gson.toJson(result);
					response.getWriter().write(json);
					return;
				}
			}
				  
		}
	}

		