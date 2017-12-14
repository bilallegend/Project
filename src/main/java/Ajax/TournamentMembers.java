package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import HelperClasses.ConnectionDatabase;

public class TournamentMembers extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	
	
   	 HashMap<String,String> result= new  HashMap<String,String>(); 
	     Gson gson = new GsonBuilder().setPrettyPrinting()
                   .create();
	     String json ="";
   	 ServletContext context  =   request.getSession().getServletContext();
   	ArrayList<Integer> tournaments=(ArrayList<Integer>) context.getAttribute("tournaments");
   	
   	String t="";
	if(tournaments==null) {
		ArrayList<Integer> array=new ArrayList<Integer>();
		array.add(1);
		context.setAttribute("tournaments",array);
		result.put("currtour","AB-"+1);
	    t=array.get(array.size()-1)+"";
	}
	else {
		   
		result.put("currtour","AB-"+tournaments.get(tournaments.size()-1));
		t=tournaments.get(tournaments.size()-1)+"";
	}
   	
   	
   	 ArrayList<String> members=(ArrayList<String>) context.getAttribute(t);
   	 String div="<p>Tournament Players</p>";
   	 if(members==null) {
   		 div+="<div><div></div><p>There is no member</p></div>";
   		 result.put("members", div);
   		 result.put("extra","8");
   		 System.out.println("dhfgrjf"+request.getParameter("num").equals("0"));
   		System.out.println("dhfgrjf"+request.getParameter("num"));
   		if(request.getParameter("num").equals("0")) {
  			 
   			result.put("join","hidden");
  		 }
   		else {
   		 result.put("join","visible");
   		}
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
   		 String visibility="";
   		 if(members.contains(name)||request.getParameter("num")=="0") {
   			visibility="hidden";
   		 }
   		else {
  			    visibility="visible";
  		 }
   		 if(4-members.size()==0) {
   			//String extra="<div><div id='cir'><div><div><div id='1'></div></div><div><div id='2'></div></div></div><div><div><div id='3'></div></div><div><div id='4'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div><div><div id='cir'><div><div><div id='5'></div></div><div><div id='6'></div></div></div><div><div><div id='7'></div></div><div><div id='8'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div>";
   			String extra="<div><div id='cir'><div><div><div id='1'></div></div><div><div id='2'></div></div></div><div><div><div id='3'></div></div><div><div id='4'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div><div><div id='cir'><div><div><div id='5'></div></div><div><div id='6'></div></div></div><div><div><div id='7'></div></div><div><div id='8'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div>";
   			 result.put("extra",extra);
   			visibility="hidden";
			 ArrayList<String> randommembers=(ArrayList<String> )context.getAttribute(t+" members");
			
			 if(randommembers==null) {
			 ArrayList<Integer> a=new  ArrayList<Integer>();
			 ArrayList<String>  b=new ArrayList<String>();
			 while(a.size()<4) {
				 int random = (int)(Math.random() * 4 + 1);
				 if(!a.contains(random)) {
					 System.out.println(random-1);
					a.add(random-1);
				 }
			 }
			 for(int i=0;i<a.size();i++) {
				 b.add(members.get(i));
			 }
			 context.setAttribute(t+" members",b);
		  } 
			 ArrayList<String> randommembers1=(ArrayList<String> )context.getAttribute(t+" members");
			 ConnectionDatabase psql = new ConnectionDatabase();
			 Connection conn			=psql.createConnection("gamecenter");
			 System.out.println(randommembers1);
			 for(int i=0;i<randommembers1.size();i++) {
				 String photo="";
				 try{
					    Statement stmt = conn.createStatement();
						String Query="select photo from player_info where username='"+randommembers1.get(i)+"'";
						ResultSet data_table=stmt.executeQuery(Query);
						
						while(data_table.next()) {
							photo+=data_table.getString("photo");
						}
						
				    } catch (SQLException e) {
				        System.out.println(e);
				    }
				 try {
					  conn.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				 
				 result.put(i+"","http://localhost:8080"+photo);
			 }
			 
			 
		 
		 result.put("join",visibility);
		 
   			// result.put("join","hidden");
   		 }
   		 
   		json=gson.toJson(result);
	     response.getWriter().write(json);
   	 }
	}

}

