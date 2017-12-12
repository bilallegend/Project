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
	    String requesterName = "";
		if(reqInfo == null) {
			messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
		}else {
			reqInfo = Cooky.getContextValue("reqInfo",request);
			
			boolean tryF = true;
			for(String Key : reqInfo.keySet()) {
				if(reqInfo.get(Key)[1].equals(name)) {
					messageData.put("replyId",reqInfo.get(Key)[0]);
					requesterName=Key;
					tryF=false;
					break;
				}
				
			}
<<<<<<< HEAD
					
			if(Reply.equals("YES") && !tryF) {
=======
			reqInfo.remove(requesterName);			
			if(Reply.equals("Yes") && !tryF) {
>>>>>>> branch 'master' of https://github.com/bilallegend/Project.git
				messageData.put("reply", Reply);
				messageData.put("redir", Redirecter.giveUrlFor(request,"/play"));
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
		
		
		String oppname=requesterName;
		reqInfo.remove(requesterName);
		ServletContext context  =   request.getSession().getServletContext();
		HashMap<String,String[]> d=(HashMap<String,String[]>) context.getAttribute("DivMap");
		ArrayList<String> cookies=new ArrayList<String>();
		
		for(String i:d.keySet()) {
			if(d.get(i)[3].equals(name)||d.get(i)[3].equals(oppname)) {
				cookies.add(i);
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
		
		 if(context.getAttribute("GameIds")==null) {
			 context.setAttribute("GameIds", new HashMap<String,String[]>() );
		 }
		 if(context.getAttribute("PlayDetails")==null) {
			 context.setAttribute("PlayDetails", new HashMap<String,HashMap<String,ArrayList<String>>>() );
		 }
		 HashMap<String,String[]> pd=(HashMap<String,String[]>)context.getAttribute("GameIds");
		 String a[]= {"",""};
		 a[0]=cookies.get(0);
		 a[1]=cookies.get(1);
		 pd.put("game "+(count+1), a);
		 context.setAttribute("GameIds",pd);
		 
		 HashMap<String,HashMap<String,ArrayList<String>>> pd1=(HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		 HashMap<String,ArrayList<String>> White=new HashMap<String,ArrayList<String>>();
		 HashMap<String,ArrayList<String>> Black=new HashMap<String,ArrayList<String>>();
		 
		 ArrayList<String> color=new ArrayList<String>();
		 ArrayList<String> colorid=new ArrayList<String>();
		 ArrayList<String> status=new ArrayList<String>();
		 colorid.add("29");
		 colorid.add("36");
		 color.add("white");
		 status.add("Waiting");
		 
		 White.put("color",color);
		 White.put("coins",colorid);
		 White.put("status", status);
		 
		 
		 ArrayList<String> color1=new ArrayList<String>();
		 ArrayList<String> colorid1=new ArrayList<String>();
		 ArrayList<String> status1=new ArrayList<String>();
		 colorid1.add("28");
		 colorid1.add("37");
		 color1.add("Black");
		 status1.add("Playing");
		 
		 Black.put("color",color1);
		 Black.put("coins",colorid1);
		 Black.put("status", status1);

		 
		 pd1.put(cookies.get(0), White);
		 pd1.put(cookies.get(1), Black);
		 
		 context.setAttribute("PlayDetails",pd1);
		 
		
		System.out.println(pd);
		System.out.println(pd1);
		
		response.getWriter().println(gson.toJson(messageData));
	    
	  }
}
