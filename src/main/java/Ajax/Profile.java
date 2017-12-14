package Ajax;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.*;
public class Profile extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		 ServletContext context  =   request.getSession().getServletContext();
	   	 HashMap<String,String> users=(HashMap<String,String>)context.getAttribute("cookie");
	   	 ArrayList<Integer> tournaments= (ArrayList<Integer>) context.getAttribute("tournaments");
	   	 String name="";
	   	HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                 .create();
	      String json ="";
	     if(users!=null) {
		 for(String s:users.keySet()) {
	   		 
	   		 if(s.equals(request.getParameter("num"))) {
	   			 name=users.get(s);
	   			 break;
	   		 }
	   	 }
	      } 
		     Statement stmt;
		     String mail="";
		     String photo="";
			try {
				stmt = conn.createStatement();
				String Query="select email_id,photo from player_info where username='"+name+"'";//Query to be passed
				ResultSet data_table=stmt.executeQuery(Query);
				while(data_table.next()) {
					mail=data_table.getString("email_id");
				    photo=data_table.getString("photo");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.put("name", name);
			result.put("mail", mail);
			result.put("photo",photo);
			if(tournaments==null) {
				ArrayList<Integer> array=new ArrayList<Integer>();
				array.add(1);
				context.setAttribute("tournaments",array);
				result.put("currtour","AB-"+1);
			}
			else {
				   
				result.put("currtour","AB-"+tournaments.get(tournaments.size()-1));
			}
			json=gson.toJson(result);
			try {
				  conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		     response.getWriter().write(json);
			
	}
}	