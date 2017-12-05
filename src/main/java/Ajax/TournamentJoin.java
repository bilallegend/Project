package Ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
   		 System.out.println(request.getParameter("num"));
   		 System.out.println(users.get(s));
   		 if(s.equals(request.getParameter("num"))) {
   			 name=users.get(s);
   			 break;
   		 }
   	 }
   	 ArrayList<String> joinedmembers= (ArrayList<String>) context.getAttribute("tournamentmembers");
   	 if(joinedmembers==null) {
   		 
   		 ArrayList<String> j= new ArrayList<String>();
   		 j.add(name);
   		 context.setAttribute("tournamentmembers",j);
         }
   	 else {
   		 joinedmembers.add(name);
   		 context.setAttribute("tournamentmembers",joinedmembers);
   	 }
   	 
   	 ArrayList<String> members=(ArrayList<String> )context.getAttribute("tournamentmembers");
   	 for(String names:members) {
			 
			 re+="<div><div></div><p>"+names+"</p></div>";
		 }
		 result.put("members", re);
		 result.put("extra",(8-members.size())+"");
		 if(1-members.size()==0) {
			 visibility="hidden";
			 String extra=" <div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div><div><div></div><div style=\"left:240px\"></div><div style=\"left:436px\"></div><div style=\"left:630px\"></div></div><div><hr><hr style=\"left:290px\"><hr style=\"left:490px\"><hr style=\"left:684px\"></div><div><div></div><div style=\"left:222px\"></div><div style=\"left:423px\"></div><div style=\"left:617px\"></div></div><div><div></div><div style=\"left:469px\"></div></div><div><hr><hr style=\"left:478px\"></div><div><div></div><div style=\"left:470px\"></div></div>";
			 result.put("extra",extra);
		 }
		 result.put("join",visibility);
		 json=gson.toJson(result);
	     response.getWriter().write(json);
   	 
    }
	
}


