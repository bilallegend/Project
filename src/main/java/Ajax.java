
import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


import HelperClasses.*;
public class Ajax extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		if(request.getPathInfo().equals("/signup")) {
			System.out.println(request.getParameterMap());
			System.out.println("'"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("pass")+"',"+request.getParameter("num"));
			
			
			String[] params= new String[]{"usrname,usrmail,usrpassword"};
			try {
				psql.insert(conn, "player_info", "username,email_id,password,number", "'"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("pass")+"',"+request.getParameter("num"));
				CookieCreator one = new CookieCreator();
                one.createContext("gc_account", request.getParameter("name"),request,response);
				response.getWriter().write("'"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("pass")+"',"+request.getParameter("num"));
			}catch(Exception e) {
				System.out.println(e);
				if ((e + "").contains("player_info_username_key")) {
					response.getWriter().write("Name already Exist");
				}
				if ((e + "").contains("player_info_email_id_key")) {
					response.getWriter().write("Mail already Exist");
				}
			}
		  	
		}
		
		System.out.println(request.getPathInfo()+" "+request.getRequestURL());
		
		if(request.getPathInfo().equals("/signin")){
			
			ResultSet data_table=psql.select(conn, "player_info", "username,email_id,password", request.getParameter("name"));
			
			try {
				while(data_table.next()) {
					
				    if(data_table.getString("email_id").equals(request.getParameter("email")) ){
				        if(data_table.getString("password").equals(request.getParameter("pass"))){
				            CookieCreator one = new CookieCreator();
				            one.createContext("gc_account", data_table.getString("username"),request,response);
				            response.getWriter().write("200");//Login sucessfull
				            
				            return;
				        }else{
				        	response.getWriter().write("150");//Password is incorrect
				            return;
				        }
				    }
				    else {
				    	response.getWriter().write("100");//Invalid mail
				    	return;
				    }
				}
				response.getWriter().write("50");//Invalid username
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	
}	
	
