package Ajax;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

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
		 boolean name=Pattern.compile("^[A-Za-z0-9]{3,30}$").matcher(request.getParameter("name")).matches();
		 boolean phone=Pattern.compile("^[0-9-()+]{10,15}").matcher(request.getParameter("num")).matches();
		 boolean pass=Pattern.compile("[A-Za-z0-9_-]{6,10}$").matcher(request.getParameter("pass")).matches();
		 boolean conpass=request.getParameter("pass").equals(request.getParameter("confirm"));
		 boolean check[]= {name,pass,conpass,phone};
		
			      System.out.println(name);
			      System.out.println(phone);
			      System.out.println(pass);
			      System.out.println(conpass);
			      
			      HashMap<String,String> result= new  HashMap<String,String>(); 
			      Gson gson = new GsonBuilder().setPrettyPrinting()
		                    .create();
			      String json ="";
			      String cookie=Cooky.getCookieValue("gc_account", request.getCookies());
			      System.out.println(cookie);
			      
			   
			      
			      if(name&&phone&&pass&&conpass) {
						
			    	 
			    	  
					
			      
				  String res=psql.insert(conn, "player_info", "username,email_id,password,number", "'"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("pass")+"',"+request.getParameter("num"));
				  System.out.println(res);
				  if(res.equals("Signup Successfull")) {
				  CookieCreator one = new CookieCreator();
                  one.createContext("gc_account", request.getParameter("name"),request,response);
                  //response.getWriter().write("{'status':'Signup Successful','name':'"+request.getParameter("name")+"','mail':'"+request.getParameter("email")+"'}");
                  result.put("status","Signup Successful");
                  result.put("name",request.getParameter("name"));
                  result.put("mail",request.getParameter("email"));
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
				
				  else {
				if (res .contains("player_info_username_key")) {
					
					result.put("status","Name already Exist ");
					json=gson.toJson(result);
					try {
						  conn.close();
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					response.getWriter().write(json);
					return;
				}
				if (res.contains("player_info_email_id_key")) {
					result.put("status","Mail Already Exist");
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
			}		
			      else {
			    	    String errors="";
			    	    String a[]= {"#errname","#errpass","#errpassword","#errnum"};
			    	    
						
			    	    for(int i=0;i<check.length;i++) {
			    	    	if(check[i]==false) {
			    	    		errors+="&"+a[i];
			    	    	}
			    	    }
			    	    result.put("status","Invalid data");
			    	    result.put("errors",errors);
			    	    json=gson.toJson(result);
			    	    try {
			  			  conn.close();
			  			} catch (SQLException e) {
			  				
			  				e.printStackTrace();
			  			}
						response.getWriter().write(json);
			      }
		  
			   
		}
	}

		