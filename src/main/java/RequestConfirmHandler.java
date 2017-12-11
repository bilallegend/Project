import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;
import HelperClasses.Redirecter;

public class RequestConfirmHandler extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};

	  @Override
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String body = CharStreams.readLines(request.getReader()).toString();
	    System.out.println(body);
	    String name = Cooky.getContextName("gc_account",request.getCookies(),"cookie", request);
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    System.out.println(json);
	    
	    String Reply  = data.get("reply");
	    HashMap<String,String[]> reqInfo = Cooky.getContextValue("reqInfo",request);
	    Map<String,String> messageData = new HashMap<String, String>();
	    
		if(reqInfo == null) {
			messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
		}else {
			reqInfo = Cooky.getContextValue("reqInfo",request);
			String requesterName = "";
			boolean tryF = true;
			for(String Key : reqInfo.keySet()) {
				if(reqInfo.get(Key)[1].equals(name)) {
					messageData.put("replyId",reqInfo.get(Key)[0]);
					requesterName=Key;
					tryF=false;
					break;
				}
				
			}
			reqInfo.remove(requesterName);			
			if(Reply.equals("Yes") && !tryF) {
				messageData.put("reply", Reply);
				messageData.put("redir", Redirecter.giveUrlFor(request,"/game"));
			}else {
				messageData.put("reply", "Sorry!");
			}
			Result result =
			        PusherService.getDefaultInstance()
			            .trigger(
			                "private-MyNotification-"+messageData.get("replyId"),
			                "GameResp", // name of event
			                messageData);
			messageData.put("status", result.getStatus().name());
			System.out.println("private-MyNotification-"+messageData.get("replyId"));
			messageData.remove("replyId");
		}
		
		String[] info=reqInfo.get(name);
		String oppname=info[info.length-1];
		
		ServletContext context  =   request.getSession().getServletContext();
		HashMap<String,String[]> d=(HashMap<String,String[]>) context.getAttribute("DivMap");
		ArrayList<String> gc_account=new ArrayList<String>();
		HashMap<String,ArrayList<String>> gc=new HashMap<String,ArrayList<String>>();
		gc.put("cookie", gc_account);
		for(String i:d.keySet()) {
			if(d.get(i)[3].equals(name)||d.get(i)[3].equals(oppname)) {
				gc_account.add(i);
			}
		}
		ConnectionDatabase psql = new ConnectionDatabase();
		 Connection conn			=psql.createConnection("gamecenter");
		 int count=0;
		 try{
			    Statement stmt = conn.createStatement();
				String Query="select id from game ";
				ResultSet data_table=stmt.executeQuery(Query);
				
				while(data_table.next()) {
					count+=1;
				}
				
		    } catch (SQLException e) {
		       // System.out.println(e);
		    	System.out.println("Anu");
		    }
		 HashMap<String,HashMap<String,ArrayList<String>>> pd1=new HashMap<String,HashMap<String,ArrayList<String>>>();
		 if(context.getAttribute("playdetails")==null) {
			 context.setAttribute("playdetails", pd1);
		 }
		 HashMap<String,HashMap<String,ArrayList<String>>> pd=( HashMap<String,HashMap<String,ArrayList<String>>> )context.getAttribute("playdetails");
		 pd.put("game "+count, gc);
		 
		 HashMap<String,ArrayList<String>> white=new HashMap<String,ArrayList<String>>();
		 ArrayList<String> color=new ArrayList<String>();
		 ArrayList<String> colorid=new ArrayList<String>();
		 colorid.add("29");
		 colorid.add("36");
		 color.add("white");
		 white.put("color",color);
		 white.put("coins",colorid);
		 HashMap<String,ArrayList<String>> black=new HashMap<String,ArrayList<String>>();
		 ArrayList<String> color1=new ArrayList<String>();
		 color1.add("black");
		 ArrayList<String> color1id=new ArrayList<String>();
		 color1id.add("29");
		 color1id.add("36");
		 black.put("color",color);
		 black.put("coins",color1id);
		 pd.put(gc_account.get(0),black);
		 pd.put(gc_account.get(1),white);
		 
		context.setAttribute("playdetails", pd);
		 
		response.getWriter().println(gson.toJson(messageData));
	    
	  }
}
