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
	
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<String,String> result= new  HashMap<String,String>(); 
	      Gson gson = new GsonBuilder().setPrettyPrinting()
                 .create();
	      String json ="";
	      
		
		Cookie[] c=request.getCookies();
		String num="";
		for(Cookie cookie:c) {
			if(cookie.getName().equals("gc_account")) {
				num=cookie.getValue();
			}
		}
		if(num.equals("")) {
			result.put("ok","no");
			result.put("url","http://localhost:8080/home");
			json=gson.toJson(result);
		      System.out.println(result);
			  response.getWriter().write(json);
			
		}
		
		ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
	   	 ServletContext context  =   request.getSession().getServletContext();
	   	 HashMap<String,String[]> gamedetail= (HashMap<String,String[]>) context.getAttribute("GameIds");
	   	 
		  String gameid="";
		  for(String i:gamedetail.keySet()) {
			  if((gamedetail.get(i))!=null&&((gamedetail.get(i)[0].equals(num)||(gamedetail.get(i)[1].equals(num)))) ){
				  gameid=i;
			  }
		  }
		  if(gameid.equals("")) {
			  result.put("ok","no");
			  result.put("url","http://localhost:8080/home");
			  json=gson.toJson(result);
		      System.out.println(result);
			  response.getWriter().write(json);
		  }
		  else {
			  
			  HashMap<String,String[]> d=(HashMap<String,String[]>) context.getAttribute("DivMap");
			  String player1name="";
			  String player2name="";
			  HashMap<String,String> detail=(HashMap<String,String>) context.getAttribute("cookie");
			  player1name=d.get(gamedetail.get(gameid)[0])[3];
			  player2name=d.get(gamedetail.get(gameid)[1])[3];
			  System.out.println(player1name);
			  System.out.println(player2name);
			  
			  
			  
		      System.out.println(gamedetail.get(gameid)[0]);
		      System.out.println(gamedetail.get(gameid)[1]);
		      HashMap<String,HashMap<String,ArrayList<String>>> playdetail=( HashMap<String,HashMap<String,ArrayList<String>>> )context.getAttribute("PlayDetails");
		      System.out.println(playdetail);
		      HashMap<String,ArrayList<String>>di=playdetail.get(gamedetail.get(gameid)[0]);
		      HashMap<String,ArrayList<String>>di2=playdetail.get(gamedetail.get(gameid)[1]);
		      
		      result.put("player1", player1name);
		      result.put("player2", player2name);
		      result.put("color1",di.get("color").get(0));
		      result.put("color2",di2.get("color").get(0));
		      result.put("status1",di.get("status").get(0));
		      result.put("status2",di2.get("status").get(0));
		      result.put("gameid",gameid);
		      json=gson.toJson(result);
		      System.out.println(result);
			  response.getWriter().write(json);
		  }
	}

}	