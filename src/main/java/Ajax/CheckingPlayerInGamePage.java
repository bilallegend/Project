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
public class CheckingPlayerInGamePage extends HttpServlet{

	
	@Override
	
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                 .create();
	      String json ="";
	      
		
		Cookie[] c=request.getCookies();
		String num=Cooky.getCookieValue("gc_account",request.getCookies());
		System.out.println("cookie  "+ num);
		System.out.println(num);
		if(num.equals("") || num == null) {
			System.out.println("num");
			response.sendRedirect("/home");
			result.put("ok","no");
			result.put("url","http://localhost:8080/home");
			json=gson.toJson(result);
		      System.out.println(result+"  "+num);
			  response.getWriter().write(json);
			  return;
			
		}
		
	   	 ServletContext context  =   request.getSession().getServletContext();
	   	 HashMap<String,String[]> gamedetail= (HashMap<String,String[]>) context.getAttribute("GameIds");
	   	 
		  String gameid="";
		  try {
			  for(String i:gamedetail.keySet()) {
				  if( gamedetail.get(i)[0].equals(num) || gamedetail.get(i)[1].equals(num) ){
					  gameid=i;
				  }
			  }
		  }catch(Exception e) {

			  System.out.println(gamedetail);
			  System.out.println("exception");
			  response.sendRedirect("/home");

			  result.put("ok","no");
			  result.put("url","http://localhost:8080/home");
			  json=gson.toJson(result);
		      System.out.println(result+"Exception ");
			  response.getWriter().write(json);

			  return;
		  }
		  
		  if(gameid.equals("") || gameid==null) {

			  System.out.println("gaemId");
			  response.sendRedirect("/home");

			  
			  result.put("ok","no");
			  result.put("url","http://localhost:8080/home");
			  json=gson.toJson(result);
		      System.out.println(result+" no game id");
			  response.getWriter().write(json);
			  return;
			  
		  }
		  else {
			  
			  getServletContext().getRequestDispatcher("/Jsp/board.jsp").forward(request, response);
  
		  }
	}

}	