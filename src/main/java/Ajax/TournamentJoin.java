package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;

public class TournamentJoin  extends HttpServlet{

	
	@Override
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	
	
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
   		 
   		 if(s.equals(request.getParameter("num"))) {
   			 name=users.get(s);
   			 break;
   		 }
   	 }
 	ArrayList<Integer> tournaments=(ArrayList<Integer>) context.getAttribute("tournaments");
   	String t=tournaments.get(tournaments.size()-1)+"";
   	 ArrayList<String> joinedmembers= (ArrayList<String>) context.getAttribute(t);
   	 if(joinedmembers==null) {
   		 
   		 ArrayList<String> j= new ArrayList<String>();
   		 j.add(name);
   		 context.setAttribute(t,j);
         }
   	 else {
   		 if(!joinedmembers.contains(name)) {
   		 joinedmembers.add(name);
   		 context.setAttribute(t,joinedmembers);
   		 }
   	 }
   	 
   	 ArrayList<String> members=(ArrayList<String> )context.getAttribute(t);
   	 for(String names:members) {
			 
			 re+="<div><div></div><p>"+names+"</p></div>";
		 }
		 result.put("members", re);
		 result.put("extra",(8-members.size())+"");
		 
		 if(4-members.size()==0) {
			 String extra="<div><div id='cir'><div><div><div id='1'></div></div><div><div id='2'></div></div></div><div><div><div id='3'></div></div><div><div id='4'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div><div><div id='cir'><div><div><div id='5'></div></div><div><div id='6'></div></div></div><div><div><div id='7'></div></div><div><div id='8'></div></div></div></div><div><div><div></div></div><div><div></div></div></div><div><div></div></div></div>";
			 result.put("extra",extra);
			 visibility="hidden";
			 ArrayList<String> randommembers=(ArrayList<String> )context.getAttribute(t+" members");
			
			 if(randommembers==null) {
			 ArrayList<Integer> a=new  ArrayList<Integer>();
			 ArrayList<String>  b=new ArrayList<String>();
			 while(a.size()<4) {
				 int random = (int)(Math.random() * 4 + 1);
				 if(!a.contains(random-1)) {
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
				       // System.out.println(e);
				    	System.out.println("Anu");
				    }
				 
				 result.put(i+"","http://localhost:8080"+photo);
			 }
		 }
		 result.put("join",visibility);
		 json=gson.toJson(result);
	     response.getWriter().write(json);
   	 
    }
	
}


