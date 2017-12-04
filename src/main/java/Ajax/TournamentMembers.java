package Ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TournamentMembers extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	
	
   	 HashMap<String,String> result= new  HashMap<String,String>(); 
	     Gson gson = new GsonBuilder().setPrettyPrinting()
                   .create();
	     String json ="";
   	 ServletContext context  =   request.getSession().getServletContext();
   	 ArrayList<String> members=(ArrayList<String>) context.getAttribute("tournamentmembers");
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
   		 String name="";
   		 if(request.getParameter("num")=="0") {
   			 
   			 
   		 }
   		 else {
   			 HashMap<String,String> users=(HashMap<String,String>)context.getAttribute("cookie");
   			  name="";
   			 for(String s:users.keySet()) {
       		 
       		 if(s.equals(request.getParameter("num"))) {
       			 name=users.get(s);
       			 break;
       		 }
       	   }
   		 } 
   		 for(String name1:members) {
   			 
   			 div+="<div><div></div><p>"+name1+"</p></div>";
   		 }
   		 result.put("members", div);
   		 result.put("extra",8-members.size()+"");
   		 
   		 if(8-members.size()==0||members.contains(name)||request.getParameter("num")=="0") {
   			 result.put("join","hidden");
   		 }
   		 else {
   			 result.put("join","visible");
   		 }
   		 json=gson.toJson(result);
		 response.getWriter().write(json);
   	 }
	}

}

