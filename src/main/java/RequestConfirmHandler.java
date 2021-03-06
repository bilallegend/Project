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
	    String name = Cooky.getContextName("gc_account",request.getCookies(),"cookie", request);
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    ServletContext context = request.getSession().getServletContext();
	    String Reply  = data.get("reply");
	    String socketId= data.get("socket_id");
	    HashMap<String,String[]> reqInfo = Cooky.getContextValue("reqInfo",request);
	    Map<String,String> messageData = new HashMap<String, String>();
	    String requesterName = "";
		if(reqInfo == null) {
			messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
			response.getWriter().println(gson.toJson(messageData));
			return;
		}else {
			reqInfo = Cooky.getContextValue("reqInfo",request);
			HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
			boolean tryF = true;
			for(String Key : reqInfo.keySet()) {
				if(reqInfo.get(Key)[1].equals(name)) {
					messageData.put("replyId",reqInfo.get(Key)[0]);
					requesterName=Key;
					tryF=false;
					break;
				}
			
					

			}	
			
			
			if(Reply.equals("Yes") && !tryF) {
				String oppname=requesterName;
				
				HashMap<String,String[]> d=(HashMap<String,String[]>) context.getAttribute("DivMap");
				ArrayList<String> cookies=new ArrayList<String>();
				System.out.println(name);
				System.out.println(oppname);
				for(String i:d.keySet()) {
					if((d.get(i)[3].equals(name) && d.get(i)[1].equals(socketId))||(d.get(i)[3].equals(oppname)  && d.get(i)[1].equals(messageData.get("replyId")))) {
						cookies.add(i);
					}
				}
				
				ConnectionDatabase psql = new ConnectionDatabase();
				 Connection conn			=psql.createConnection("gamecenter");
				 int count=0;
				 try{
					    Statement stmt = conn.createStatement();
						String Query="select game_id from game_list ";
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
				 String a[]= new String[3];
				 System.out.println(cookies);
				 a[0]=cookies.get(0);
				 a[1]=cookies.get(1);
				 a[2]=d.get(Cooky.getCookieValue("gc_account",request.getCookies()))[2];
				 System.out.println(a[2]+"  privacy");
				 pd.put("game_"+(count+1), a);
				 
				 HashMap<String,HashMap<String,ArrayList<String>>> pd1=(HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
				 HashMap<String,ArrayList<String>> White=new HashMap<String,ArrayList<String>>();
				 HashMap<String,ArrayList<String>> Black=new HashMap<String,ArrayList<String>>();
				 
				 ArrayList<String> color=new ArrayList<String>();
				 ArrayList<String> colorid=new ArrayList<String>();
				 ArrayList<String> status=new ArrayList<String>();
				 colorid.add("29");
				 colorid.add("36");
				 color.add("White");
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
				 System.out.println((HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails"));
				
				System.out.println(pd);
				System.out.println(pd1);
				String player1id="";
				String player2id="";
				System.out.println(name);
				System.out.println(oppname);
				try{
				    Statement stmt = conn.createStatement();//Statement class creates a object that can execute our query in the connected database in connection object
					String Query="select player_id,username from player_info where username in ('"+name+"','"+oppname+"')";//Query to be passed
					System.out.println(Query);
					ResultSet data_table=stmt.executeQuery(Query);
					while(data_table.next()) {
						System.out.println("ddgdf");
						if(data_table.getString("username").equals(name)) {
							player1id=data_table.getString("player_id");
						}
						else {
							player2id=data_table.getString("player_id");
						}
					}
					
			    } catch (SQLException e) {
			        System.out.println(e+"");
			    }
				
				System.out.println(player1id);
				System.out.println(player2id);
				
				
				
				try{
				    Statement stmt = conn.createStatement();
					String Query1="insert into game_list (game_id,player1_id,player2_id,date,firstmove) values ("+(count+1)+","+Integer.parseInt(player1id)+","+Integer.parseInt(player2id)+",0,"+Integer.parseInt(player2id)+")";
					System.out.println(Query1);
					stmt.executeUpdate(Query1);//execution
					
			    } catch (SQLException e) {
			    	
			      System.out.println(e+"");
			      
			    }
				try {
					  conn.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
			}
			System.out.println("private-MyNotification-"+messageData.get("replyId"));
			ArrayList<String> socketList = MultiTabs.get(name);
			System.out.println(socketList+" in "+socketId);
			for(String socketid : socketList) {
				if(!socketid.equals(socketId)) {
					messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
				
				Result result =
				        PusherService.getDefaultInstance()
				            .trigger(
				                "private-MyNotification-"+socketid,
				                "GameResp", // name of event
				                messageData);
				messageData.put("status", result.getStatus().name());
				System.out.println("private-MyNotification-"+socketid);
				System.out.println(result.getStatus().name());
				}
			}
			MultiTabs.get(name).remove(socketList.indexOf(socketId));
			socketList = MultiTabs.get(requesterName);
			System.out.println(socketList+" in "+messageData.get("replyId"));
			for(String socketid : socketList) {
				if(socketid.equals(messageData.get("replyId")) && (Reply.equals("Yes") && !tryF)) {
					messageData.put("redir", Redirecter.giveUrlFor(request,"/home/play"));
				}else {
					messageData.put("redir", Redirecter.giveUrlFor(request,"/home"));
				}
				Result result =
				        PusherService.getDefaultInstance()
				            .trigger(
				                "private-MyNotification-"+socketid,
				                "GameResp", // name of event
				                messageData);
				messageData.put("status", result.getStatus().name());
				
			}
			if(Reply.equals("Yes") && !tryF) {
				messageData.put("reply", Reply);
				messageData.put("redir", Redirecter.giveUrlFor(request,"/home/play"));
			}else {
				messageData.put("reply", "Sorry!");
			}
			MultiTabs.get(requesterName).remove(socketList.indexOf(messageData.get("replyId")));
			reqInfo.remove(requesterName);
			messageData.remove("replyId");
			
		}
		
		
		
		response.getWriter().println(gson.toJson(messageData));
	    
	  }
	 }
