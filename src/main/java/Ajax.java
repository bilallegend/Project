
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.*;
public class Ajax extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		if(request.getPathInfo().equals("/signup")) {
			
			
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
		
		System.out.println(request.getPathInfo()+" "+request.getRequestURL());
		
		if(request.getPathInfo().equals("/signin")){
			
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
		
		if(request.getPathInfo().equals("/tournament")){
			
			response.getWriter().write("http://localhost:8080/tournament");
		}
         if(request.getPathInfo().equals("/joinedmembers")){
        	 HashMap<String,String> result= new  HashMap<String,String>(); 
		     Gson gson = new GsonBuilder().setPrettyPrinting()
	                    .create();
		     String json ="";
        	 ServletContext context  =   request.getSession().getServletContext();
        	 HashSet<String> members=(HashSet<String>) context.getAttribute("tournamentmembers");
        	 String div="<p>Tournament Players</p>";
        	 if(members==null) {
        		 div+="<div><div></div><p>There is no member</p></div>";
        		 result.put("members", div);
        		 result.put("extra","8");
        		 result.put("join","visible");
        		 json=gson.toJson(result);
			     response.getWriter().write(json);
			     
        	 }
        	 else {
        		 HashMap<String,String> users=(HashMap<String,String>)context.getAttribute("cookie");
            	 String name="";
        		 for(String s:users.keySet()) {
            		 System.out.println(request.getParameter("num"));
            		 System.out.println(users.get(s));
            		 if(s.equals(request.getParameter("num"))) {
            			 name=users.get(s);
            			 break;
            		 }
            	 }
        		 
        		 for(String name1:members) {
        			 
        			 div+="<div><div></div><p>"+name1+"</p></div>";
        		 }
        		 result.put("members", div);
        		 result.put("extra",8-members.size()+"");
        		 System.out.println(name);
        		 System.out.println(members.contains(name));
        		 if(8-members.size()==0||members.contains(name)) {
        			 result.put("join","hidden");
        		 }
        		 else {
        			 result.put("join","visible");
        		 }
        		 json=gson.toJson(result);
			     response.getWriter().write(json);
        	 }
		}
         if(request.getPathInfo().equals("/join")){
        	 ServletContext context  =   request.getSession().getServletContext();
        	 HashMap<String,String> users=(HashMap<String,String>)context.getAttribute("cookie");
        	 String name="";
        	 String visibility="visible";
        	 String re="<p>Tournament Players</p>";
        	 HashMap<String,String> result= new  HashMap<String,String>(); 
		      Gson gson = new GsonBuilder().setPrettyPrinting()
	                    .create();
		      String json ="";
        	 for(String s:users.keySet()) {
        		 System.out.println(request.getParameter("num"));
        		 System.out.println(users.get(s));
        		 if(s.equals(request.getParameter("num"))) {
        			 name=users.get(s);
        			 break;
        		 }
        	 }
        	 HashSet<String> joinedmembers= (HashSet<String>) context.getAttribute("tournamentmembers");
        	 if(joinedmembers==null) {
        		 
        		 HashSet<String> j= new HashSet<String>();
        		 j.add(name);
        		 context.setAttribute("tournamentmembers",j);
              }
        	 else {
        		 joinedmembers.add(name);
        		 context.setAttribute("tournamentmembers",joinedmembers);
        	 }
        	 
        	 HashSet<String> members=(HashSet<String> )context.getAttribute("tournamentmembers");
        	 for(String names:members) {
    			 
    			 re+="<div><div></div><p>"+names+"</p></div>";
    		 }
    		 result.put("members", re);
    		 result.put("extra",(8-members.size())+"");
    		 if(8-members.size()==0) {
    			 visibility="hidden";
    		 }
    		 result.put("join",visibility);
    		 json=gson.toJson(result);
		     response.getWriter().write(json);
        	 
         }
		
	}
	
}	
	
